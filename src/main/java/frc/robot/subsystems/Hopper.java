// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Hopper extends SubsystemBase {
  private WPI_TalonSRX hopperMotor;
  private DigitalInput topSlotSensor;
  private DigitalInput bottomSlotSensor;
  private boolean isFeedingShooter = false;
  private boolean ballInTrasit = false;
  private Intake intake;
  public Hopper(Intake intake) {
    hopperMotor = new WPI_TalonSRX(Constants.HOPPER_ID);
    hopperMotor.setInverted(true);
    topSlotSensor = new DigitalInput(Constants.HOPPER_TOP_SLOT_CHAN);
    bottomSlotSensor = new DigitalInput(Constants.HOPPER_BOTTOM_SLOT_CHAN);
    this.intake = intake;
  }

  public void startHopperMotor() {
    hopperMotor.set(Constants.HOPPER_SPEED);
    isFeedingShooter = false;

  }

  public void startHopperShooterFeed() {
    hopperMotor.set(Constants.HOPPER_SHOOTER_FEED_SPEED);
    isFeedingShooter = true;
    }

  public void stopHopper() {
    hopperMotor.set(0);
    isFeedingShooter = false;
  }

  public boolean hopperIsOn() {
    return (hopperMotor.getMotorOutputPercent() != 0);
  }

  public boolean hopperIsFeedingShooter() {
    return isFeedingShooter;
  }

  public boolean topSlotIsFull() {
    return topSlotSensor.get();
  }

  public boolean bottomSlotIsFull() {
    return !bottomSlotSensor.get();
  }

  public boolean bolthSlotsAreFull() {
    return (topSlotIsFull() && !bottomSlotIsFull());
  }

  public boolean bolthSlotsAreEmpty() {
    return (!topSlotIsFull() && !bottomSlotIsFull());
  }

  public boolean BallIsInTransit() {
    return ballInTrasit;
  }

  private void hoperLogicLoop() {
    if (intake.getIntakeIsRunning() && !isFeedingShooter) {
      if (!topSlotIsFull() && bottomSlotIsFull()) {
        startHopperMotor();
        ballInTrasit = true;
      } else if (topSlotIsFull() && !bottomSlotIsFull()) {
        stopHopper();
        ballInTrasit = false;
      } else if (bolthSlotsAreFull() || (bolthSlotsAreEmpty() && !ballInTrasit)) {
        stopHopper();
        intake.stopIntake();
      }
    }
  }

  @Override
  public void periodic() {
    hoperLogicLoop();
  }
}
