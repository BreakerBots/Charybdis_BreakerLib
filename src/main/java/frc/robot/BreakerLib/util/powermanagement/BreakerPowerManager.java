// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util.powermanagement;

import java.util.List;
import java.util.TreeMap;

import edu.wpi.first.hal.simulation.PowerDistributionDataJNI;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;

/** Add your docs here. */
public class BreakerPowerManager {
    private static PowerDistribution distributor = new PowerDistribution();
    private static double batteryPercentageRemaining;
    private static double batteryJoulesExpended;
    private static double batteryJoulesRemaining;
    private static final double fullBatteryCapacityJoules = 2592000;
    private static List<BreakerPowerChannel> channelList = BreakerPowerUtil.getNewPowerChannelList(distributor.getType());

    public static void setDistribuionModule(int moduleID, ModuleType moduleType ) {
        distributor = new PowerDistribution(moduleID, moduleType);
        channelList = BreakerPowerUtil.getNewPowerChannelList(moduleType);
    }

    public static PowerDistribution getPowerDistributor() {
        return distributor;
    }

    public static double getBatteryVoltage() {
        return distributor.getVoltage();
    }

    public static double getTotalEnergyUsed() {
        return distributor.getTotalEnergy();
    }

    public static void resetTotalEnergyUsed() {
        distributor.resetTotalEnergy();
    }

    public static double getFullbatterycapacityJoules() {
        return fullBatteryCapacityJoules;
    }

    // returns the battersys remaing energey as a perentage 0 to 1
    public static double getRemainingBatteryPercentage() {
        double invPercent = distributor.getTotalEnergy() / fullBatteryCapacityJoules;
        return 1d - invPercent;
    }



}
