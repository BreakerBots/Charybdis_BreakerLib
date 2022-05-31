// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util.math.interpolation.interpolateingmaps;

/** Add your docs here. */
public interface BreakerGenericInterpolateingMap<K, V> {

    /**
     * interpolates the Map to return the aproxamate value(Y) that would corespond
     * to a given Key(X) based on the data points provided
     * 
     * @param interpolendValue the Key(Y) value to base the estimation on
     * @return
     */
    public abstract V getInterpolatedValue(K interpolendValue);

}
