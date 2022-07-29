// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.devices;

import frc.robot.BreakerLib.util.loging.BreakerGenericTelemetryProvider;
import frc.robot.BreakerLib.util.powermanagement.BreakerPowerManageable;
import frc.robot.BreakerLib.util.selftest.BreakerSelfTestableBase;
import frc.robot.BreakerLib.util.selftest.SelfTest;

/** Future replacement class for BreakerGenericDevice */
public abstract class BreakerGenericDeviceBase extends BreakerSelfTestableBase implements BreakerPowerManageable, BreakerGenericTelemetryProvider {
    private boolean telemetryEnabled = false;
    public BreakerGenericDeviceBase() {
        SelfTest.autoRegisterDevice(this);
    }

    @Override
    public void setProviderName(String newName) {
        setDeviceName(newName);
    }

    @Override
    public String getProviderName() {
        return getDeviceName();
    }

    @Override
    public void setTelemetryEnabled(boolean isEnabled) {
        telemetryEnabled = isEnabled;
        
    }

    @Override
    public boolean isTelemetryEnabled() {
        return telemetryEnabled;
    }
}
