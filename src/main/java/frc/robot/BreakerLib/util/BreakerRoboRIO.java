
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BreakerLib.util.math.BreakerUnits;
import edu.wpi.first.wpilibj.RobotState;

/**
 * RoboRIO wrapper class utilizing Subsystem framework. Manages time and robot
 * mode.
 */
public class BreakerRoboRIO extends SubsystemBase {

    /** Operating modes for robot. */
    public enum RobotMode {
        DISABLED,
        AUTONOMOUS,
        TELEOP,
        TEST
    }

    private static double prevTime = RobotController.getFPGATime();
    private static double diffTime = 0;
    private static RobotMode curMode = RobotMode.DISABLED;
    private static BreakerRoboRIO roboRIO = new BreakerRoboRIO();

    private BreakerRoboRIO() {
    }

    /** Calculates time between cycles. Run periodically. */
    private double calculateInterCycleTime() {
        double curTime = RobotController.getFPGATime(); // In microseconds
        double diffTime = curTime - prevTime;
        prevTime = curTime;
        return BreakerUnits.microsecondsToSeconds(diffTime);
    }

    /** Returns time between cycles in seconds. */
    public static double getInterCycleTimeSeconds() {
        return diffTime;
    }

    /** Returns time as long of microseconds??? */
    public static long getRobotTimeMCRS() {
        return RobotController.getFPGATime();
    }

    /** Operating time of robot in seconds. */
    public static double getRobotTimeSeconds() {
        return BreakerUnits.microsecondsToSeconds(RobotController.getFPGATime());
    }

    /** Returns current operating mode of robot. */
    public static RobotMode getCurrentRobotMode() {
        return curMode;
    }

    /** Use in {@link Robot} class's init methods to update robot mode. */
    public static void setCurrentRobotMode(RobotMode newMode) {
        curMode = newMode;
    }

    @Override
    public void periodic() {
        diffTime = calculateInterCycleTime();
    }
}
