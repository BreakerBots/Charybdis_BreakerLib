// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.physics;

import edu.wpi.first.math.geometry.Rotation2d;

/** represents a point with 2 axial vectors of ajustable magnatudes (one paralell with the points X, and Y axis) */
public class BreakerVector2 {
    private Rotation2d forceRotation;
    private double force;
    private double forceX;
    private double forceY;
    public BreakerVector2(double forceX, double forceY) {
        this.forceX = forceX;
        this.forceY = forceY;
    }

    private BreakerVector2(double forceX, double forceY, double force, Rotation2d forceRotation) {
        this.forceX = forceX;
        this.forceY = forceY;
        this.force = force;
        this.forceRotation = forceRotation;
    }

    public static BreakerVector2 fromForceAndRotation(Rotation2d forceRotation, double force) {
        return new BreakerVector2(force * Math.cos(forceRotation.getRadians()), force * Math.sin(forceRotation.getRadians()), force, forceRotation);
    }

    public double getForce() {
        return force;
    }

    public Rotation2d getForceRotation() {
        return forceRotation;
    }

    public double getForceX() {
        return forceX;
    }

    public double getForceY() {
        return forceY;
    }
}
