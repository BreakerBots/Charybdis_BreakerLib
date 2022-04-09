// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util;

import edu.wpi.first.wpilibj.DataLogManager;

/** Add your docs here. */
public class BreakerLog {
    
    public static void startLog(boolean autologNetworkTables) {
        DataLogManager.logNetworkTables(autologNetworkTables);
        DataLogManager.start();
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

    public static void log(String message) {
      DataLogManager.log(message);
    }
}
