// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util.robotmanager;

import frc.robot.BreakerLib.auto.trajectory.management.BreakerAutoPath;
import frc.robot.BreakerLib.devices.cosmetic.music.BreakerFalconOrchestra;
import frc.robot.BreakerLib.subsystemcores.drivetrain.BreakerGenericDrivetrain.BreakerAutomaticBreakModeConfig;

/** Add your docs here. */
public class BreakerRobotConfig {
    private double secondsBetweenSelfChecks;
    private boolean autologNetworkTables;
    private String selftestServerAddress;
    private boolean autoRegesterDevices;
    private BreakerFalconOrchestra orchestra;
    private boolean usesOrchestra;
    private BreakerAutoPath[] autoPaths;
    private boolean usesPaths;
    private BreakerRobotStartConfig startConfig;
    private BreakerAutomaticBreakModeConfig breakModeConfig;

    public BreakerRobotConfig(double secondsBetweenSelfChecks, String selftestServerAddress, boolean autoRegesterDevices, boolean autologNetworkTables, BreakerRobotStartConfig startConfig, BreakerFalconOrchestra orchestra, BreakerAutomaticBreakModeConfig breakModeConfig, BreakerAutoPath... autoPaths) {
        this.secondsBetweenSelfChecks = secondsBetweenSelfChecks;
        this.autologNetworkTables = autologNetworkTables;
        this.selftestServerAddress = selftestServerAddress;
        this.orchestra = orchestra;
        this.autoPaths = autoPaths;
        this.autoRegesterDevices = autoRegesterDevices;
        this.startConfig = startConfig;
        this.breakModeConfig = breakModeConfig;
        usesOrchestra = true;
        usesPaths = true;
    }

    public BreakerRobotConfig(double secondsBetweenSelfChecks, String selftestServerAddress, boolean autoRegesterDevices, boolean autologNetworkTables, BreakerRobotStartConfig startConfig, BreakerFalconOrchestra orchestra, BreakerAutoPath... autoPaths) {
        this.secondsBetweenSelfChecks = secondsBetweenSelfChecks;
        this.autologNetworkTables = autologNetworkTables;
        this.selftestServerAddress = selftestServerAddress;
        this.orchestra = orchestra;
        this.autoPaths = autoPaths;
        this.autoRegesterDevices = autoRegesterDevices;
        this.startConfig = startConfig;
        breakModeConfig = new BreakerAutomaticBreakModeConfig();
        usesOrchestra = true;
        usesPaths = true;
    }

    public BreakerRobotConfig(double secondsBetweenSelfChecks, String selftestServerAddress, boolean autologNetworkTables, BreakerRobotStartConfig startConfig, BreakerFalconOrchestra orchestra, BreakerAutomaticBreakModeConfig breakModeConfig) {
        this.secondsBetweenSelfChecks = secondsBetweenSelfChecks;
        this.autologNetworkTables = autologNetworkTables;
        this.selftestServerAddress = selftestServerAddress;
        this.orchestra = orchestra;
        this.autoPaths = new BreakerAutoPath[0];
        this.startConfig = startConfig;
        this.breakModeConfig = breakModeConfig;
        usesOrchestra = true;
        usesPaths = false;
    }

    public BreakerRobotConfig(double secondsBetweenSelfChecks, String selftestServerAddress, boolean autologNetworkTables, BreakerRobotStartConfig startConfig, BreakerFalconOrchestra orchestra) {
        this.secondsBetweenSelfChecks = secondsBetweenSelfChecks;
        this.autologNetworkTables = autologNetworkTables;
        this.selftestServerAddress = selftestServerAddress;
        this.orchestra = orchestra;
        this.autoPaths = new BreakerAutoPath[0];
        this.startConfig = startConfig;
        breakModeConfig = new BreakerAutomaticBreakModeConfig();
        usesOrchestra = true;
        usesPaths = false;
    }

    public BreakerRobotConfig(double secondsBetweenSelfChecks, String selftestServerAddress, boolean autologNetworkTables, BreakerRobotStartConfig startConfig, BreakerAutomaticBreakModeConfig breakModeConfig, BreakerAutoPath... autoPaths) {
        this.secondsBetweenSelfChecks = secondsBetweenSelfChecks;
        this.autologNetworkTables = autologNetworkTables;
        this.selftestServerAddress = selftestServerAddress;
        this.orchestra = new BreakerFalconOrchestra();
        this.autoPaths = autoPaths;
        this.startConfig = startConfig;
        this.breakModeConfig = breakModeConfig;
        usesOrchestra = false;
        usesPaths = true;
    }

    public BreakerRobotConfig(double secondsBetweenSelfChecks, String selftestServerAddress, boolean autologNetworkTables, BreakerRobotStartConfig startConfig, BreakerAutoPath... autoPaths) {
        this.secondsBetweenSelfChecks = secondsBetweenSelfChecks;
        this.autologNetworkTables = autologNetworkTables;
        this.selftestServerAddress = selftestServerAddress;
        this.orchestra = new BreakerFalconOrchestra();
        this.autoPaths = autoPaths;
        this.startConfig = startConfig;
        breakModeConfig = new BreakerAutomaticBreakModeConfig();
        usesOrchestra = false;
        usesPaths = true;
    }

    public BreakerRobotConfig(double secondsBetweenSelfChecks, String selftestServerAddress, boolean autologNetworkTables, BreakerRobotStartConfig startConfig, BreakerAutomaticBreakModeConfig breakModeConfig) {
        this.secondsBetweenSelfChecks = secondsBetweenSelfChecks;
        this.autologNetworkTables = autologNetworkTables;
        this.selftestServerAddress = selftestServerAddress;
        this.orchestra = new BreakerFalconOrchestra();
        this.autoPaths = new BreakerAutoPath[0];
        this.startConfig = startConfig;
        this.breakModeConfig = breakModeConfig;
        usesOrchestra = false;
        usesPaths = false;
    }

    public BreakerRobotConfig(double secondsBetweenSelfChecks, String selftestServerAddress, boolean autologNetworkTables, BreakerRobotStartConfig startConfig) {
        this.secondsBetweenSelfChecks = secondsBetweenSelfChecks;
        this.autologNetworkTables = autologNetworkTables;
        this.selftestServerAddress = selftestServerAddress;
        this.orchestra = new BreakerFalconOrchestra();
        this.autoPaths = new BreakerAutoPath[0];
        this.startConfig = startConfig;
        breakModeConfig = new BreakerAutomaticBreakModeConfig();
        usesOrchestra = false;
        usesPaths = false;
    }

    public BreakerRobotConfig(BreakerRobotStartConfig startConfig, BreakerFalconOrchestra orchestra, BreakerAutomaticBreakModeConfig breakModeConfig, BreakerAutoPath... autoPaths) {
        this.secondsBetweenSelfChecks = 5;
        this.autologNetworkTables = false;
        this.selftestServerAddress = "172.22.11.2";
        this.autoRegesterDevices = true;
        this.orchestra = orchestra;
        this.autoPaths = autoPaths;
        this.startConfig = startConfig;
        this.breakModeConfig = breakModeConfig;
        usesOrchestra = true;
        usesPaths = true;
    }

    public BreakerRobotConfig(BreakerRobotStartConfig startConfig, BreakerFalconOrchestra orchestra, BreakerAutoPath... autoPaths) {
        this.secondsBetweenSelfChecks = 5;
        this.autologNetworkTables = false;
        this.selftestServerAddress = "172.22.11.2";
        this.autoRegesterDevices = true;
        this.orchestra = orchestra;
        this.autoPaths = autoPaths;
        this.startConfig = startConfig;
        breakModeConfig = new BreakerAutomaticBreakModeConfig();
        usesOrchestra = true;
        usesPaths = true;
    }

    public BreakerRobotConfig(BreakerRobotStartConfig startConfig, BreakerFalconOrchestra orchestra, BreakerAutomaticBreakModeConfig breakModeConfig) {
        this.secondsBetweenSelfChecks = 5;
        this.autologNetworkTables = false;
        this.selftestServerAddress = "172.22.11.2";
        this.autoRegesterDevices = true;
        this.orchestra = orchestra;
        this.autoPaths = new BreakerAutoPath[0];
        this.startConfig = startConfig;
        this.breakModeConfig = breakModeConfig;
        usesOrchestra = true;
        usesPaths = false;
    }

    public BreakerRobotConfig(BreakerRobotStartConfig startConfig, BreakerFalconOrchestra orchestra) {
        this.secondsBetweenSelfChecks = 5;
        this.autologNetworkTables = false;
        this.selftestServerAddress = "172.22.11.2";
        this.autoRegesterDevices = true;
        this.orchestra = orchestra;
        this.autoPaths = new BreakerAutoPath[0];
        this.startConfig = startConfig;
        breakModeConfig = new BreakerAutomaticBreakModeConfig();
        usesOrchestra = true;
        usesPaths = false;
    }

    public BreakerRobotConfig(BreakerRobotStartConfig startConfig,  BreakerAutomaticBreakModeConfig breakModeConfig, BreakerAutoPath... autoPaths) {
        this.secondsBetweenSelfChecks = 5;
        this.autologNetworkTables = false;
        this.selftestServerAddress = "172.22.11.2";
        this.autoRegesterDevices = true;
        this.orchestra = new BreakerFalconOrchestra();
        this.autoPaths = autoPaths;
        this.startConfig = startConfig;
        this.breakModeConfig = breakModeConfig;
        usesOrchestra = false;
        usesPaths = true;
    }

    public BreakerRobotConfig(BreakerRobotStartConfig startConfig, BreakerAutoPath... autoPaths) {
        this.secondsBetweenSelfChecks = 5;
        this.autologNetworkTables = false;
        this.selftestServerAddress = "172.22.11.2";
        this.autoRegesterDevices = true;
        this.orchestra = new BreakerFalconOrchestra();
        this.autoPaths = autoPaths;
        this.startConfig = startConfig;
        breakModeConfig = new BreakerAutomaticBreakModeConfig();
        usesOrchestra = false;
        usesPaths = true;
    }

    public BreakerRobotConfig(BreakerRobotStartConfig startConfig, BreakerAutomaticBreakModeConfig breakModeConfig) {
        this.secondsBetweenSelfChecks = 5;
        this.autologNetworkTables = false;
        this.selftestServerAddress = "172.22.11.2";
        this.autoRegesterDevices = true;
        this.orchestra = new BreakerFalconOrchestra();
        this.autoPaths = new BreakerAutoPath[0];
        this.startConfig = startConfig;
        this.breakModeConfig = breakModeConfig;
        usesOrchestra = false;
        usesPaths = false;
    }

    public BreakerRobotConfig(BreakerAutomaticBreakModeConfig breakModeConfig) {
        this.secondsBetweenSelfChecks = 5;
        this.autologNetworkTables = false;
        this.selftestServerAddress = "172.22.11.2";
        this.autoRegesterDevices = true;
        this.orchestra = new BreakerFalconOrchestra();
        this.autoPaths = new BreakerAutoPath[0];
        this.startConfig = new BreakerRobotStartConfig();
        this.breakModeConfig = breakModeConfig;
        usesOrchestra = false;
        usesPaths = false;
    }

    public BreakerRobotConfig(BreakerRobotStartConfig startConfig) {
        this.secondsBetweenSelfChecks = 5;
        this.autologNetworkTables = false;
        this.selftestServerAddress = "172.22.11.2";
        this.autoRegesterDevices = true;
        this.orchestra = new BreakerFalconOrchestra();
        this.autoPaths = new BreakerAutoPath[0];
        this.startConfig = startConfig;
        breakModeConfig = new BreakerAutomaticBreakModeConfig();
        usesOrchestra = false;
        usesPaths = false;
    }

    public BreakerRobotConfig() {
        this.secondsBetweenSelfChecks = 5;
        this.autologNetworkTables = false;
        this.selftestServerAddress = "172.22.11.2";
        this.autoRegesterDevices = true;
        this.orchestra = new BreakerFalconOrchestra();
        this.autoPaths = new BreakerAutoPath[0];
        this.startConfig = new BreakerRobotStartConfig();
        breakModeConfig = new BreakerAutomaticBreakModeConfig();
        usesOrchestra = false;
        usesPaths = false;
    }

    public boolean getAutoRegesterDevices() {
        return autoRegesterDevices;
    }

    public BreakerAutoPath[] getAutoPaths() {
        return autoPaths;
    }

    public BreakerFalconOrchestra getOrchestra() {
        return orchestra;
    }

    public double getSecondsBetweenSelfChecks() {
        return secondsBetweenSelfChecks;
    }

    public String getSelftestServerAddress() {
        return selftestServerAddress;
    }

    public boolean getAutologNetworkTables() {
        return autologNetworkTables;
    }

    public boolean UsesOrchestra() {
        return usesOrchestra;
    }

    public boolean UsesPaths() {
        return usesPaths;
    }

    public BreakerRobotStartConfig getStartConfig() {
        return startConfig;
    }

    public BreakerAutomaticBreakModeConfig getBreakModeConfig() {
        return breakModeConfig;
    }

}
