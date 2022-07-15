// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util.robotmanager;

import com.ctre.phoenix.music.Orchestra;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BreakerLib.auto.trajectory.management.BreakerAutoManager;
import frc.robot.BreakerLib.auto.trajectory.management.BreakerAutoPath;
import frc.robot.BreakerLib.devices.cosmetic.music.BreakerFalconOrchestra;
import frc.robot.BreakerLib.factories.BreakerCANCoderFactory;
import frc.robot.BreakerLib.subsystemcores.drivetrain.BreakerGenericDrivetrain;
import frc.robot.BreakerLib.subsystemcores.drivetrain.automaticbreakmanagement.BreakerAutomaticBreakModeManager;
import frc.robot.BreakerLib.subsystemcores.drivetrain.automaticbreakmanagement.BreakerAutomaticBreakModeManagerConfig;
import frc.robot.BreakerLib.util.BreakerLog;
import frc.robot.BreakerLib.util.BreakerRoboRIO;
import frc.robot.BreakerLib.util.selftest.SelfTest;

/** Add your docs here. */
public class BreakerRobotManager extends SubsystemBase {
    private static SelfTest test;
    private static BreakerAutoManager autoManager;
    private static BreakerAutomaticBreakModeManager breakModeManager;
    private static BreakerGenericDrivetrain baseDrivetrain;
    private static final BreakerRobotManager robotManager = new BreakerRobotManager();


    private BreakerRobotManager() {}

    public static void setup(BreakerGenericDrivetrain baseDrivetrain, BreakerRobotConfig robotConfig) {
        if (robotConfig.UsesOrchestra()) { 
            BreakerLog.startLog(robotConfig.getAutologNetworkTables(), robotConfig.getOrchestra());
            test = new SelfTest(robotConfig.getSecondsBetweenSelfChecks(), robotConfig.getSelftestServerAddress(), robotConfig.getOrchestra(), robotConfig.getAutoRegesterDevices());
        } else {
            BreakerLog.startLog(robotConfig.getAutologNetworkTables());
            test = new SelfTest(robotConfig.getSecondsBetweenSelfChecks(), robotConfig.getSelftestServerAddress(), robotConfig.getAutoRegesterDevices());
        }
        BreakerRobotManager.baseDrivetrain = baseDrivetrain;
        BreakerRobotManager.autoManager = robotConfig.UsesPaths() ? new BreakerAutoManager(robotConfig.getAutoPaths()) : new BreakerAutoManager();
        BreakerRobotManager.breakModeManager = new BreakerAutomaticBreakModeManager(new BreakerAutomaticBreakModeManagerConfig(baseDrivetrain));
        BreakerLog.logRobotStarted(robotConfig.getStartConfig());
    }

    public static void setCustomAutomaticBreakModeConfig(BreakerAutomaticBreakModeManagerConfig config) {
        BreakerRobotManager.breakModeManager = new BreakerAutomaticBreakModeManager(config);
    }

    public static BreakerAutomaticBreakModeManager getAutomaticBreakModeManager() {
        return breakModeManager;
    }

    public static SelfTest getSelfTest() {
        return test;
    }

    public static BreakerAutoManager getAutoManager() {
        return autoManager;
    }

    public static SequentialCommandGroup getSelectedAutoPath() {
        return autoManager.getSelectedBaseCommandGroup();
    }

    public static void setDrivetrainBreakMode(boolean isEnabled) {
        baseDrivetrain.setDrivetrainBrakeMode(isEnabled);
    }

    @Override
    public void periodic() {
        
    }
}
