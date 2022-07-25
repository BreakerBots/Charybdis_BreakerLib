// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.subsystemcores.shooter;

/** Add your docs here. */
public class BreakerFlywheelConfig {
    private double flywheelKp;
    private double flywheelKi;
    private double flywheelKd;
    private double flywheelKv;
    private double flywheelKs;
    private double flywheelKa;
    private double flywheelAccelTol;
    private double flywheelVelTol;
    private double flywheelMomentOfInertaJKgMetersSq;
    private double flywheelGearRatioToOne;
    private double modelKalmanTrust;
    private double encoderKalmanTrust;
    private double lqrVelocityErrorTolerance;
    private double lqrControlEffort;

    public BreakerFlywheelConfig(double flywheelKp, double flywheelKi, double flywheelKd, double flywheelVelTol,
            double flywheelAccelTol, double flywheelMomentOfInertaJKgMetersSq, double flywheelGearRatioToOne,
            double modelKalmanDeviation,
            double encoderKalmanDeveation, double lqrVelocityErrorTolerance, double lqrControlEffort, double flywheelKv,
            double flywheelKa, double flywheelKs) {

        this.flywheelKp = flywheelKp;
        this.flywheelKi = flywheelKi;
        this.flywheelKd = flywheelKd;
        this.flywheelKv = flywheelKv;
        this.flywheelKs = flywheelKs;
        this.flywheelKa = flywheelKa;
        this.flywheelAccelTol = flywheelAccelTol;
        this.flywheelVelTol = flywheelVelTol;
        this.flywheelMomentOfInertaJKgMetersSq = flywheelMomentOfInertaJKgMetersSq;
        this.flywheelGearRatioToOne = flywheelGearRatioToOne;
        this.modelKalmanTrust = modelKalmanDeviation;
        this.encoderKalmanTrust = encoderKalmanDeveation;
        this.lqrVelocityErrorTolerance = lqrVelocityErrorTolerance;
        this.lqrControlEffort = lqrControlEffort;
    }

    public double getFlywheelKa() {
        return flywheelKa;
    }

    public double getFlywheelKs() {
        return flywheelKs;
    }

    public double getFlywheelKv() {
        return flywheelKv;
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

    public double getFlywheelKd() {
        return flywheelKd;
    }

    public double getFlywheelKi() {
        return flywheelKi;
    }

    public double getFlywheelKp() {
        return flywheelKp;
    }

    public double getFlywheelAccelTol() {
        return flywheelAccelTol;
    }

    public double getFlywheelVelTol() {
        return flywheelVelTol;
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
