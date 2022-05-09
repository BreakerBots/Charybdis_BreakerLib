// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.physics;

import edu.wpi.first.math.geometry.Rotation2d;

/** Add your docs here. */
public class BreakerVector2 {
    private Rotation2d forceRotation;
    private double force;
    private double forceX;
    private double forceY;
    public BreakerVector2(Rotation2d forceRotation, double force) {
        this.forceRotation = forceRotation;
        this.force = force;
        forceX = force * Math.cos(forceRotation.getRadians());
        forceY = force * Math.sin(forceRotation.getRadians());
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
