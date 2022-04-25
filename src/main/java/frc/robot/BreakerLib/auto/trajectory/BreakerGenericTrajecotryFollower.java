// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.auto.trajectory;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.Trajectory.State;

/** Add your docs here. */
public interface BreakerGenericTrajecotryFollower {
    public abstract Trajectory getCurrentTrajectory();

    public abstract Trajectory[] getAllTrajectorys();

    public abstract double getTotalPathTimeSeconds();

    public abstract double getCurrentPathTimeSeconds();

    public abstract boolean getPathStopsAtEnd();

    public abstract List<State> getAllStates();

    public abstract State getCurrentState();

}

