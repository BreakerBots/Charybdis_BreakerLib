// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator.Validity;

import edu.wpi.first.math.Drake;
import edu.wpi.first.math.spline.PoseWithCurvature;
import edu.wpi.first.wpilibj.PneumaticsControlModule;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.BreakerLib.devices.pneumatics.BreakerDoubleSolenoid;
import frc.robot.BreakerLib.util.BreakerLog;
import frc.robot.BreakerLib.util.selftest.SystemDiagnostics;

import static edu.wpi.first.wpilibj.DoubleSolenoid.Value.*;

public class Intake extends SubsystemBase {
  /** Creates a new Intake. */
  private BreakerDoubleSolenoid intakeSol;
  private WPI_TalonSRX leftIndexerMotor;
  private WPI_TalonSRX rightIndexerMotor;
  private WPI_TalonSRX primaryIntakeMotor;
  private Boolean hopperFeedEnabled = false;
  private SystemDiagnostics diagnostics;

  public Intake() {
    intakeSol = new BreakerDoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.INTAKESOL_FWD,
        Constants.INTAKESOL_REV, Constants.PCM_ID, kReverse);
    leftIndexerMotor = new WPI_TalonSRX(Constants.LEFT_INDEXER_ID);
    rightIndexerMotor = new WPI_TalonSRX(Constants.RIGHT_INDEXER_ID);
    leftIndexerMotor.setInverted(true);
    primaryIntakeMotor = new WPI_TalonSRX(Constants.PRIM_INTAKE_ID);
    diagnostics = new SystemDiagnostics(" Intake ");
    diagnostics.addMotorControllers(leftIndexerMotor, rightIndexerMotor, primaryIntakeMotor);
  }

  // Intake arm solenoid

  public void extendIntakeArm() {
    intakeSol.set(kForward);
    BreakerLog.logSuperstructureEvent("Intake arm extended");
  }

  public void retractIntakeArm() {
    intakeSol.set(kReverse);
    BreakerLog.logSuperstructureEvent("Intake arm retracted");
    if (getIntakeIsRunning()) {
      stopIntake();
    }
  }

  public void unlockIntakeArm() {
    intakeSol.set(kOff);
    BreakerLog.logSuperstructureEvent("Intake arm unlocked");
    if (getIntakeIsRunning()) {
      stopIntake();
    }
  }

  public boolean getIsExtended() {
    return (intakeSol.getState() == kForward);
  }

  // Primary intake motor (for arm)

  public void startPrimaryIntakeMotor() {
    primaryIntakeMotor.set(Constants.PRIM_INTAKE_SPEED);
  }

  public void stopPrimaryIntakeMotor() {
    primaryIntakeMotor.set(0);
  }

  public boolean getPrimaryIntakeMotorIsRunning() {
    return (primaryIntakeMotor.getMotorOutputPercent() != 0);
  }

  // Left indexer motor

  public void startLeftIndexer() {
    leftIndexerMotor.set(Constants.LEFT_INDEXER_SPEED);
  }

  public void stopLeftIndexer() {
    leftIndexerMotor.set(0);
  }

  public boolean getLeftIndexerIsRunning() {
    return (leftIndexerMotor.getMotorOutputPercent() != 0);
  }

  // Right indexer motor

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
  public boolean getIntakeIsRunning() {
    return (getPrimaryIntakeMotorIsRunning() && getLeftIndexerIsRunning() && getRightIndexerIsRunning());
  }

  /** Starts all motors. */
  public void startIntakeMotors() {
    if (!getIntakeIsRunning()) {
      startLeftIndexer();
      startRightIndexer();
      startPrimaryIntakeMotor();
    }
  }

  /** Stops all motors. */
  public void stopIntakeMotors() {
    stopPrimaryIntakeMotor();
    stopRightIndexer();
    stopLeftIndexer();
    BreakerLog.logSuperstructureEvent(" Intake arm motor stopped | Left Indexer stopped | Right Indexer stopped ");
  }

  /** Starts all motors and extends arm. */
  public void startIntake() {
    BreakerLog.logEvent(" StartIntake() called ");
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
    BreakerLog.logEvent(" StopIntake() called ");
    if (!hopperFeedEnabled) {
      stopIntakeMotors();
    } else {
      stopPrimaryIntakeMotor();
      stopRightIndexer();
      BreakerLog.logSuperstructureEvent(" Intake arm motor stopped | Right Indexer stopped ");
    }
  }

  /** Starts left indexer to feed into hopper. Hopper feed is enabled. */
  public void startHopperFeed() {
    if (!getIntakeIsRunning()) {
      startLeftIndexer();
    }
    hopperFeedEnabled = true;
  }

  /** Stops left indexer. Hopper feed is disabled. */
  public void stopHopperFeed() {
    if (!getIntakeIsRunning()) {
      stopLeftIndexer();
    }
    hopperFeedEnabled = false;
  }

  /** Returns if intake is feeding into hopper. */
  public boolean getHopperFeedIsEnabled() {
    return hopperFeedEnabled;
  }
}
