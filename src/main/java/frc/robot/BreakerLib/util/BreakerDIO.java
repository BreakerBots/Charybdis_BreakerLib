// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;

/** Add your docs here. */
public class BreakerDIO {

    public enum DIOState {
        DIGITAL_IN,
        DIGITAL_OUT,
        PWM_OUT
    }

    private DigitalInput input;
    private DigitalOutput output;
    private DIOState state = DIOState.DIGITAL_IN;
    private int channel;
    public BreakerDIO(int channel) {
        input = new DigitalInput(channel);
        output = new DigitalOutput(channel);
        this.channel = channel;
    }

    public boolean getInput() {
        output.disablePWM();
        state = DIOState.DIGITAL_IN;
        return input.get();
    }

    public boolean getOutput() {
        state = DIOState.DIGITAL_OUT;
        return output.get();
    }

    public void setOutput(boolean value) {
        output.disablePWM();
        output.set(value);
    }

    public void setOutputPWM(double dutyCycle) {
        state = DIOState.PWM_OUT;
        output.enablePWM(dutyCycle);
    }

    public DIOState getState() {
        return state;
    }

    public DigitalInput getBaseInput() {
        return input;
    }

    public DigitalOutput getBaseOutput() {
        return output;
    }

    public int getChannel() {
        return channel;
    }

}