// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util.testsuites.flywheelSuite;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.BreakerLib.subsystemcores.shooter.BreakerFlywheel;

public class RepeatedFlywheelSpinUpTest extends CommandBase {
  /** Creates a new RepeatedFlywheelSpinUpTest. */
  private int numberOfSpinUps;
  private double targetRPM;
  private BreakerFlywheel baseFlywheel;
  public RepeatedFlywheelSpinUpTest(int numberOfSpinUps, double targetRPM, BreakerFlywheel baseFlywheel) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.numberOfSpinUps = numberOfSpinUps;
    this.targetRPM = targetRPM;
    this.baseFlywheel = baseFlywheel;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
