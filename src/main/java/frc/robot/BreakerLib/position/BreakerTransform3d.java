// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.position;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Transform2d;

/** Represents a 3d transformation of a pose */
public class BreakerTransform3d {

    private BreakerTranslation3d translation;
    private BreakerRotation3d rotation;
    
    public BreakerTransform3d(BreakerTranslation3d translation, BreakerRotation3d rotation) {
        this.translation = translation;
        this.rotation = rotation;
    }

    public BreakerRotation3d getRotationComponent() {
        return rotation;
    }

    public BreakerTranslation3d getTranslationComponent() {
        return translation;
    }

    public Transform2d get2dTransformationComponent() {
        return new Transform2d(translation.get2dTranslationComponent(), rotation.getYaw());
    }

    public BreakerPose3d transformFromPose2d(Pose2d pose2d) {
        return new BreakerPose3d(pose2d).transformBy(this);
    }
}
