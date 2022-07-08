// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.subsystemcores.shooter;

/** Add your docs here. */
public class BreakerFlywheelConfig {
    private double flywheelKp;
    private double flywheelKi;
    private double flywheelKd;
    private double flywheelPosTol;
    private double flywheelVelTol;
    private double flywheelKv;
    private double flywheelKs;
    private double modelKalmanTrust;
    private double encoderKalmanTrust;
    private double lqrVelocityErrorTolerance;
    private double lqrControlEffort;
    public BreakerFlywheelConfig(double flywheelKp, double flywheelKi, double flywheelKd, double flywheelPosTol, 
    double flywheelVelTol, double flywheelKv, double flywheelKs, double modelKalmanDeviation, 
    double encoderKalmanDeveation, double lqrVelocityErrorTolerance, double lqrControlEffort) {

        this.flywheelKp = flywheelKp;
        this.flywheelKi = flywheelKi;
        this.flywheelKd = flywheelKd;
        this.flywheelPosTol = flywheelPosTol;
        this.flywheelVelTol = flywheelVelTol;
        this.flywheelKv = flywheelKv;
        this.flywheelKs = flywheelKs;
        this.modelKalmanTrust = modelKalmanDeviation;
        this.encoderKalmanTrust = encoderKalmanDeveation;
        this.lqrVelocityErrorTolerance = lqrVelocityErrorTolerance;
        this.lqrControlEffort = lqrControlEffort;
    }

    public double getFlywheelKs() {
        return flywheelKs;
    }

    public double getFlywheelKv() {
        return flywheelKv;
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

    public double getFlywheelPosTol() {
        return flywheelPosTol;
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
