// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.subsystemcores.drivetrain.automaticbreakmanagement;

import frc.robot.BreakerLib.subsystemcores.drivetrain.BreakerGenericDrivetrain;

/** Add your docs here. */
public class BreakerAutomaticBreakModeManagerConfig {
    private boolean breakInAuto;
    private boolean breakInTeleop;
    private boolean breakInTest;
    private boolean breakInDisabled;
    private BreakerGenericDrivetrain baseDrivetrain;
    public BreakerAutomaticBreakModeManagerConfig(BreakerGenericDrivetrain baseDrivetrain, boolean breakInTeleop, boolean breakInAuto, boolean breakInTest, boolean breakInDisabled) {
        this.breakInAuto = breakInAuto;
        this.breakInDisabled = breakInDisabled;
        this.breakInTeleop = breakInTeleop;
        this.breakInTest = breakInTest;
        this.baseDrivetrain = baseDrivetrain;
    }

    public BreakerAutomaticBreakModeManagerConfig(BreakerGenericDrivetrain baseDrivetrain) {
        this.breakInAuto = true;
        this.breakInDisabled = false;
        this.breakInTeleop = true;
        this.breakInTest = true;
        this.baseDrivetrain = baseDrivetrain;
    }

    public boolean getBreakInAuto() {
        return breakInAuto;
    }

    public boolean getBreakInDisabled() {
        return breakInDisabled;
    }

    public boolean getBreakInTeleop() {
        return breakInTeleop;
    }

    public boolean getBreakInTest() {
        return breakInTest;
    }

    public BreakerGenericDrivetrain getBaseDrivetrain() {
        return baseDrivetrain;
    }
}
