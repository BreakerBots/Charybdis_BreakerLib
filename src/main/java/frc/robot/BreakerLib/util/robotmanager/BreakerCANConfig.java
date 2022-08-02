// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util.robotmanager;

/** Add your docs here. */
public class BreakerCANConfig {
    private String[] diagnosticServers;
    public BreakerCANConfig(String... diagnosticServers) {
        this.diagnosticServers = diagnosticServers;
    }

   public String[] getDiagnosticServers() {
       return diagnosticServers;
   }
}
