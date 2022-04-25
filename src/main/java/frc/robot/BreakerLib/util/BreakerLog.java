// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util;

import java.text.DateFormat;
import java.util.Date;

import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.util.WPILibVersion;
import frc.robot.BreakerLib.devices.misic.BreakerRoboRio;

/** Add your docs here. */
public class BreakerLog {
    
    public static void startLog(boolean autologNetworkTables) {
        DataLogManager.logNetworkTables(autologNetworkTables);
        DataLogManager.start();
    }

    public static void logRobotStarted() {
      StringBuilder work = new StringBuilder(" | ---------------- ROBOT STARTED ---------------- |\n\n ");
      work.append("TEAM: " + BreakerConfigHandler.getRobotInfo().path("teamNumber").asText() + " - " + BreakerConfigHandler.getRobotInfo().path("teamName").asText() + "\n");
      work.append("ROBOT: " + BreakerConfigHandler.getRobotInfo().path("robotName").asText() + " - " + BreakerConfigHandler.getRobotInfo().path("year").asInt() + "\n");
      work.append("BREAKERLIB: " + "V" + BreakerConfigHandler.getRobotInfo().path("breakerLibVersion").asText() + " | " + "ROBOT SOFTWARE: " + "V" + BreakerConfigHandler.getRobotInfo().path("softwareVersion").asText() + "\n");
      work.append("AUTHORS: " + BreakerConfigHandler.getRobotInfo().path("authors").asText() + "\n");
      work.append(" | ---------------------------------------------- | \n\n\n");
      DataLogManager.log(work.toString());
      System.out.println(work.toString());
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
