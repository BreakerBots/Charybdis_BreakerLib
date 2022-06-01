// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.position.geometry;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.BreakerLib.util.math.interpolation.Interpolable;

/**
 * Represents an object's 3-dimensional position and 3 axis angular orientation
 * in space (Linear: XYZ / Angular: YPR)
 * <p>
 * Combines {@link Translation3d} and {@link Rotation3d} into one pose.
 */
public class Pose3d implements Interpolable<Pose3d> {
    private Translation3d translation;
    private Rotation3d rotation;

    /**
     * Creates a new BreakerPose3d based on given position and rotation.
     * 
     * @param metersX  x-position in meters.
     * @param metersY  y-position in meters.
     * @param metersZ  z-position in meters.
     * @param rotation 3d rotation of pose.
     */
    public Pose3d(double metersX, double metersY, double metersZ, Rotation3d rotation) {
        this.rotation = rotation;
        translation = new Translation3d(metersX, metersY, metersZ);
    }

    /**
     * Creates a new BreakerPose3d based on given translation and rotation.
     * 
     * @param translation 3d translation of positional pose.
     * @param rotation    3d rotation of pose
     */
    public Pose3d(Translation3d translation, Rotation3d rotation) {
        this.rotation = rotation;
        this.translation = translation;
    }

    /**
     * Creates a new BreakerPose3d based on given translation and rotation.
     * 
     * @param pose2d 2d pose of robot. z-pos, pitch, and roll are set to 0.
     */
    public Pose3d(Pose2d pose2d) {
        rotation = new Rotation3d(Rotation2d.fromDegrees(0d), pose2d.getRotation(), Rotation2d.fromDegrees(0d));
        translation = new Translation3d(pose2d.getX(), pose2d.getY(), 0d);
    }

    public Translation3d getTranslationComponent() {
        return translation;
    }

    public Rotation3d getRotationComponent() {
        return rotation;
    }

    /** Returns 2d pose with x-pos, y-pos, and yaw. */
    public Pose2d get2dPoseComponent() {
        return new Pose2d(translation.getMetersX(), translation.getMetersY(), rotation.getYaw());
    }

    /** Returns new pose transformed by the given {@link Transform3d}. */
    public Pose3d transformBy(Transform3d transformation) {
        Translation3d newTrans = this.translation.plus(transformation.getTranslationComponent());
        Rotation3d newRot = this.rotation.plus(transformation.getRotationComponent());
        return new Pose3d(newTrans, newRot);
    }

    @Override
    public String toString() {
        return "Position: (" + translation.toString() + "), Rotation: (" + rotation.toString() + ")";
    }

    @Override
    public Pose3d interpolate(double interpolendValue, double highKey, Pose3d highVal, double lowKey, Pose3d lowVal) {
        Translation3d interT = translation.interpolate(interpolendValue, highKey, highVal.translation, lowKey,
                lowVal.translation);
        Rotation3d interR = rotation.interpolate(interpolendValue, highKey, highVal.rotation, lowKey, lowVal.rotation);
        return new Pose3d(interT, interR);
    }

    @Override
    public Pose3d getSelf() {
        return this;
    }

    /** [0-2] = Translation, [3-5] = Rotation */
    @Override
    public double[] getInterpolatableData() {
        return new double[] { translation.getMetersX(), translation.getMetersY(),
                translation.getMetersZ(), rotation.getPitch().getRadians(),
                rotation.getYaw().getRadians(), rotation.getRoll().getRadians() };
    }

    @Override
    public Pose3d fromInterpolatableData(double[] interpolatableData) {
        return new Pose3d(interpolatableData[0], interpolatableData[1], interpolatableData[2],
                rotation.fromInterpolatableData(
                        new double[] { interpolatableData[3], interpolatableData[4], interpolatableData[5] }));
    }
}
