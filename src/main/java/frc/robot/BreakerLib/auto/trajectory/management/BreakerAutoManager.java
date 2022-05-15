// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.auto.trajectory.management;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.WidgetType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.BreakerLib.driverstation.BreakerDashboard;

/** Class that manages all avalable autopaths for dashboard chosser */
public class BreakerAutoManager {
    private BreakerAutoPath[] autoPaths;
    private SendableChooser<BreakerAutoPath> selector;

    public BreakerAutoManager(BreakerAutoPath...autoPaths) {
        this.autoPaths = autoPaths;
        selector.addOption("Do Nouthing", null);
        for (BreakerAutoPath path: autoPaths) {
            selector.addOption(path.getPathName(), path);
        }
        BreakerDashboard.getSetupTab().add("AUTOPATH SELECTOR", selector).withWidget(BuiltInWidgets.kComboBoxChooser);
    }

    public BreakerAutoPath getSelected() {
        return selector.getSelected();
    }

    public SequentialCommandGroup getSelectedBaseCommandGroup() {
        return getSelected().getBaseCommandGroup();
    }
}
