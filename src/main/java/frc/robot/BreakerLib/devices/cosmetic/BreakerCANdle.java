// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.devices.cosmetic;

import com.ctre.phoenix.led.CANdle;
import com.ctre.phoenix.led.RainbowAnimation;
import com.ctre.phoenix.led.StrobeAnimation;

import edu.wpi.first.wpilibj.util.Color;

/** Add your docs here. */

public class BreakerCANdle {
    private CANdle candle;
    private RainbowAnimation enabledStatus;
    private StrobeAnimation errorStatus;
    int state1 = 0;
    int state2 = 0;
    int cycleCount1 = 0;
    int cycleCount2 = 0;
    public BreakerCANdle (int canID, int numberOfLEDs, BreakerCANdleConfig config) {
        candle = new CANdle(canID);
        candle.configAllSettings(config.getConfig());
        candle.setLEDs(255, 255, 255);
        enabledStatus = new RainbowAnimation(1, 0.5, numberOfLEDs);
        errorStatus = new StrobeAnimation(255, 0, 0, 0, 0.5, numberOfLEDs);
    }

    public void setRobotEnabledStatusLED() {
        candle.animate(enabledStatus);
    }

    public void setLED(Color ledColor) {
        int cRed = (int) (ledColor.red * 255);
        int cBlue = (int) (ledColor.blue * 255);
        int cGreen = (int) (ledColor.green * 255);
        candle.setLEDs(cRed, cGreen, cBlue);
    }

    public void setLED(int red, int green, int blue) {
        candle.setLEDs(red, green, blue);
    }

    public void runErrorStatusLED() {
        candle.animate(errorStatus);
    }

    public void runSpesificErrorStatusLED(Color errorColor) {
        int eRed = (int) (errorColor.red * 255);
        int eBlue = (int) (errorColor.blue * 255);
        int eGreen = (int) (errorColor.green * 255);
        switch (state1) {
            case 0: 
                candle.setLEDs(255, 0, 0);
                cycleCount1 ++;
                if (cycleCount1 >= 50) {
                    cycleCount1 = 0;
                    state1 = 1;
                }
            break;
            default:
            case 1:
                candle.setLEDs(eRed, eGreen, eBlue);
                cycleCount1 ++;
                if (cycleCount1 >= 50) {
                    cycleCount1 = 0;
                    state1 = 0;
                }
        }
    }
}
