// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.BreakerLib.subsystemcores.drivetrain.differential.BreakerDiffDrive;

public class DriveInTeleop extends CommandBase {
  private XboxController controller;
  private BreakerDiffDrive baseDrivetrain;
  public DriveInTeleop(XboxController controller, BreakerDiffDrive baseDrivetrain) {
    this.controller = controller;
    this.baseDrivetrain = baseDrivetrain;
  }

  @Override
  public void execute() {
    double turn = controller.getLeftX();
    double net = controller.getRightTriggerAxis() - controller.getLeftTriggerAxis();
    baseDrivetrain.arcadeDrive(net, turn);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
