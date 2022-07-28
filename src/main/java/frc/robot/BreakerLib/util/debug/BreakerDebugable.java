// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util.debug;

/** Add your docs here. */
public abstract class BreakerDebugable {
    protected boolean debugEnabled = false, telemetryEnabled = false;

    public void setDebugEnabled(boolean isEnabled) {
        this.debugEnabled = isEnabled;
    }

    public void setTelemetryEnabled(boolean isEnabled) {
        this.telemetryEnabled = isEnabled;
    }

    public boolean isDebugEnabled() {
        return debugEnabled;
    }

    public boolean isTelemetryEnabled() {
        return telemetryEnabled;
    }

}
