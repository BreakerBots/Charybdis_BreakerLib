// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.devices.misic;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/** Version of a DoubleSolenoid with internal state control. */
public class BreakerDoubleSolenoid {
    private DoubleSolenoid solenoid;
    private Value defaultState = Value.kOff;

    public BreakerDoubleSolenoid(PneumaticsModuleType moduleType, int fwdChannel, int revChannel) {
        solenoid = new DoubleSolenoid(moduleType, fwdChannel, revChannel);
        toDefaultState();
    }

    public BreakerDoubleSolenoid(PneumaticsModuleType moduleType, int fwdChannel, int revChannel, int pcmID) {
        solenoid = new DoubleSolenoid(pcmID, moduleType, fwdChannel, revChannel);
        toDefaultState();
    }

    public BreakerDoubleSolenoid(PneumaticsModuleType moduleType, int fwdChannel, int revChannel, Value defaultState) {
        solenoid = new DoubleSolenoid(moduleType, fwdChannel, revChannel);
        setDefaultState(defaultState);
        toDefaultState();
    }

    public BreakerDoubleSolenoid(PneumaticsModuleType moduleType, int fwdChannel, int revChannel, int pcmID, Value defaultState) {
        solenoid = new DoubleSolenoid(pcmID, moduleType, fwdChannel, revChannel);
        setDefaultState(defaultState);
        toDefaultState();
    }

    public void setDefaultState(Value value) {
        defaultState = value;
    }

    public void toDefaultState() {
        if (solenoid.get() != defaultState) {
            solenoid.set(defaultState);
        }
    }

    public void set(Value value) {
        if (solenoid.get() != value) {
            solenoid.set(value);
        }
    }

    public Value getState() {
        return solenoid.get();
    }
}
