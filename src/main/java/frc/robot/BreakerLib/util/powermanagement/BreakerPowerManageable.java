// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util.powermanagement;

/** Add your docs here. */
public interface BreakerPowerManageable {
    public abstract DevicePowerMode calculatePowerMode(BreakerPowerManagementConfig managementConfig, double remainingRobotBatteryPrecent);
    public abstract void setPowerMode(DevicePowerMode currentPowerMode);
    public abstract void overrideAutomaticPowerManagement(boolean isEnabled);
    public abstract boolean isUnderAutomaticControl();
    public abstract DevicePowerMode getPowerMode();
}
