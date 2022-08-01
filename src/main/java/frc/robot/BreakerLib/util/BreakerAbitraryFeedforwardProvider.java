// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util;

import java.util.function.DoubleSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

import frc.robot.BreakerLib.util.math.interpolation.interpolateingmaps.BreakerGenericInterpolatingMap;

/** Add your docs here. */
public class BreakerAbitraryFeedforwardProvider {
    private BreakerGenericInterpolatingMap<Double, Double> ffMap;
    private double feedforwardCoeficent, staticFrictionCoeficent; 
    private DoubleSupplier ffSupplier;
    private FFType ffType;
    private enum FFType{
        MAP_SUP,
        COEFS,
        SUPPLIER
    }

    public BreakerAbitraryFeedforwardProvider(BreakerGenericInterpolatingMap<Double, Double> speedToFeedforwardValMap) {
        ffMap = speedToFeedforwardValMap;
        ffType = FFType.MAP_SUP;
    }

    public BreakerAbitraryFeedforwardProvider(double feedforwardCoeficent, double staticFrictionCoeficent) {
        this.feedforwardCoeficent = feedforwardCoeficent;
        this.staticFrictionCoeficent = staticFrictionCoeficent;
        ffType = FFType.COEFS;
    }

    public BreakerAbitraryFeedforwardProvider(DoubleSupplier ffSupplier) {
        this.ffSupplier = ffSupplier;
        ffType = FFType.SUPPLIER;
    }

    public double getArbitraryFeedforwardValue(double curSpeed) {
        double feedForward = 0.0;
        switch (ffType) {
            case COEFS:
                feedForward = feedforwardCoeficent * curSpeed + staticFrictionCoeficent;
                break;
            case MAP_SUP:
                feedForward = ffMap.getInterpolatedValue(curSpeed);
                break;
            case SUPPLIER:
                feedForward = ffSupplier.getAsDouble();
                break;
        }
        return feedForward;
    }
}
