// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.devices.sensors;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Ultrasonic;

/** Class for ultrasonic sensor. */
public class BreakerUltrasonicSensor {

    private DigitalOutput pingCh;
    private DigitalInput echoCh;
    private Ultrasonic sonic;
    private double offsetMM;

    public BreakerUltrasonicSensor(int pingOutChannelDIO, int echoInChannelDIO) {
        pingCh = new DigitalOutput(pingOutChannelDIO);
        echoCh = new DigitalInput(echoInChannelDIO);
        sonic = new Ultrasonic(pingCh, echoCh);
        Ultrasonic.setAutomaticMode(true);
    }

    public BreakerUltrasonicSensor(int pingOutChannelDIO, int echoInChannelDIO, double offsetMM) {
        pingCh = new DigitalOutput(pingOutChannelDIO);
        echoCh = new DigitalInput(echoInChannelDIO);
        sonic = new Ultrasonic(pingCh, echoCh);
        Ultrasonic.setAutomaticMode(true);
    }

    public double getRangeMM() {
        return sonic.getRangeMM() - offsetMM;
    }

    public double getRangeInches() {
        return sonic.getRangeInches() - Units.metersToInches(offsetMM/1000);
    }
}
