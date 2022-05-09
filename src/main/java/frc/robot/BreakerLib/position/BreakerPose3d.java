// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.position;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;

/** Represents an objects 3 dimentional position and 3 axis angle in space (Linear: XYZ / Angular: YPR) */
public class BreakerPose3d {
    private BreakerTranslation3d translation;
    private BreakerRotation3d rotation;
    public BreakerPose3d(double metersX, double metersY, double metersZ, BreakerRotation3d rotation) {
        this.rotation = rotation;
        translation = new BreakerTranslation3d(metersX, metersY, metersZ);
    }

    public BreakerPose3d(BreakerTranslation3d translation, BreakerRotation3d rotation) {
        this.rotation = rotation;
        this.translation = translation;
    }

    public BreakerPose3d(Pose2d pose2d) {
        rotation = new BreakerRotation3d(Rotation2d.fromDegrees(0d), pose2d.getRotation(), Rotation2d.fromDegrees(0d));
        translation = new BreakerTranslation3d(pose2d.getX(), pose2d.getY(), 0d);
    }

    public BreakerTranslation3d getTranslationComponent() {
        return translation;
    }

    public BreakerRotation3d getRotationComponent() {
        return rotation;
    }

    public Pose2d get2dPoseComponent() {
        return new Pose2d(translation.getMetersX(), translation.getMetersY(), rotation.getYaw());
    }

    public BreakerPose3d transformBy(BreakerTransform3d outher) {
        BreakerTranslation3d newTrans = this.translation.plus(outher.getTranslationComponent());
        BreakerRotation3d newRot = this.rotation.plus(outher.getRotationComponent());
        return new BreakerPose3d(newTrans, newRot);
    }
}
