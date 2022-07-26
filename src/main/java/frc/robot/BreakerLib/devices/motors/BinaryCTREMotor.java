// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.devices.motors;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import frc.robot.BreakerLib.devices.BreakerGenericDevice;
import frc.robot.BreakerLib.util.powermanagement.BreakerPowerManagementConfig;
import frc.robot.BreakerLib.util.powermanagement.DevicePowerMode;
import frc.robot.BreakerLib.util.selftest.DeviceHealth;

/** Falcon motor with simple on/off controls */
public class BinaryCTREMotor <T extends BaseMotorController> implements BreakerGenericDevice {

    private T motor;
    private double output;

    /**
     * Create a new BinaryCTREMotor that switches between 100% and 0% output.
     * 
     * @param motor TalonFX or TalonSRX motor controller.
     */
    public BinaryCTREMotor(T motor) {
        this.motor = motor;
        output = 1;
    }

    /**
     * Create a new BinaryCTREMotor that switches between given output % and 0% output.
     * 
     * @param motor TalonFX or TalonSRX motor controller.
     * @param output Percent output between -1 and 1.
     */
    public BinaryCTREMotor(T motor, double output) {
        this.motor = motor;
        this.output = output;
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
        // TODO Auto-generated method stub
        
    }

    @Override
    public DeviceHealth getHealth() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getFaults() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDeviceName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasFault() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void setDeviceName(String newName) {
        // TODO Auto-generated method stub
        
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
