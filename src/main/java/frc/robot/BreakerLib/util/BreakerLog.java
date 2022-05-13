// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util;

import edu.wpi.first.wpilibj.DataLogManager;
import frc.robot.BreakerLib.devices.misc.BreakerRoboRio;
import frc.robot.BreakerLib.devices.misc.BreakerRoboRio.RobotMode;

/** Add your docs here. */
public class BreakerLog {

  public static final String breakerLibVersion = "V1.2";

  public static void startLog(boolean autologNetworkTables) {
    DataLogManager.logNetworkTables(autologNetworkTables);
    DataLogManager.start();
  }

  /** Startup message for robot */
  public static void logRobotStarted(int teamNum, String teamName, String robotName, int year,
      String robotSoftwareVersion, String authorNames) {
    StringBuilder work = new StringBuilder(" | ---------------- ROBOT STARTED ---------------- |\n\n");
    work.append(" TEAM: " + teamNum + " - " + teamName + "\n");
    work.append(" ROBOT: " + robotName + " - " + year + "\n");
    work.append(" BREAKERLIB: " + breakerLibVersion + " | " + "ROBOT SOFTWARE: " + robotSoftwareVersion + "\n");
    work.append(" AUTHORS: " + authorNames + "\n\n");
    work.append(" | ---------------------------------------------- | \n\n\n");
    BreakerLog.log(work.toString());
    BreakerRoboRio.setCurrentRobotMode(RobotMode.DISABLED);
  }

  public static void logRobotChangedMode(RobotMode newMode) {
    DataLogManager.log("| ---- ROBOT MODE CHANGED TO: " + newMode + " ---- |");
    BreakerRoboRio.setCurrentRobotMode(newMode);
  }

  public static void logEvent(String event) {
    DataLogManager.log(" EVENT: " + event);
  }

  public static void logBreakerLibEvent(String event) {
    DataLogManager.log(" BREAKERLIB INTERNAL EVENT: " + event);
  }

  public static void logError(String error) {
    DataLogManager.log(" ERROR: " + error);
  }

  public static void logSuperstructureEvent(String event) {
    DataLogManager.log(" ROBOT SUPERSTRUCTURE EVENT: " + event);
  }

  public static void log(String message) {
    DataLogManager.log(message);
  }
}
