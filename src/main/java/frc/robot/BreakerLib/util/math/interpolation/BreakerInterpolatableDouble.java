// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util.math.interpolation;

import frc.robot.BreakerLib.util.math.BreakerMath;

/** wraps around the double primitive type for use with BreakerLib's interpolation classes */
public class BreakerInterpolatableDouble implements BreakerInterpolateable<BreakerInterpolatableDouble> {
    private double value;
    public BreakerInterpolatableDouble(Double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public BreakerInterpolatableDouble getSelf() {
        return this;
    }

    @Override
    public BreakerInterpolatableDouble interpolate(double interpolendValue, double highKey,
            BreakerInterpolatableDouble highVal, double lowKey, BreakerInterpolatableDouble lowVal) {
                BreakerMath.interpolateLinear(interpolendValue, lowKey, highKey, lowVal.getValue(), highVal.getValue());
        return new BreakerInterpolatableDouble(value);
    }


}
