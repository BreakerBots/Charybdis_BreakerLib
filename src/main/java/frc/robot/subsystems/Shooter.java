// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BreakerLib.subsystemcores.Shooter.BreakerFlywheel;
import frc.robot.BreakerLib.subsystemcores.Shooter.BreakerFlywheelConfig;

public class Shooter extends SubsystemBase {
  /** Creates a new Shooter. */
  private BreakerFlywheel flywheel;
  private BreakerFlywheelConfig config;

  private WPI_TalonFX leftFlyMotor;
  private WPI_TalonFX rightFlyMotor;

  public Shooter() {
    leftFlyMotor = new WPI_TalonFX(deviceNumber);
    rightFlyMotor = new WPI_TalonFX(deviceNumber);

    config = new BreakerFlywheelConfig(flywheelKp, flywheelKi, flywheelKd, flywheelPosTol, flywheelVelTol, flywheelGearing, 
      flywheelMomentOfInertiaJulesKgMetersSquared, modelKalmanTrust, encoderKalmanTrust, lqrVelocityErrorTolerance, lqrControlEffort)
    flywheel = new BreakerFlywheel(config, leftFlyMotor, rightFlyMotor);
  }

  private BreakerFlywheel getBaseFlywheel() {
    return flywheel;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
