// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.physics;

/** Add your docs here. */
public class BreakerProjectile {
    private double massKg;
    private double surfaceAreaMetersSq_X;
    private double surfaceAreaMetersSq_Y;
    private double dragCoefficient_X;
    private double dragCoefficient_Y;
    public BreakerProjectile(double massKg, double surfaceAreaMetersSq_X, double surfaceAreaMetersSq_Y, double dragCoefficient_X, double dragCoefficient_Y) {
        this.massKg = massKg;
        this.surfaceAreaMetersSq_X = surfaceAreaMetersSq_X;
        this.surfaceAreaMetersSq_Y = surfaceAreaMetersSq_Y;
        this.dragCoefficient_X = dragCoefficient_X;
        this.dragCoefficient_Y = dragCoefficient_Y;
    }

    public double getDragCoefficient_X() {
        return dragCoefficient_X;
    }

    public double getDragCoefficient_Y() {
        return dragCoefficient_Y;
    }

    public double getMassKg() {
        return massKg;
    }

    public double getSurfaceAreaMetersSq_X() {
        return surfaceAreaMetersSq_X;
    }

    public double getSurfaceAreaMetersSq_Y() {
        return surfaceAreaMetersSq_Y;
    }
}
