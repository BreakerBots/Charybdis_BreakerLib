// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.devices.motors;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;

import frc.robot.BreakerLib.devices.BreakerGenericDeviceBase;
import frc.robot.BreakerLib.util.BreakerCTREUtil;
import frc.robot.BreakerLib.util.BreakerTriplet;
import frc.robot.BreakerLib.util.power.BreakerPowerManagementConfig;
import frc.robot.BreakerLib.util.power.DevicePowerMode;
import frc.robot.BreakerLib.util.test.selftest.DeviceHealth;
import frc.robot.BreakerLib.util.test.selftest.SelfTest;

/** Falcon motor with simple on/off controls */
public class BreakerBinaryCTREMotor<T extends BaseMotorController> extends BreakerGenericDeviceBase {

    private T motor;
    private double output;

    /**
     * Create a new BinaryCTREMotor that switches between 100% forward output and 0%
     * output.
     * 
     * @param motor CTRE motor controller.
     */
    public BreakerBinaryCTREMotor(T motor) {
        this.motor = motor;
        output = 1;
        deviceName = " Binary_Motor (" + motor.getDeviceID() + ") ";
    }

    /**
     * Create a new BinaryCTREMotor that switches between given output % and 0%
     * output.
     * 
     * @param motor  CTRE motor controller.
     * @param output Percent output between -1 and 1.
     */
    public BreakerBinaryCTREMotor(T motor, double output) {
        this.motor = motor;
        this.output = output;
        deviceName = " Binary_Motor (" + motor.getDeviceID() + ") ";
    }

    /** Sets motor to designated percent output. */
    public void start() {
        motor.set(ControlMode.PercentOutput, output);
    }

    /** Sets motor to 0% output (stopped) */
    public void stop() {
        motor.set(ControlMode.PercentOutput, 0);
    }

    /** Checks if motor is running or not. */
    public boolean isActive() {
        return (motor.getMotorOutputPercent() != 0);
    }

    public T getMotor() {
        return motor;
    }

    @Override
    public void runSelfTest() {
        faultStr = null;
        health = DeviceHealth.NOMINAL;
        Faults faultObj = new Faults();
        motor.getFaults(faultObj);
        if (faultObj.hasAnyFault() || SelfTest.checkIsMissingCanID(motor.getDeviceID())) {
            BreakerTriplet<DeviceHealth, String, Boolean> trip = BreakerCTREUtil
                    .getMotorHealthFaultsAndConnectionStatus(faultObj, motor.getDeviceID());
            faultStr = trip.getMiddle();
            health = trip.getLeft();
        }
    }

    @Override
    public DevicePowerMode managePower(BreakerPowerManagementConfig managementConfig) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void overrideAutomaticPowerManagement(DevicePowerMode manualPowerMode) {
        // TODO Auto-generated method stub

    }

    @Override
    public void returnToAutomaticPowerManagement() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isUnderAutomaticControl() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public DevicePowerMode getPowerMode() {
        // TODO Auto-generated method stub
        return null;
    }

}
