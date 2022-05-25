// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util.math.interpolation;

import frc.robot.BreakerLib.util.math.BreakerMath;

/** wraps around the double primitive type for use with BreakerLib's interpolation classes */
public class BreakerInterpolatableDouble<T> implements BreakerInterpolateable<Double> {
    private Double value;
    public BreakerInterpolatableDouble(Double value) {
        this.value = value;
    }
    @Override
    public Double interpolate(double interpolendValue, double highKey, Double highVal, double lowKey, Double lowVal) {
        return BreakerMath.interpolateLinear(interpolendValue, lowKey, highKey, lowVal.doubleValue(), highVal.doubleValue());
    }

    @Override
    public Double getSelf() {
        return value;
    }


}
