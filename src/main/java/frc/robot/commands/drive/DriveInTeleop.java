// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.BreakerLib.subsystemcores.drivetrain.differential.DiffDrive;
import frc.robot.subsystems.Drive;

public class DriveInTeleop extends CommandBase {
  private XboxController controller;
  private DiffDrive baseDrivetrain;
  private Drive drivetrain;

  public DriveInTeleop(XboxController controller, Drive drivetrain) {
    addRequirements(drivetrain);
    this.controller = controller;
    this.drivetrain = drivetrain;
    baseDrivetrain = drivetrain.getBaseDrivetrain();
  }

  @Override
  public void execute() {
    double turn = controller.getLeftX();
    double net = controller.getRightTriggerAxis() - controller.getLeftTriggerAxis();
    baseDrivetrain.arcadeDrive(net, turn);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
