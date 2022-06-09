// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util.powermanagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import edu.wpi.first.hal.simulation.PowerDistributionDataJNI;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.PerpetualCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** Add your docs here. */
public class BreakerPowerManager extends SubsystemBase {
    private static PowerDistribution distributor = new PowerDistribution();
    private static double batteryPercentageRemaining;
    private static double batteryJoulesExpended;
    private static double batteryJoulesRemaining;
    private static final double fullBatteryCapacityJoules = 2592000;
    private static List<BreakerPowerChannel> channelList = BreakerPowerUtil.getNewPowerChannelList(distributor.getType());
    private static TreeMap<BreakerPowerManageable, BreakerPowerManagementConfig> devicesAndConfigs = new TreeMap<>();
    private BreakerPowerManager manager = new BreakerPowerManager();

    private BreakerPowerManager() {}

    public static void setDistribuionModule(int moduleID, ModuleType moduleType ) {
        distributor = new PowerDistribution(moduleID, moduleType);
        channelList = BreakerPowerUtil.getNewPowerChannelList(moduleType);
    }

    public static void register(BreakerPowerManageable powerManageableDevice, BreakerPowerManagementConfig config) {
        devicesAndConfigs.put(powerManageableDevice, config);
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

    public static BreakerPowerChannel getChannel(int channelNum) {
        return channelList.get(channelNum);
    }

    // returns the battery's remaing energy as a fractional perentage 0 to 1
    public static double getRemainingBatteryPercentage() {
        double invPercent = distributor.getTotalEnergy() / fullBatteryCapacityJoules;
        return 1d - invPercent;
    }

    private void managePower() {
        for (Entry<BreakerPowerManageable, BreakerPowerManagementConfig> entry: devicesAndConfigs.entrySet()) {
           DevicePowerMode powerMode = entry.getKey().calculatePowerMode(entry.getValue(), getRemainingBatteryPercentage());
           entry.getKey().setPowerMode(entry.getKey().isUnderAutomaticControl() ? powerMode : entry.getKey().getPowerMode());
        }
    }

    @Override
    public void periodic() {
        managePower();
    }



}
