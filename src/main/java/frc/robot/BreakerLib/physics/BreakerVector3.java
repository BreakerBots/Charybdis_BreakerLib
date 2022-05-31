// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.physics;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.BreakerLib.position.geometry.BreakerRotation3d;
import frc.robot.BreakerLib.util.math.BreakerMath;
import frc.robot.BreakerLib.util.math.interpolation.BreakerInterpolateable;

/** represents a point with 3 axial vectors of ajustable magnatudes (one on each X, Y, and Z axis) */
public class BreakerVector3 implements BreakerInterpolateable<BreakerVector3>{

    private double forceX;
    private double forceY;
    private double forceZ;
    private double magnatude;
    private BreakerRotation3d forceRotation;

    public BreakerVector3(double forceX, double forceY, double forceZ) {
        this.forceX = forceX;
        this.forceY = forceY;
        this.forceZ = forceZ;
        magnatude = Math.sqrt(Math.pow(forceX, 2) + Math.pow(forceY, 2) + Math.pow(forceZ, 2));
        forceRotation = new BreakerRotation3d(new Rotation2d(Math.atan2(forceZ, forceY)), new Rotation2d(Math.atan2(forceY, forceX))); // need to check this math
    }

    private BreakerVector3(double forceX, double forceY, double forceZ, double magnatude, BreakerRotation3d forceRotation) {
        this.forceX = forceX;
        this.forceY = forceY;
        this.forceZ = forceZ;
        this.magnatude = magnatude;
        this.forceRotation = forceRotation;
    }

    public static BreakerVector3 fromMagnatudeAndForceRotation(double magnatude, BreakerRotation3d forceRotation) {
        double x = Math.cos(forceRotation.getYaw().getRadians()) * Math.cos(forceRotation.getPitch().getRadians());
        double y = Math.sin(forceRotation.getYaw().getRadians()) * Math.cos(forceRotation.getPitch().getRadians());
        double z = Math.sin(forceRotation.getPitch().getRadians());
        return new BreakerVector3(x, y, z, magnatude, forceRotation);
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

    public double getMagnatude() {
        return magnatude;
    }

    public BreakerRotation3d getForceRotation() {
        return forceRotation;
    }

    public BreakerVector3 minus(BreakerVector3 outher) {
        return new BreakerVector3(forceX - outher.forceX, forceY - outher.forceY, forceZ - outher.forceZ);
    }

    public BreakerVector3 plus(BreakerVector3 outher) {
        return new BreakerVector3(forceX + outher.forceX, forceY + outher.forceY, forceZ + outher.forceZ);
    }

    @Override
    public BreakerVector3 interpolate(double interpolendValue, double highKey, BreakerVector3 highVal,
            double lowKey, BreakerVector3 lowVal) {
                double interX = BreakerMath.interpolateLinear(interpolendValue, lowKey, highKey, lowVal.getForceX(), highVal.getForceX());
                double interY = BreakerMath.interpolateLinear(interpolendValue, lowKey, highKey, lowVal.getForceY(), highVal.getForceZ());
                double interZ = BreakerMath.interpolateLinear(interpolendValue, lowKey, highKey, lowVal.getForceZ(), highVal.getForceZ());
        
                return new BreakerVector3(interX, interY, interZ);
    }

    @Override
    public BreakerVector3 getSelf() {
        return this;
    }

    /** [0] = X, [1] = Y, [2] = Z */
    @Override
    public double[] getInterpolatableData() {
        return new double[] {forceX, forceY, forceZ};
    }

    @Override
    public BreakerVector3 fromInterpolatableData(double[] interpolatableData) {
        return new BreakerVector3(interpolatableData[0], interpolatableData[1], interpolatableData[2]);
    }
}
