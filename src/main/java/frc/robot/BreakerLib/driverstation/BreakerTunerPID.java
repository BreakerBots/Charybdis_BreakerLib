// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.driverstation;

import edu.wpi.first.math.controller.PIDController;

/** Add your docs here. */
public class BreakerTunerPID extends BreakerTunerBase {
    private PIDController controller;
    private boolean usesPassedInController;
    public BreakerTunerPID(PIDController controller) {
        this.controller = controller;
        usesPassedInController = true;
    }

    public BreakerTunerPID() {
        usesPassedInController = false;
    }


}
