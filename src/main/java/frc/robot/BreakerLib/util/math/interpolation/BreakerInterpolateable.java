// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util.math.interpolation;

/** Add your docs here. */
public interface BreakerInterpolateable<V> {
    public abstract V interpolate(double interpolendValue, double highKey, V highVal, double lowKey, V lowVal);

    public abstract V getSelf();
}
