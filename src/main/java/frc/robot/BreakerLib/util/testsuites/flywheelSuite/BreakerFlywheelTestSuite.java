// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util.testsuites.flywheelSuite;

import frc.robot.BreakerLib.subsystemcores.shooter.BreakerFlywheel;
import frc.robot.BreakerLib.subsystemcores.shooter.BreakerFlywheelConfig;
import frc.robot.BreakerLib.util.testsuites.BreakerTestBase;
import frc.robot.BreakerLib.util.testsuites.BreakerTestSuiteDataLogType;

/** Add your docs here. */
public class BreakerFlywheelTestSuite {
    private BreakerFlywheel baseFlywheel;
    private BreakerTestSuiteDataLogType logType;
    private BreakerFlywheelTestSuite(BreakerFlywheel baseFlywheel) {
        this.baseFlywheel = baseFlywheel;
    }

    public void setLogType(BreakerTestSuiteDataLogType newLogType) {
        logType = newLogType;
    }

    public BreakerTimedFlywheelSpinUpTest TimedSpinUpTest(double testTimeSeconds, double targetRPM) {
        BreakerTimedFlywheelSpinUpTest test = new BreakerTimedFlywheelSpinUpTest(testTimeSeconds, targetRPM, baseFlywheel, logType);
        test.schedule();
        return test;
    }

}
