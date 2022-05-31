// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util.math.interpolation.interpolateingmaps;

/** Add your docs here. */
public interface BreakerGenericInterpolateingMap<K, V> {
    
    public abstract V getInterpolatedValue(K interpolendValue);

}
