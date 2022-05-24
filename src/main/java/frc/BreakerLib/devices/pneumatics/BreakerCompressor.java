package frc.BreakerLib.devices.pneumatics;

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.PneumaticHub;
import static edu.wpi.first.wpilibj.PneumaticsModuleType.*;

/**
 * Compressor with built-in AnalogPotentiometer and pneumatic module support.
 */
public class BreakerCompressor {

    private PneumaticsModuleType moduleType;

    private PneumaticsControlModule pcm;
    private PneumaticHub ph;

    private AnalogPotentiometer analogPressureSensor = null;
    private Compressor airCompressor;

    public BreakerCompressor(int moduleID, PneumaticsModuleType moduleType) {
        this.moduleType = moduleType;
        airCompressor = new Compressor(moduleID, moduleType);
        moduleSetup(moduleID);
    }

    public BreakerCompressor(PneumaticsModuleType type) {
        moduleType = type;
        airCompressor = new Compressor(moduleType);
        moduleSetup();
    }

    /** Creates PCM or PH in constructor. */
    private void moduleSetup(int id) {
        switch (moduleType) {
            case CTREPCM:
                pcm = new PneumaticsControlModule(id);
                break;
            case REVPH:
                ph = new PneumaticHub(id);
                break;
        }
    }

    /** Creates PCM or PH in constructor. */
    private void moduleSetup() {
        switch (moduleType) {
            case CTREPCM:
                pcm = new PneumaticsControlModule();
                break;
            case REVPH:
                ph = new PneumaticHub();
                break;
        }
    }

    /**
     * Creates an analog pressure sensor based on the REV Analog Pressure Sensor.
     */
    public void addAnalogPressureSensor(int analog_channel) {
        analogPressureSensor = new AnalogPotentiometer(analog_channel, 250, -25);
    }

    /** Creates an analog pressure sensor with given full range and offset. */
    public void addAnalogPressureSensor(int analog_channel, double fullRange, double offset) {
        analogPressureSensor = new AnalogPotentiometer(analog_channel, fullRange, offset);
    }

    /**
     * Returns psi measured by analog pressure sensor. If pressure sensor not made,
     * returns -1.
     */
    public double getPressure() {
        double val = analogPressureSensor == null ? -1 : analogPressureSensor.get();
        return val;
    }

}
