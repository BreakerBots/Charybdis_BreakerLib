// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.position;

import frc.robot.BreakerLib.physics.Breaker6AxisForces;

/** Represents an objects 3 dimentional (linear: XYZ / Angular: YPR) position, velocity, acceleration, and jerk at a given time */
public class BreakerMovementState3d {
    public BreakerMovementState3d(BreakerPose3d position, Breaker6AxisForces velocity, Breaker6AxisForces acceleration, Breaker6AxisForces jerk) {

    }

    public BreakerMovementState3d(BreakerPose3d position, Breaker6AxisForces velocity, Breaker6AxisForces acceleration) {

    }
}
