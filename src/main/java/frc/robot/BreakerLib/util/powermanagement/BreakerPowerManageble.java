// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util.powermanagement;

/** Add your docs here. */
public interface BreakerPowerManageble {
    public abstract void enablePowerManagement(BreakerPowerChannel powerChannel);
    public abstract void dissablePowerManagement();
    public abstract boolean powerManagementIsEnabled();
    public abstract boolean getPowerMode();
    public abstract boolean getAutomaticPowerModeUsed();
    public abstract void overrideAutomaticPowerMode(DevicePowerMode newMode);
    public abstract void renableAutomaticPowerMode();
    public abstract void setPowerManagementPriority();
    public abstract PowerManagementPriority getPowerManagementPriority();
}
