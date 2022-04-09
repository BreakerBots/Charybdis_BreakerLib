// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.auto.trajectory.diffauto;

import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;

import frc.robot.BreakerLib.subsystemcores.drivetrain.differential.BreakerDiffDrive;
import frc.robot.BreakerLib.util.BreakerLog;

/** Add your docs here. */
public class BreakerRamsete extends CommandBase{
    private RamseteCommand ramsete;
    private RamseteController ramseteController;
    private BreakerDiffDrive drivetrain;
    private TrajectoryConfig config;
    private DifferentialDriveVoltageConstraint voltageConstraints;
    public BreakerRamsete(Trajectory trajectoryToFollow, BreakerDiffDrive drivetrain, 
    Subsystem subsystemRequirements, double ramseteB, double ramseteZeta, double maxVel, double maxAccel, double maxVoltage){
        BreakerLog.logBreakerLibEvent("BreakerRamsete command instance has started, total cumulative path time: " + trajectoryToFollow.getTotalTimeSeconds());
        drivetrain = this.drivetrain;
        voltageConstraints = new DifferentialDriveVoltageConstraint(drivetrain.getFeedforward(), drivetrain.getKinematics(), maxVoltage);
        config = new TrajectoryConfig(maxVel, maxAccel);
            config.setKinematics(drivetrain.getKinematics());
            config.addConstraint(voltageConstraints);
        ramseteController = new RamseteController(ramseteB, ramseteZeta);
        ramsete = new RamseteCommand(trajectoryToFollow, drivetrain :: getOdometryPoseMeters, ramseteController, drivetrain.getFeedforward(), 
        drivetrain.getKinematics(), drivetrain :: getWheelSpeeds, drivetrain.getLeftPIDController(), drivetrain.getRightPIDController(), drivetrain :: tankMoveVoltage, subsystemRequirements);
        ramsete.schedule();
    }

    @Override
    public void end(boolean interrupted) {
        BreakerLog.logBreakerLibEvent("BreakerRamsete command instance has ended");
    }

    @Override
    public boolean isFinished() {
        return ramsete.isFinished();
    }
}
