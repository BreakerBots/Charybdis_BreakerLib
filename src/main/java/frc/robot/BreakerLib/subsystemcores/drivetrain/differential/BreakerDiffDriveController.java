// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.subsystemcores.drivetrain.differential;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.BreakerLib.driverstation.BreakerXboxController;
import frc.robot.BreakerLib.util.math.polynomials.BreakerGenericPolynomial;

public class BreakerDiffDriveController extends CommandBase {
  
  BreakerXboxController controller;
  boolean usesSuppliers, usesCurves;
  BreakerGenericPolynomial netSpeedCurve, turnSpeedCurve;
  DoubleSupplier netSpeedPrecentSupplier, turnSpeedPrecentSupplier;
  public BreakerDiffDriveController(BreakerXboxController controller) {
    this.controller = controller;
    usesSuppliers = false;
    usesCurves = false;
  }

  public BreakerDiffDriveController(BreakerXboxController controller, BreakerGenericPolynomial netSpeedCurve, BreakerGenericPolynomial turnSpeedCurve) {
    this.controller = controller;
    this.netSpeedCurve = netSpeedCurve;
    this.turnSpeedCurve = turnSpeedCurve;
    usesSuppliers = false;
    usesCurves = true;
  }

  public BreakerDiffDriveController(DoubleSupplier netSpeedPrecentSupplier, DoubleSupplier turnSpeedPrecentSupplier) {
    this.netSpeedPrecentSupplier = netSpeedPrecentSupplier;
    this.turnSpeedPrecentSupplier = turnSpeedPrecentSupplier;
    usesSuppliers = true;
    usesCurves = false;
  }

  public BreakerDiffDriveController(DoubleSupplier netSpeedPrecentSupplier, DoubleSupplier turnSpeedPrecentSupplier, BreakerGenericPolynomial netSpeedCurve, BreakerGenericPolynomial turnSpeedCurve) {
    this.netSpeedPrecentSupplier = netSpeedPrecentSupplier;
    this.turnSpeedPrecentSupplier = turnSpeedPrecentSupplier;
    this.netSpeedCurve = netSpeedCurve;
    this.turnSpeedCurve = turnSpeedCurve;
    usesSuppliers = true;
    usesCurves = true;
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
