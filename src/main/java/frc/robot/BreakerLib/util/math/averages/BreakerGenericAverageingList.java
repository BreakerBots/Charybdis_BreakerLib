// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util.math.averages;

/** Add your docs here. */
public interface BreakerGenericAverageingList<T> {
    public abstract void addValue(T valueToAdd);
    public abstract T getAverage();
    public abstract T getAverageBetweenGivenIndexes(int startIndex, int stopIndex);
    public abstract T[] getAsArray();
}
