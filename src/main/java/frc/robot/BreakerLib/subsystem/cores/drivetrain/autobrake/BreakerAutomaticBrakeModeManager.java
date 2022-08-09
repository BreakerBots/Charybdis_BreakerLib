// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.subsystem.cores.drivetrain.autobrake;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BreakerLib.subsystem.cores.drivetrain.BreakerGenericDrivetrain;
import frc.robot.BreakerLib.util.BreakerRoboRIO;
import frc.robot.BreakerLib.util.BreakerRoboRIO.RobotMode;

/** Add your docs here. */
public class BreakerAutomaticBrakeModeManager extends SubsystemBase {
        private boolean breakInAuto;
        private boolean breakInTeleop;
        private boolean breakInTest;
        private boolean breakInDisabled;
        private boolean autoBreakIsEnabled;
        private BreakerGenericDrivetrain baseDrivetrain;
        public BreakerAutomaticBrakeModeManager(BreakerAutomaticBrakeModeManagerConfig config) {
            breakInAuto = config.getBreakInAuto();
            breakInTeleop = config.getBreakInTeleop();
            breakInTest = config.getBreakInTest();
            breakInDisabled = config.getBreakInDisabled();
            baseDrivetrain = config.getBaseDrivetrain();
        }

        public void changeConfig(BreakerAutomaticBrakeModeManagerConfig config) {
            breakInAuto = config.getBreakInAuto();
            breakInTeleop = config.getBreakInTeleop();
            breakInTest = config.getBreakInTest();
            breakInDisabled = config.getBreakInDisabled();
            baseDrivetrain = config.getBaseDrivetrain();
        }

        public boolean isAutomaticBreakModeEnabled() {
            return autoBreakIsEnabled;
        }

        public void setAutomaticBreakModeEnabled(Boolean isEnabled) {
            autoBreakIsEnabled = isEnabled;
        }

        public void setAutomaticBreakMode() {
            switch(BreakerRoboRIO.getCurrentRobotMode()) {
                case AUTONOMOUS:
                    baseDrivetrain.setDrivetrainBrakeMode(breakInAuto);
                    break;
                case DISABLED:
                    baseDrivetrain.setDrivetrainBrakeMode(breakInDisabled);
                    break;
                case TELEOP:
                    baseDrivetrain.setDrivetrainBrakeMode(breakInTeleop);
                    break;
                case TEST:
                    baseDrivetrain.setDrivetrainBrakeMode(breakInTest);
                    break;
                case UNKNOWN:
                default:
                    baseDrivetrain.setDrivetrainBrakeMode(false);
                    break;
               
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

        @Override
        public void periodic() {
            if (autoBreakIsEnabled) {
                setAutomaticBreakMode();
            }
        }
}
