// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kForward;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kOff;
import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.kReverse;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.BreakerLib.devices.pneumatics.BreakerDoubleSolenoid;
import frc.robot.BreakerLib.util.BreakerLog;
import frc.robot.BreakerLib.util.selftest.SystemDiagnostics;

/** Intake arm and internal indexer wheels. */
public class Intake extends SubsystemBase {

  private BreakerDoubleSolenoid intakeSol;
  private WPI_TalonSRX leftIndexerMotor;
  private WPI_TalonSRX rightIndexerMotor;
  private WPI_TalonSRX intakeMotor;
  private Boolean hopperFeedEnabled = false;
  private SystemDiagnostics diagnostics;

  /** Creates a new Intake. */
  public Intake() {
    // Indexer motors
    leftIndexerMotor = new WPI_TalonSRX(Constants.LEFT_INDEXER_ID);
    rightIndexerMotor = new WPI_TalonSRX(Constants.RIGHT_INDEXER_ID);
    leftIndexerMotor.setInverted(true);

    // Intake motor and solenoid piston.
    intakeMotor = new WPI_TalonSRX(Constants.PRIM_INTAKE_ID);
    intakeSol = new BreakerDoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.INTAKESOL_FWD,
        Constants.INTAKESOL_REV, Constants.PCM_ID, kReverse);

    // Diagnostics info
    diagnostics = new SystemDiagnostics(" Intake ");
    diagnostics.addMotorControllers(leftIndexerMotor, rightIndexerMotor, intakeMotor);
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

  // Arm motor

  /** Starts arm motor. */
  public void startIntakeMotor() {
    intakeMotor.set(Constants.PRIM_INTAKE_SPEED);
  }

  /** Stops arm motor. */
  public void stopIntakeMotor() {
    intakeMotor.set(0);
  }

  /** Checks if arm motor is active. */
  public boolean intakeMotorIsRunning() {
    return (intakeMotor.getMotorOutputPercent() != 0);
  }

  // Left indexer motor, similar methods to intake motor.

  public void startLeftIndexer() {
    leftIndexerMotor.set(Constants.LEFT_INDEXER_SPEED);
  }

  public void stopLeftIndexer() {
    leftIndexerMotor.set(0);
  }

  public boolean getLeftIndexerIsRunning() {
    return (leftIndexerMotor.getMotorOutputPercent() != 0);
  }

  // Right indexer motor, similar to intake motor.

  public void startRightIndexer() {
    rightIndexerMotor.set(Constants.RIGHT_INDEXER_SPEED);
  }

  public void stopRightIndexer() {
    rightIndexerMotor.set(0);
  }

  public boolean getRightIndexerIsRunning() {
    return (rightIndexerMotor.getMotorOutputPercent() != 0);
  }

  /** All motors are running. */
  public boolean allMotorsActive() {
    return (intakeMotorIsRunning() && getLeftIndexerIsRunning() && getRightIndexerIsRunning());
  }

  /** Starts all motors. */
  public void startIntakeMotors() {
    if (!allMotorsActive()) {
      startLeftIndexer();
      startRightIndexer();
      startIntakeMotor();
    }
  }

  /** Stops all motors. */
  public void stopIntakeMotors() {
    stopIntakeMotor();
    stopRightIndexer();
    stopLeftIndexer();
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
      stopIntakeMotor();
      stopRightIndexer();
      BreakerLog.logSuperstructureEvent(" Intake arm motor stopped | Right Indexer stopped ");
    }
  }

  /** Starts left indexer to feed into hopper. Hopper feed is enabled. */
  public void startHopperFeed() {
    if (!allMotorsActive()) {
      startLeftIndexer();
    }
    hopperFeedEnabled = true;
  }

  /** Stops left indexer. Hopper feed is disabled. */
  public void stopHopperFeed() {
    if (!allMotorsActive()) {
      stopLeftIndexer();
    }
    hopperFeedEnabled = false;
  }

  /** Returns if intake is feeding into hopper. */
  public boolean hopperFeedEnabled() {
    return hopperFeedEnabled;
  }
}
