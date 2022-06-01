// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util.math.interpolation;

import frc.robot.BreakerLib.util.math.BreakerMath;

/**
 * Wraps around the double primitive type for use with BreakerLib's
 * interpolation classes
 */
public class InterpolableDouble implements Interpolable<InterpolableDouble> {
    private double value;

    public InterpolableDouble(Double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public InterpolableDouble getSelf() {
        return this;
    }

    @Override
    public InterpolableDouble interpolate(double valToInterpolate, double highKey,
            InterpolableDouble highVal, double lowKey, InterpolableDouble lowVal) {
        BreakerMath.interpolateLinear(valToInterpolate, lowKey, highKey, lowVal.getValue(), highVal.getValue());
        return new InterpolableDouble(value);
    }

    @Override
    public double[] getInterpolatableData() {
        return new double[] { value };
    }

    @Override
    public InterpolableDouble fromInterpolatableData(double[] interpolatableData) {
        return new InterpolableDouble(interpolatableData[0]);
    }

}
