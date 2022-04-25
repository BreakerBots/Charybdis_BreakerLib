// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.text.html.parser.Parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


/** Add your docs here. */
public class BreakerConfigHandler {
    private static BufferedReader reader;
    private static ObjectMapper mapper;
    private static JsonNode parser;
    public static void startConfigHandler() {;
        try {
            reader = Files.newBufferedReader(Paths.get("robotConfig.json"));
            mapper = new ObjectMapper();
            parser = mapper.readTree(reader);
        } catch (Exception e) {
        }
    }

    public static JsonNode getRobotInfo() {
        return parser.path("robotInfo");
    }

    public static JsonNode getDrivetrain() {
        return parser.path("drivetrain");
    }

    public static String getDrivetrainType() {
        return getDrivetrain().path("drivetrainType").asText();
    }

    public static JsonNode getDiffdriveConfig() {
        return getDrivetrain().path("diffConstants");
    }

    public static JsonNode getSwerveConfig() {
        return getDrivetrain().path("swerveConstants");
    }

    public static JsonNode getNode(String fieldName) {
        return parser.path(fieldName);
    }
}
