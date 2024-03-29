// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.TreeMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.BreakerLib.physics.BreakerVector2;
import frc.robot.BreakerLib.physics.projectilemotion.BreakerProjectile;
import frc.robot.BreakerLib.subsystem.cores.drivetrain.differential.BreakerDiffDrive;
import frc.robot.BreakerLib.subsystem.cores.shooter.BreakerFlywheel;
import frc.robot.BreakerLib.subsystem.cores.shooter.BreakerFlywheelConfig;
import frc.robot.BreakerLib.util.math.interpolation.interpolateingmaps.BreakerInterpolatingTreeMap;

public class Shooter extends SubsystemBase {
//   public enum ShooterAimMode {
//     AUTO_AIM,
//     FENDER
//   }
  /** Creates a new Shooter. */
  private BreakerFlywheelConfig config;
  private BreakerFlywheel flywheel;
  private WPI_TalonFX leftFlywheelMotor;
  private WPI_TalonFX rightFlywheelMotor;
  //private Vision vision;
  private BreakerInterpolatingTreeMap<Double, BreakerVector2> interpolateingShotPerameterMap;
  private BreakerDiffDrive drivetrain;
 // private ShooterAimMode aimMode = ShooterAimMode.AUTO_AIM;
  private BreakerProjectile ball;
  private Translation2d targetLocation = new Translation2d(0, 0);
  private boolean flywheelEnabled = false;
  public Shooter(Drive drive) {
    //this.vision = vision;
    drivetrain = drive.getBaseDrivetrain();
    leftFlywheelMotor = new WPI_TalonFX(Constants.LEFT_FLYWHEEL_MOTOR_ID);
    rightFlywheelMotor = new WPI_TalonFX(Constants.RIGHT_FLYWHEEL_MOTOR_ID);

    leftFlywheelMotor.setInverted(true);
    rightFlywheelMotor.setInverted(false);

    //flywheelKp, flywheelKi, flywheelKd, flywheelVelTol, flywheelAccelTol, 
    //flywheelMomentOfInertaJKgMetersSq, flywheelGearRatioToOne, modelKalmanDeviation, encoderKalmanDeveation, 
    //lqrVelocityErrorTolerance, lqrControlEffort, flywheelKv, flywheelKa, flywheelKs
    config = new BreakerFlywheelConfig(0.0, 0.0, 0.0, 10.0, 10.0, 
    1.0, 0.0006, 3.0, 0.01, 10.0, 3.0, 0.023, 0.001, 0.00000001); //0.003
    flywheel = new BreakerFlywheel(config, leftFlywheelMotor, rightFlywheelMotor);

    // ball = new BreakerProjectile(massKg, dragCoeffiecnt, crossSectionalAreaMetersSq)

    TreeMap<Double, BreakerVector2> shotPerameterMap = new TreeMap<Double, BreakerVector2>();
      shotPerameterMap.put(0.0, BreakerVector2.fromForceAndRotation(Rotation2d.fromDegrees(0), 700.0));
      shotPerameterMap.put(2.5, BreakerVector2.fromForceAndRotation(Rotation2d.fromDegrees(0), 700.0));
      shotPerameterMap.put(5.0, BreakerVector2.fromForceAndRotation(Rotation2d.fromDegrees(0), 700.0));
      shotPerameterMap.put(7.5, BreakerVector2.fromForceAndRotation(Rotation2d.fromDegrees(0), 700.0));
      shotPerameterMap.put(10.0, BreakerVector2.fromForceAndRotation(Rotation2d.fromDegrees(0), 700.0));
      shotPerameterMap.put(12.5, BreakerVector2.fromForceAndRotation(Rotation2d.fromDegrees(0), 700.0));

    interpolateingShotPerameterMap = new frc.robot.BreakerLib.util.math.interpolation.interpolateingmaps.BreakerInterpolatingTreeMap<>(shotPerameterMap);
  }

//   public void setShooterAimMode(ShooterAimMode newAimMode) {
//     aimMode = newAimMode;
//   }

  private BreakerVector2 getIntepolatedVector(double distanceMeters) {
    return interpolateingShotPerameterMap.getInterpolatedValue(distanceMeters);
  }

  private double getDistanceToTarget() {
    return targetLocation.getDistance(drivetrain.getOdometryPoseMeters().getTranslation());
  }

  private void flywheelLogic() {
    //if (aimMode == ShooterAimMode.AUTO_AIM) {
      double speed = 2000;//getIntepolatedVector(getDistanceToTarget()).getForce();
      flywheel.setFlywheelSpeed(speed);
      System.out.println("Commanded SPEED: " + speed );

    //}
  }

  public void toggleShooter() {
      flywheelEnabled = !flywheelEnabled;
  }

  @Override
  public void periodic() {
    if (flywheelEnabled) {
      flywheelLogic();
      System.out.println("RPM: " + flywheel.getFlywheelRPM());
    } else {
        flywheel.setFlywheelSpeed(0.0);
    }
    
  }
}
