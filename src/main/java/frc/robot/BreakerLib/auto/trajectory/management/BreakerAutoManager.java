// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.auto.trajectory.management;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.WidgetType;
import frc.robot.BreakerLib.util.BreakerDashboard;

/** Class that manages all avalable autopaths for dashboard chosser */
public class BreakerAutoManager {
    private BreakerAutoPath[] autoPaths;
    private NetworkTableEntry selectedPath;
    public BreakerAutoManager(BreakerAutoPath...autoPaths) {
        this.autoPaths = autoPaths;
    }
}
