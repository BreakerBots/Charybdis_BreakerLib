// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator.Validity;

import edu.wpi.first.math.Drake;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.BreakerLib.devices.misic.BreakerDoubleSolenoid;

public class Intake extends SubsystemBase {
  /** Creates a new Intake. */
  private BreakerDoubleSolenoid intakeSol;
  private WPI_TalonSRX leftIndexerMotor;
  private WPI_TalonSRX rightIndexerMotor;
  private WPI_TalonSRX primaryIntakeMotor;
  private Boolean hopperFeedEnabled = false;
  public Intake() {
    intakeSol = new BreakerDoubleSolenoid(PneumaticsModuleType.CTREPCM, Constants.INTAKESOL_FWD, Constants.INTAKESOL_REV, Constants.PCM_ID, Value.kReverse);
    leftIndexerMotor = new WPI_TalonSRX(Constants.LEFT_INDEXER_ID);
    rightIndexerMotor = new WPI_TalonSRX(Constants.RIGHT_INDEXER_ID);
    rightIndexerMotor.setInverted(true);
    primaryIntakeMotor = new WPI_TalonSRX(Constants.PRIM_INTAKE_ID);
  }

  public void extendIntakeArm() {
    intakeSol.set(Value.kForward);
  }

  public void retractIntakeArm() {
    intakeSol.set(Value.kReverse);
    if (getIntakeIsRunning()) {
      stopIntake();
    }
  }

  public boolean getIsExtended() {
    return (intakeSol.getState() == Value.kForward);
  }

  public void startPrimaryIntakeMotor() {
    primaryIntakeMotor.set(Constants.PRIM_INTAKE_SPEED);
  }

  public void stopPrimaryIntakeMotor() {
    primaryIntakeMotor.set(0);
  }

  public boolean getPrimaryIntakeMotorIsRunning() {
    return (primaryIntakeMotor.getMotorOutputPercent() != 0);
  }

  public void startLeftIndexer() {
    leftIndexerMotor.set(Constants.LEFT_INDEXER_SPEED);
  }

  public void stopLeftIndexer() {
    leftIndexerMotor.set(0);
  }

  public boolean getLeftIndexerIsRunning() {
    return (leftIndexerMotor.getMotorOutputPercent() != 0);
  }

  public void startRightIndexer() {
    rightIndexerMotor.set(Constants.RIGHT_INDEXER_SPEED);
  }

  public void stopRightIndexer() {
    rightIndexerMotor.set(0);
  }

  public boolean getRightIndexerIsRunning() {
    return (rightIndexerMotor.getMotorOutputPercent() != 0);
  }

  public boolean getIntakeIsRunning() {
    return (getPrimaryIntakeMotorIsRunning() && getLeftIndexerIsRunning() && getRightIndexerIsRunning());
  }

  public void startIntakeMotors() {
    if (!getIntakeIsRunning()) {
      startLeftIndexer();
      startRightIndexer();
      startPrimaryIntakeMotor();
    }
  }

  public void stopIntakeMotors() {
    stopPrimaryIntakeMotor();
    stopRightIndexer();
    stopLeftIndexer();
  }

  public void startIntake() {
    startIntakeMotors();
    if (!getIsExtended()) {
      extendIntakeArm();
    }
  }

  public void stopIntake() {
    if (!hopperFeedEnabled) {
      stopIntakeMotors();
    } else {
      stopPrimaryIntakeMotor();
      stopRightIndexer();
    }
  }

  public void startHopperFeed() {
    if (!getIntakeIsRunning()) {
      startLeftIndexer();
    }
    hopperFeedEnabled = true;
  }

  public void stopHopperFeed() {
    if (!getIntakeIsRunning()) {
      stopLeftIndexer();
    }
    hopperFeedEnabled = false;
  }

  public boolean getHopperFeedIsEnable() {
    return hopperFeedEnabled;
  }
}
