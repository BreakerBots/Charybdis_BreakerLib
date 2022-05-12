// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.auto.trajectory.management;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.BreakerLib.devices.sensors.BreakerPigeon2;
import frc.robot.BreakerLib.subsystemcores.drivetrain.BreakerGenericDrivetrain;
import frc.robot.BreakerLib.util.BreakerLog;

public class BreakerStartTrajectoryPath extends InstantCommand {
  private BreakerGenericDrivetrain drivetrain;
  private BreakerPigeon2 pigeon2;
  private Pose2d startingPose;
  public BreakerStartTrajectoryPath(BreakerGenericDrivetrain drivetrain, Pose2d startingPose, BreakerPigeon2 pigeon2) {
    this.drivetrain = drivetrain;
    this.pigeon2 = pigeon2;
    this.startingPose = startingPose;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    BreakerLog.logBreakerLibEvent("A Trajectory Path has been started - A BreakerStartTrajectoryPath instance has been created and ran");
    drivetrain.setOdometry(startingPose, pigeon2.getRawAngles()[0]);
  }
}
