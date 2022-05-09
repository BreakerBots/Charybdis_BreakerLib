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

    public BreakerTranslation3d getTranslationComponent() {
        return translation;
    }

    public BreakerRotation3d getRotationComponent() {
        return rotation;
    }

    public Pose2d get2dPoseComponent() {
        return new Pose2d(translation.getMetersX(), translation.getMetersY(), rotation.getYaw());
    }
}
