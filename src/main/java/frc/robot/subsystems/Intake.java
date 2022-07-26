// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kForward;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kOff;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kReverse;
import static edu.wpi.first.wpilibj.PneumaticsModuleType.CTREPCM;
import static frc.robot.Constants.INTAKESOL_FWD;
import static frc.robot.Constants.INTAKESOL_REV;
import static frc.robot.Constants.LEFT_INDEXER_ID;
import static frc.robot.Constants.LEFT_INDEXER_SPEED;
import static frc.robot.Constants.PCM_ID;
import static frc.robot.Constants.PRIM_INTAKE_ID;
import static frc.robot.Constants.PRIM_INTAKE_SPEED;
import static frc.robot.Constants.RIGHT_INDEXER_ID;
import static frc.robot.Constants.RIGHT_INDEXER_SPEED;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BreakerLib.devices.motors.BreakerBinaryCTREMotor;
import frc.robot.BreakerLib.devices.pneumatics.BreakerDoubleSolenoid;
import frc.robot.BreakerLib.util.BreakerLog;
import frc.robot.BreakerLib.util.selftest.SystemDiagnostics;

/** Intake arm and internal indexer wheels. */
public class Intake extends SubsystemBase {

  private BreakerDoubleSolenoid intakeSol;
  private BreakerBinaryCTREMotor<WPI_TalonSRX> leftIndexerMotor;
  private BreakerBinaryCTREMotor<WPI_TalonSRX> rightIndexerMotor;
  private BreakerBinaryCTREMotor<WPI_TalonSRX> intakeMotor;
  private Boolean hopperFeedEnabled = false;
  private SystemDiagnostics diagnostics;

  /** Creates a new Intake. */
  public Intake() {
    // Indexer motors
    leftIndexerMotor = new BreakerBinaryCTREMotor<>(new WPI_TalonSRX(LEFT_INDEXER_ID), LEFT_INDEXER_SPEED);
    rightIndexerMotor = new BreakerBinaryCTREMotor<>(new WPI_TalonSRX(RIGHT_INDEXER_ID), RIGHT_INDEXER_SPEED);
    leftIndexerMotor.getMotor().setInverted(true);

    // Intake motor and solenoid piston.
    intakeMotor = new BreakerBinaryCTREMotor<>(new WPI_TalonSRX(PRIM_INTAKE_ID), PRIM_INTAKE_SPEED);
    intakeSol = new BreakerDoubleSolenoid(CTREPCM, INTAKESOL_FWD, INTAKESOL_REV, PCM_ID, kReverse);

    // Diagnostics info
    diagnostics = new SystemDiagnostics(" Intake ");
    diagnostics.addBreakerDevices(leftIndexerMotor, rightIndexerMotor, intakeMotor);
  }

  // Intake solenoid

  /** Arm solenoid is set to forward. */
  public void extendIntakeArm() {
    intakeSol.set(kForward);
    BreakerLog.logSuperstructureEvent("Intake arm extended");
  }

  /** Arm solenoid is set to reverse. */
  public void retractIntakeArm() {
    intakeSol.set(kReverse);
    BreakerLog.logSuperstructureEvent("Intake arm retracted");
    if (allMotorsActive()) {
      stopIntake();
    }
  }

  /** Arm solenoid is set to off. */
  public void unlockIntakeArm() {
    intakeSol.set(kOff);
    BreakerLog.logSuperstructureEvent("Intake arm unlocked");
    if (allMotorsActive()) {
      stopIntake();
    }
  }

  /** Check if arm solenoid is set to forward. */
  public boolean getIsExtended() {
    return (intakeSol.getState() == kForward);
  }

  /** All motors are running. */
  public boolean allMotorsActive() {
    return (intakeMotor.isActive() && leftIndexerMotor.isActive() && rightIndexerMotor.isActive());
  }

  /** Starts all motors. */
  public void startIntakeMotors() {
    if (!allMotorsActive()) {
      intakeMotor.start();
      leftIndexerMotor.start();
      rightIndexerMotor.start();
    }
  }

  /** Stops all motors. */
  public void stopIntakeMotors() {
    intakeMotor.stop();
    leftIndexerMotor.stop();
    rightIndexerMotor.stop();
    BreakerLog.logSuperstructureEvent(" Intake arm motor stopped | Left Indexer stopped | Right Indexer stopped ");
  }

  /** Starts all motors and extends arm. */
  public void startIntake() {
    BreakerLog.logEvent(" startIntake() called ");
    startIntakeMotors();
    if (!getIsExtended()) {
      extendIntakeArm();
    }
  }

  /**
   * Stops primary arm motor and right indexer. Left indexer is only shut off if
   * not feeding into hopper.
   */
  public void stopIntake() {
    BreakerLog.logEvent(" stopIntake() called ");
    if (!hopperFeedEnabled) {
      stopIntakeMotors();
    } else {
      intakeMotor.stop();
      rightIndexerMotor.stop();
      BreakerLog.logSuperstructureEvent(" Intake arm motor stopped | Right Indexer stopped ");
    }
  }

  /** Starts left indexer to feed into hopper. Hopper feed is enabled. */
  public void startHopperFeed() {
    if (!allMotorsActive()) {
      leftIndexerMotor.start();
    }
    hopperFeedEnabled = true;
  }

  /** Stops left indexer. Hopper feed is disabled. */
  public void stopHopperFeed() {
    if (!allMotorsActive()) {
      leftIndexerMotor.stop();
    }
    hopperFeedEnabled = false;
  }

  /** Returns if intake is feeding into hopper. */
  public boolean hopperFeedEnabled() {
    return hopperFeedEnabled;
  }
}
