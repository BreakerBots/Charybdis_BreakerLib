// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.BreakerLib.devices.sensors.BreakerPigeon2;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;


public class RobotContainer {
  private final BreakerPigeon2 imuSys = new BreakerPigeon2(Constants.IMU_ID, Constants.IMU_INVERT);
  private final Drive drivetrainSys = new Drive(imuSys);
  private final Intake intakeSys = new Intake();
  public RobotContainer() {
    
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
