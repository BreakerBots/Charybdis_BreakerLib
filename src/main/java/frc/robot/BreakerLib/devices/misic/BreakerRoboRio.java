// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.devices.misic;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BreakerLib.util.RobotMode;
import frc.robot.BreakerLib.util.math.BreakerUnits;

/** Add your docs here. */
public class BreakerRoboRio extends SubsystemBase {
    private static double prevTime = RobotController.getFPGATime();
    private static double diffTime = 0;
    private static RobotMode curMode = RobotMode.DISABLED;
    public BreakerRoboRio() {}

    private static double calculateInterCycleTime() {
        double curTime = RobotController.getFPGATime(); // In microseconds
        double diffTime = curTime - prevTime;
        prevTime = curTime;
        return BreakerUnits.microsecondsToSeconds(diffTime);
    }

    public static double getInterCycleTimeSeconds() {
        return diffTime;
    }

    public static long getRobotTimeMCRS() {
        return RobotController.getFPGATime();
    }

    public static double getRobotTimeSeconds() {
        return BreakerUnits.microsecondsToSeconds(RobotController.getFPGATime());
    }

    public static RobotMode getCurrentRobotMode() {
        return curMode;
    }

    public static void setCurrentRobotMode(RobotMode newMode) {
        curMode = newMode;
    }

    @Override
    public void periodic() {
        diffTime = calculateInterCycleTime();
    }
}
