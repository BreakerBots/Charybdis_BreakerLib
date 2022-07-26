// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.subsystemcores.shooter;

import edu.wpi.first.math.controller.PIDController;

/** Add your docs here. */
public class BreakerFlywheelConfig {
    private double flywheelMomentOfInertaJKgMetersSq;
    private double flywheelGearRatioToOne;
    private double modelKalmanTrust;
    private double encoderKalmanTrust;
    private double lqrVelocityErrorTolerance;
    private double lqrControlEffort;

    public BreakerFlywheelConfig( double flywheelMomentOfInertaJKgMetersSq, double flywheelGearRatioToOne,
            double modelKalmanDeviation, double encoderKalmanDeveation, 
            double lqrVelocityErrorTolerance, double lqrControlEffort) {

        this.flywheelMomentOfInertaJKgMetersSq = flywheelMomentOfInertaJKgMetersSq;
        this.flywheelGearRatioToOne = flywheelGearRatioToOne;
        this.modelKalmanTrust = modelKalmanDeviation;
        this.encoderKalmanTrust = encoderKalmanDeveation;
        this.lqrVelocityErrorTolerance = lqrVelocityErrorTolerance;
        this.lqrControlEffort = lqrControlEffort;
    }

    public double getFlywheelGearRatioToOne() {
        return flywheelGearRatioToOne;
    }

    public double getFlywheelMomentOfInertaJKgMetersSq() {
        return flywheelMomentOfInertaJKgMetersSq;
    }

    public double getEncoderKalmanTrust() {
        return encoderKalmanTrust;
    }

    public double getLqrControlEffort() {
        return lqrControlEffort;
    }

    public double getLqrVelocityErrorTolerance() {
        return lqrVelocityErrorTolerance;
    }

    public double getModelKalmanTrust() {
        return modelKalmanTrust;
    }
}
