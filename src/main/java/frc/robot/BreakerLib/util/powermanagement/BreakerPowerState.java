// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util.powermanagement;

/** Add your docs here. */
public class BreakerPowerState {
    private double currentBatteryPercentage;
    private double runningAvrageBatteryVoltage;
    public BreakerPowerState(double currentBatteryPercentage, double runningAvrageBatteryVoltage) {
        this.currentBatteryPercentage = currentBatteryPercentage;
        this.runningAvrageBatteryVoltage = runningAvrageBatteryVoltage;
    }

    public double getCurrentBatteryPercentage() {
        return currentBatteryPercentage;
    }

    public double getRunningAvrageBatteryVoltage() {
        return runningAvrageBatteryVoltage;
    }
}
