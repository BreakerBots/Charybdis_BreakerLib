// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.physics;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.BreakerLib.position.geometry.Rotation3d;
import frc.robot.BreakerLib.util.math.BreakerMath;
import frc.robot.BreakerLib.util.math.interpolation.Interpolable;

/**
 * represents a point with 3 axial vectors of ajustable magnatudes (one on each
 * X, Y, and Z axis)
 */
public class Vector3 implements Interpolable<Vector3> {

    private double forceX;
    private double forceY;
    private double forceZ;
    private double magnatude;
    private Rotation3d forceRotation;

    public Vector3(double forceX, double forceY, double forceZ) {
        this.forceX = forceX;
        this.forceY = forceY;
        this.forceZ = forceZ;
        magnatude = Math.sqrt(Math.pow(forceX, 2) + Math.pow(forceY, 2) + Math.pow(forceZ, 2));
        forceRotation = new Rotation3d(new Rotation2d(Math.atan2(forceZ, forceY)),
                new Rotation2d(Math.atan2(forceY, forceX))); // need to check this math
    }

    private Vector3(double forceX, double forceY, double forceZ, double magnatude, Rotation3d forceRotation) {
        this.forceX = forceX;
        this.forceY = forceY;
        this.forceZ = forceZ;
        this.magnatude = magnatude;
        this.forceRotation = forceRotation;
    }

    public static Vector3 fromMagnatudeAndForceRotation(double magnatude, Rotation3d forceRotation) {
        double x = Math.cos(forceRotation.getYaw().getRadians()) * Math.cos(forceRotation.getPitch().getRadians());
        double y = Math.sin(forceRotation.getYaw().getRadians()) * Math.cos(forceRotation.getPitch().getRadians());
        double z = Math.sin(forceRotation.getPitch().getRadians());
        return new Vector3(x, y, z, magnatude, forceRotation);
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

    public Rotation3d getForceRotation() {
        return forceRotation;
    }

    public Vector3 minus(Vector3 outher) {
        return new Vector3(forceX - outher.forceX, forceY - outher.forceY, forceZ - outher.forceZ);
    }

    public Vector3 plus(Vector3 outher) {
        return new Vector3(forceX + outher.forceX, forceY + outher.forceY, forceZ + outher.forceZ);
    }

    @Override
    public Vector3 interpolate(double interpolendValue, double highKey, Vector3 highVal,
            double lowKey, Vector3 lowVal) {
        double interX = BreakerMath.interpolateLinear(interpolendValue, lowKey, highKey, lowVal.getForceX(),
                highVal.getForceX());
        double interY = BreakerMath.interpolateLinear(interpolendValue, lowKey, highKey, lowVal.getForceY(),
                highVal.getForceZ());
        double interZ = BreakerMath.interpolateLinear(interpolendValue, lowKey, highKey, lowVal.getForceZ(),
                highVal.getForceZ());

        return new Vector3(interX, interY, interZ);
    }

    @Override
    public Vector3 getSelf() {
        return this;
    }

    /** [0] = X, [1] = Y, [2] = Z */
    @Override
    public double[] getInterpolatableData() {
        return new double[] { forceX, forceY, forceZ };
    }

    @Override
    public Vector3 fromInterpolatableData(double[] interpolatableData) {
        return new Vector3(interpolatableData[0], interpolatableData[1], interpolatableData[2]);
    }

    @Override
    public String toString() {
        return "Vector Magnatude:" + magnatude + ", X-Magnatude: " + forceX + ", Y-Magnatude: " + forceY
                + ", Z-Magnatude: " + forceZ + ", Vector Angles: " + forceRotation.toString();
    }
}
