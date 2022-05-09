// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.physics;

import edu.wpi.first.math.geometry.Rotation2d;

/** Add your docs here. */
public class BreakerVector3 {
    
    private double forceX;
    private double forceY;
    private double forceZ;

    public BreakerVector3(double forceX, double forceY, double forceZ) {
        this.forceX = forceX;
        this.forceY = forceY;
        this.forceZ = forceZ;
    }

    public double getForceX() {
        return forceX;
    }

    public double getForceY() {
        return forceY;
    } 

    public double getForceZ() {
        return forceZ;
    }
}
