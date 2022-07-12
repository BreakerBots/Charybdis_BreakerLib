// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.subsystemcores.drivetrain;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import frc.robot.BreakerLib.devices.BreakerGenericDevice;
import frc.robot.BreakerLib.position.odometry.BreakerGenericOdometer;
import frc.robot.BreakerLib.util.BreakerRoboRIO.RobotMode;
import frc.robot.BreakerLib.util.selftest.BreakerSelfTestable;

/** Contianer class for methods common to all drivetrain types */
public interface BreakerGenericDrivetrain extends BreakerGenericOdometer, BreakerGenericDevice {

    public class BreakerAutomaticBreakModeConfig {
        private boolean breakInAuto;
        private boolean breakInTeleop;
        private boolean breakInTest;
        private boolean breakInDisabled;
        public BreakerAutomaticBreakModeConfig(boolean breakInTeleop, boolean breakInAuto, boolean breakInTest, boolean breakInDisabled) {
            this.breakInAuto = breakInAuto;
            this.breakInDisabled = breakInDisabled;
            this.breakInTeleop = breakInTeleop;
            this.breakInTest = breakInTest;
        }

        public BreakerAutomaticBreakModeConfig(boolean breakForAll) {
            breakInAuto = breakForAll;
            breakInDisabled = breakForAll;
            breakInTeleop = breakForAll;
            breakInTest = breakForAll;
        }

        public BreakerAutomaticBreakModeConfig() {
            breakInAuto = true;
            breakInDisabled = false;
            breakInTeleop = true;
            breakInTest = true;
        }

        public boolean getBreakModeForRobotMode(RobotMode currentMode) {
            switch(currentMode) {
                case AUTONOMOUS:
                    return breakInAuto;
                case DISABLED:
                    return breakInDisabled;
                case TELEOP:
                    return breakInTeleop;
                case TEST:
                    return breakInTest;
                case UNKNOWN:
                default:
                    return false; 
            }
        }

        public boolean getBreakInAuto() {
            return breakInAuto;
        }

        public boolean getBreakInDisabled() {
            return breakInDisabled;
        }

        public boolean getBreakInTeleop() {
            return breakInTeleop;
        }

        public boolean getBreakInTest() {
            return breakInTest;
        }
    }
    
    /** Updates the odometer position. */
    public abstract void updateOdometry();

    public abstract void setDrivetrainBrakeMode(boolean isEnabled);

    public abstract void setSlowMode(boolean isEnabled);

    public abstract boolean isInSlowMode();

    public abstract ChassisSpeeds getFieldRelativeChassisSpeeds(BreakerGenericOdometer odometer);

}
