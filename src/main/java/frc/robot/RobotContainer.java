// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.BreakerLib.auto.trajectory.management.BreakerAutoManager;
import frc.robot.BreakerLib.auto.trajectory.management.BreakerAutoPath;
import frc.robot.BreakerLib.devices.cosmetic.music.BreakerFalconOrchestra;
import frc.robot.BreakerLib.devices.sensors.BreakerPigeon2;
import frc.robot.BreakerLib.driverstation.BreakerXboxController;
import frc.robot.BreakerLib.subsystemcores.drivetrain.differential.BreakerDiffDriveController;
import frc.robot.BreakerLib.util.BreakerLog;
import frc.robot.BreakerLib.util.robotmanager.BreakerRobotConfig;
import frc.robot.BreakerLib.util.robotmanager.BreakerRobotManager;
import frc.robot.BreakerLib.util.robotmanager.BreakerRobotStartConfig;
import frc.robot.BreakerLib.util.selftest.SelfTest;
import frc.robot.commands.drive.ToggleSlowMode;
import frc.robot.commands.intake.ToggleIntake;
import frc.robot.commands.shooter.ToggleShooter;
import frc.robot.commands.trajectorypaths.DemoTrajectoryS;
import frc.robot.commands.trajectorypaths.attachedCommandsDemoTrajectory;
import frc.robot.commands.trajectorypaths.circleDemoTrajectory;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Hopper;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

public class RobotContainer {
  private final BreakerXboxController controllerSys = new BreakerXboxController(0);
  private final BreakerPigeon2 imuSys = new BreakerPigeon2(Constants.IMU_ID);
  private final Drive drivetrainSys = new Drive(imuSys);
  private final Intake intakeSys = new Intake();
  private final BreakerFalconOrchestra orchestraSys = new BreakerFalconOrchestra();
  private final Hopper hopperSys = new Hopper(intakeSys);
  private final Shooter shooterSys = new Shooter(drivetrainSys);

  public RobotContainer() {
    orchestraSys.addOrchestraMotors(drivetrainSys.getBaseDrivetrain().getLeftMotors());
    orchestraSys.addOrchestraMotors(drivetrainSys.getBaseDrivetrain().getRightMotors());

    BreakerRobotManager.setup(
      drivetrainSys.getBaseDrivetrain(), 
      new BreakerRobotConfig(
        new BreakerRobotStartConfig(5104, "BreakerBots", "Charybdis", 2022, "V3.2", "Roman Abrahamson, and Yousif Alkhalaf"), 
        orchestraSys, 
        new BreakerAutoPath("Circle Demo", new circleDemoTrajectory(drivetrainSys)),
        new BreakerAutoPath("S-shape Demo", new DemoTrajectoryS(drivetrainSys)),
        new BreakerAutoPath("S-attaced com demo", new attachedCommandsDemoTrajectory(drivetrainSys, imuSys, intakeSys))));

    drivetrainSys.getBaseDrivetrain().setDefaultCommand(new BreakerDiffDriveController(drivetrainSys.getBaseDrivetrain(), controllerSys));
    configureButtonBindings();
  }

  private void configureButtonBindings() {
    controllerSys.getButtonA().whenPressed(new ToggleIntake(intakeSys));
  
    controllerSys.getdPadRight().whenPressed(new ToggleSlowMode(drivetrainSys.getBaseDrivetrain()));

    controllerSys.getButtonB().whenPressed(new ToggleShooter(shooterSys));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return BreakerRobotManager.getSelectedAutoPath(); //new attachedCommandsDemoTrajectory(drivetrainSys, imuSys ,intakeSys);
  }
}
