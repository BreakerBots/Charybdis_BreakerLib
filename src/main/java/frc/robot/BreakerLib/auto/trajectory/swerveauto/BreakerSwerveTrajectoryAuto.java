// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.auto.trajectory.swerveauto;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import frc.robot.BreakerLib.subsystemcores.drivetrain.swerve.BreakerSwerveDrive;
import frc.robot.BreakerLib.util.BreakerLog;

/** Add your docs here. */
public class BreakerSwerveTrajectoryAuto extends CommandBase {
    private SwerveControllerCommand controller;
    private BreakerSwerveTrajectoryAutoConfig config;
    private BreakerSwerveDrive drivetrain;
    private Subsystem requiredSubsystem;
    private Trajectory[] trajectorysToFollow;
    private int currentTrajectory = 0;
    private int prevTrajectory = 0;
    private double totalTimeSeconds = 0;
    private boolean commandIsFinished = false;
    private boolean stopAtEnd = false;
    private double currentTimeCycles = 0;
    BreakerSwerveTrajectoryAuto(BreakerSwerveTrajectoryAutoConfig config, Boolean stopAtEnd, Subsystem requiredSubsystem, Trajectory... trajectorysToFollow) {
        drivetrain = config.getDrivetrain();
        this.trajectorysToFollow = trajectorysToFollow;
        this.config = config;
        this.stopAtEnd = stopAtEnd;
        this.requiredSubsystem = requiredSubsystem;
    }

    @Override
    public void initialize() {
        for (Trajectory trajectory: trajectorysToFollow) {
            totalTimeSeconds += trajectory.getTotalTimeSeconds();
        }
        BreakerLog.logBreakerLibEvent("BreakerSwerveTrajectoryAuto command instance started, total cumulative path time: " + totalTimeSeconds);
    }

    @Override
    public void execute() {
        currentTimeCycles ++;
        if (currentTrajectory != prevTrajectory) {
            try {
                controller = new SwerveControllerCommand(trajectorysToFollow[currentTrajectory], drivetrain::getOdometryPoseMeters, 
                    drivetrain.getConfig().getKinematics(), config.getxPosPID(), config.getyPosPID(), config.gettAngPID(), drivetrain::setRawModuleStates, requiredSubsystem);
                controller.schedule();
                BreakerLog.logBreakerLibEvent("BreakerSwerveTrajectoryAuto command instance has swiched to a new trajecotry");
            } catch (Exception e) {
                commandIsFinished = true;
            }
        }
        prevTrajectory = currentTrajectory;
        if (controller.isFinished()) {
            currentTrajectory ++;
        }
    }

    public double getTotalTimeSeconds() {
        return totalTimeSeconds;
    }

    public double getCurrentTimeSeconds() {
        return (currentTimeCycles / 50);
    }

    @Override
    public void end(boolean interrupted) {
        if (stopAtEnd) {
            drivetrain.stop();
        }
        BreakerLog.logBreakerLibEvent("BreakerSwerveTrajectoryAuto command instance has ended");
    }

    @Override
    public boolean isFinished() {
        return commandIsFinished || (currentTrajectory > trajectorysToFollow.length);
    }
}
