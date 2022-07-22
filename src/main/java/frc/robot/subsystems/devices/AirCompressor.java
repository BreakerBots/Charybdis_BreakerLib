// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.devices;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BreakerLib.devices.pneumatics.BreakerCompressor;
import frc.robot.BreakerLib.util.BreakerRoboRIO;
import frc.robot.BreakerLib.util.BreakerRoboRIO.RobotMode;

public class AirCompressor extends SubsystemBase {
  BreakerCompressor compressor;
  public AirCompressor() {
    compressor = new BreakerCompressor(5, PneumaticsModuleType.CTREPCM);
    compressor.disable();
  }

  public void enable() {
    compressor.enableDigital();
  }

  @Override
  public void periodic() {
  }
}
