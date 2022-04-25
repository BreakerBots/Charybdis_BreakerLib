// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.auto.trajectory.swerve;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.Trajectory.State;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SwerveControllerCommand;
import frc.robot.BreakerLib.auto.trajectory.BreakerGenericTrajecotryFollower;
import frc.robot.BreakerLib.subsystemcores.drivetrain.swerve.BreakerSwerveDrive;
import frc.robot.BreakerLib.util.BreakerLog;

/** Add your docs here. */
public class BreakerFollowSwerveTrajectory extends CommandBase implements BreakerGenericTrajecotryFollower {
    private SwerveControllerCommand controller;
    private BreakerFollowSwerveTrajectoryConfig config;
    private BreakerSwerveDrive drivetrain;
    private Subsystem requiredSubsystem;
    private Trajectory[] trajectorysToFollow;
    private int currentTrajectory = 0;
    private int prevTrajectory = 0;
    private double totalTimeSeconds = 0;
    private boolean commandIsFinished = false;
    private boolean stopAtEnd = false;
    private double currentTimeCycles = 0;
    BreakerFollowSwerveTrajectory(BreakerFollowSwerveTrajectoryConfig config, Boolean stopAtEnd, Subsystem requiredSubsystem, Trajectory... trajectorysToFollow) {
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

    @Override
    public Trajectory getCurrentTrajectory() {
        return trajectorysToFollow[currentTrajectory];
    }

    @Override
    public Trajectory[] getAllTrajectorys() {
        return trajectorysToFollow;
    }

    @Override
    public double getTotalPathTimeSeconds() {
        return totalTimeSeconds;
    }

    @Override
    public double getCurrentPathTimeSeconds() {
        return (currentTimeCycles / 50);
    }

    @Override
    public boolean getPathStopsAtEnd() {
        return stopAtEnd;
    }

    @Override
    public List<State> getAllStates() {
        List<State> allStates = new ArrayList<State>();
        for (Trajectory sampTreject: trajectorysToFollow) {
            for (State sampState: sampTreject.getStates()) {
                allStates.add(sampState);
            }
        }
        return allStates;
    }

    private double getOnlyCurrentPathTime() {
        double time = 0;
        for (int i = 0; i < currentTrajectory; i++) {
            time += trajectorysToFollow[i].getTotalTimeSeconds();
        }
        return (getCurrentPathTimeSeconds() - time);
    }

    @Override
    public State getCurrentState() {
        return trajectorysToFollow[currentTrajectory].sample(getCurrentPathTimeSeconds() - getOnlyCurrentPathTime());
    }
}
