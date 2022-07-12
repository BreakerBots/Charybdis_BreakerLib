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
import frc.robot.BreakerLib.subsystemcores.drivetrain.BreakerGenericDrivetrain.BreakerAutomaticBreakModeConfig;
import frc.robot.BreakerLib.util.BreakerLog;
import frc.robot.BreakerLib.util.BreakerRoboRIO;
import frc.robot.BreakerLib.util.selftest.SelfTest;

/** Add your docs here. */
public class BreakerRobotManager extends SubsystemBase {
    private static SelfTest test;
    private static BreakerAutoManager autoManager;
    private static BreakerAutomaticBreakModeConfig breakModeConfig;
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
        BreakerRobotManager.breakModeConfig = robotConfig.getBreakModeConfig();
        BreakerLog.logRobotStarted(robotConfig.getStartConfig());
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

    @Override
    public void periodic() {
        if (BreakerRoboRIO.robotModeHasChanged()) {
            baseDrivetrain.setDrivetrainBrakeMode(breakModeConfig.getBreakModeForRobotMode(BreakerRoboRIO.getCurrentRobotMode()));
        }
    }
}
