// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.TreeMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.BreakerLib.physics.BreakerVector2;
import frc.robot.BreakerLib.subsystemcores.shooter.BreakerFlywheel;
import frc.robot.BreakerLib.subsystemcores.shooter.BreakerFlywheelConfig;
import frc.robot.BreakerLib.util.math.interpolation.BreakerInterpolateingVector2TreeMap;
import frc.robot.subsystems.devices.Vision;

public class Shooter extends SubsystemBase {
  /** Creates a new Shooter. */
  private BreakerFlywheelConfig config;
  private BreakerFlywheel flywheel;
  private WPI_TalonFX leftFlywheelMotor;
  private WPI_TalonFX rightFlywheelMotor;
  private Vision vision;
  private BreakerInterpolateingVector2TreeMap interpolateingShotPerameterMap;
  public Shooter(Vision vision) {
    this.vision = vision;
    leftFlywheelMotor = new WPI_TalonFX(Constants.LEFT_FLYWHEEL_MOTOR_ID);
    rightFlywheelMotor = new WPI_TalonFX(Constants.RIGHT_FLYWHEEL_MOTOR_ID);

    leftFlywheelMotor.setInverted(true);
    rightFlywheelMotor.setInverted(false);

    config = new BreakerFlywheelConfig(flywheelKp, flywheelKi, flywheelKd, flywheelPosTol, flywheelVelTol, flywheelGearing, flywheelMomentOfInertiaJulesKgMetersSquared, modelKalmanTrust, encoderKalmanTrust, lqrVelocityErrorTolerance, lqrControlEffort);
    flywheel = new BreakerFlywheel(config, leftFlywheelMotor, rightFlywheelMotor);

    TreeMap<Double, BreakerVector2> shotPerameterMap = new TreeMap<Double, BreakerVector2>();
      shotPerameterMap.put(arg0, BreakerVector2.fromForceAndRotation(forceRotation, force));
      shotPerameterMap.put(arg0, BreakerVector2.fromForceAndRotation(forceRotation, force));
      shotPerameterMap.put(arg0, BreakerVector2.fromForceAndRotation(forceRotation, force));
      shotPerameterMap.put(arg0, BreakerVector2.fromForceAndRotation(forceRotation, force));
      shotPerameterMap.put(arg0, BreakerVector2.fromForceAndRotation(forceRotation, force));
      shotPerameterMap.put(arg0, BreakerVector2.fromForceAndRotation(forceRotation, force));
      shotPerameterMap.put(arg0, BreakerVector2.fromForceAndRotation(forceRotation, force));

    interpolateingShotPerameterMap = new BreakerInterpolateingVector2TreeMap(shotPerameterMap);
  }

  private BreakerVector2 getIntepolatedVector() {
    return interpolateingShotPerameterMap.getInterpolatedVector2(vision.getHubTarget().getTargetDistanceMeters());
  }

  @Override
  public void periodic() {
      flywheel.setFlywheelSpeed(getIntepolatedVector().getForce());
  }
}
