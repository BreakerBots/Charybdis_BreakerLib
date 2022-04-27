// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util;

import java.io.BufferedReader;
import java.math.BigInteger;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.Date;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.util.WPILibVersion;
import frc.robot.BreakerLib.devices.misic.BreakerRoboRio;

/** Add your docs here. */
public class BreakerLog {

      private static BufferedReader reader;
      private static ObjectMapper mapper;
      private static JsonNode parser;
    
    public static void startLog(boolean autologNetworkTables) {
        DataLogManager.logNetworkTables(autologNetworkTables);
        DataLogManager.start();
    }

    public static void logRobotStarted() {
      try {
        reader = Files.newBufferedReader(Paths.get("src/main/java/frc/robot/config/robotConfig.json"));
        mapper = new ObjectMapper();
        parser = mapper.readTree(reader);
        StringBuilder work = new StringBuilder(" | ---------------- ROBOT STARTED ---------------- |\n\n");
        work.append(" TEAM: " + parser.path("robotInfo").path("teamNumber").textValue() + " - " + parser.path("robotInfo").path("teamName").asText() + "\n");
        work.append(" ROBOT: " + parser.path("robotInfo").path("robotName").asText() + " - " + parser.path("robotInfo").path("year").asInt() + "\n");
        work.append(" BREAKERLIB: " + "V" + parser.path("robotInfo").path("breakerLibVersion").asText() + " | " + "ROBOT SOFTWARE: " + "V" + parser.path("robotInfo").path("softwareVersion").asText() + "\n");
        work.append(" AUTHORS: " + parser.path("robotInfo").path("authors").asText() + "\n\n");
        work.append(" | ---------------------------------------------- | \n\n\n");
        BreakerLog.log(work.toString());
        BreakerRoboRio.setCurrentRobotMode(RobotMode.DISABLED);
      } catch (Exception e) {
        BreakerLog.logError("FAILED_TO_PARSE_CONFIG " + e);
      }
      
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
