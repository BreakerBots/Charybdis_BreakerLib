// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.vision;

import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.BreakerLib.auto.trajectory.BreakerPurePersuitTrajectory;
import frc.robot.BreakerLib.auto.trajectory.diff.BreakerRamsete;
import frc.robot.BreakerLib.subsystemcores.drivetrain.differential.BreakerDiffDrive;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.devices.Vision;

public class PursueBall extends CommandBase {
  /** Creates a new persueBall. */
  private Vision vision;
  private Drive drivetrain;
  private BreakerPurePersuitTrajectory trajectory;
  private Pose2d aproxTargetPose;
  private BreakerRamsete ramsete;
  private boolean tgtPoseUpdated = false;
  private double drivePoseAngCor = 0;
  private int cycleCount = 0;
  public PursueBall(Vision vision, Drive drivetrain, Pose2d aproxTargetPose) {
    this.vision = vision;
    this.aproxTargetPose = aproxTargetPose;
    this.drivetrain = drivetrain;
    trajectory = new BreakerPurePersuitTrajectory(waypointIgnoreThreshold, config, waypoints);
    trajectory.generate(drivetrain.getBaseDrivetrain().getOdometryPoseMeters(), aproxTargetPose);
    ramsete = new BreakerRamsete(trajectory, getCorCamPose(), drivetrain, ramseteB, ramseteZeta, maxVel, maxAccel, maxVoltage, stopAtEnd);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    ramsete.schedule();
  }

  private Pose2d getCorCamPose() {
    if (!tgtPoseUpdated && vision.hasTargetsBC()) {
      drivePoseAngCor = drivetrain.getBaseDrivetrain().getOdometryPoseMeters().getRotation().getDegrees() - vision.getBestTargetBC().getYaw();
      tgtPoseUpdated = true;
    }
    return new Pose2d(drivetrain.getBaseDrivetrain().getOdometryPoseMeters().getTranslation(), Rotation2d.fromDegrees(drivetrain.getBaseDrivetrain().getOdometryPoseMeters().getRotation().getDegrees() - drivePoseAngCor));
  }

  private Pose2d getCorTgtPose() {
    return new Pose2d(vision.getBestTargetBC().getTargetTranslationFromField(), Rotation2d.fromDegrees(0));
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (cycleCount++ % 250 == 0) {
        trajectory.generate(getCorCamPose(), getCorTgtPose());
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return ramsete.isFinished();
  }
}
