// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util.robotmanager;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.BreakerLib.auto.trajectory.management.BreakerAutoManager;
import frc.robot.BreakerLib.subsystem.cores.drivetrain.autobrake.BreakerAutomaticBrakeModeManager;
import frc.robot.BreakerLib.subsystem.cores.drivetrain.autobrake.BreakerAutomaticBrakeModeManagerConfig;
import frc.robot.BreakerLib.util.loging.BreakerLog;
import frc.robot.BreakerLib.util.selftest.SelfTest;
import frc.robot.BreakerLib.subsystem.cores.drivetrain.BreakerGenericDrivetrain;

/**
 * Robot manager that configures SelfTest functionality, automatic brake mode,
 * and auto paths.
 */
public class BreakerRobotManager {
    private static SelfTest test;
    private static BreakerAutoManager autoManager;
    private static BreakerAutomaticBrakeModeManager breakModeManager;
    private static BreakerGenericDrivetrain baseDrivetrain;
    private static final BreakerRobotManager robotManager = new BreakerRobotManager();

    private BreakerRobotManager() {
    }

    /** Setup for the BreakerRobotManager.
     * 
     * @param baseDrivetrain Base drivetrain.
     * @param robotConfig Robot configuration.
     */
    public static void setup(BreakerGenericDrivetrain baseDrivetrain, BreakerRobotConfig robotConfig) {
        if (robotConfig.UsesOrchestra()) {
            BreakerLog.startLog(robotConfig.getAutologNetworkTables(), robotConfig.getOrchestra());
            test = new SelfTest(robotConfig.getSecondsBetweenSelfChecks(), robotConfig.getSelftestServerAddress(),
                    robotConfig.getOrchestra(), robotConfig.getAutoRegesterDevices());
        } else {
            BreakerLog.startLog(robotConfig.getAutologNetworkTables());
            test = new SelfTest(robotConfig.getSecondsBetweenSelfChecks(), robotConfig.getSelftestServerAddress(),
                    robotConfig.getAutoRegesterDevices());
        }
        BreakerRobotManager.baseDrivetrain = baseDrivetrain;
        BreakerRobotManager.autoManager = robotConfig.UsesPaths() ? new BreakerAutoManager(robotConfig.getAutoPaths())
                : new BreakerAutoManager();
        BreakerRobotManager.breakModeManager = new BreakerAutomaticBrakeModeManager(
                new BreakerAutomaticBrakeModeManagerConfig(baseDrivetrain));
        BreakerLog.logRobotStarted(robotConfig.getStartConfig());
    }

    public static BreakerAutomaticBrakeModeManager getAutomaticBreakModeManager() {
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
}
