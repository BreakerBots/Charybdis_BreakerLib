// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.position;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

/** represnets an objects angular orentation in 3 dimentional space using yaw, pitch, and roll */
public class BreakerRotation3d {
    private Rotation2d pitch;
    private Rotation2d yaw;
    private Rotation2d roll;
    public BreakerRotation3d(Rotation2d pitch, Rotation2d yaw, Rotation2d roll) {
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
    }

    public Rotation2d getPitch() {
        return pitch;
    }

    public Rotation2d getYaw() {
        return yaw;
    }

    public Rotation2d getRoll() {
        return roll;
    }
}
