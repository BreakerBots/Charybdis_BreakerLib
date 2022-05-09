// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.BreakerLib.devices.misic.BreakerRoboRio;
import frc.robot.BreakerLib.devices.sensors.BreakerPigeon2;
import frc.robot.BreakerLib.driverstation.BreakerXboxController;
import frc.robot.BreakerLib.util.BreakerLog;
import frc.robot.BreakerLib.util.selftest.SelfTest;
import frc.robot.commands.drive.DriveInTeleop;
import frc.robot.commands.drive.ToggleSlowMode;
import frc.robot.commands.intake.ToggleIntake;
import frc.robot.commands.trajectorypaths.DemoTrajectoryS;
import frc.robot.commands.trajectorypaths.circleDemoTrajectory;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;


public class RobotContainer {
  private final BreakerXboxController controllerSys = new BreakerXboxController(0);
  private final BreakerPigeon2 imuSys = new BreakerPigeon2(Constants.IMU_ID, Constants.IMU_INVERT);
  private final Drive drivetrainSys = new Drive(imuSys);
  private final Intake intakeSys = new Intake();
  private final Hopper hopperSys = new Hopper(intakeSys);
  private final SelfTest testSys = new SelfTest(5);
  private final BreakerRoboRio roboRioSys = new BreakerRoboRio();
  public RobotContainer() {
    SelfTest.addDevice(imuSys);
    SelfTest.addDevice(drivetrainSys.getBaseDrivetrain());
    BreakerLog.startLog(false);
    drivetrainSys.setDefaultCommand(new DriveInTeleop(controllerSys.getBaseController(), drivetrainSys));
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    controllerSys.getButtonA().whenPressed(new ToggleIntake(intakeSys));

    controllerSys.getdPadRight().whenPressed(new ToggleSlowMode(drivetrainSys.getBaseDrivetrain()));
  }

  public void setDriveBreakMode(Boolean isEnebled) {
    drivetrainSys.getBaseDrivetrain().setDrivetrainBrakeMode(isEnebled);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return new DemoTrajectoryS(drivetrainSys, imuSys);
  }
}
