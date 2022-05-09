// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.subsystems;

// import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.Constants;
// import frc.robot.BreakerLib.subsystemcores.Shooter.BreakerFlywheel;
// import frc.robot.BreakerLib.subsystemcores.Shooter.BreakerFlywheelConfig;

// public class Shooter extends SubsystemBase {
//   /** Creates a new Shooter. */
//   private BreakerFlywheelConfig config;
//   private BreakerFlywheel flywheel;
//   private WPI_TalonFX leftFlywheelMotor;
//   private WPI_TalonFX rightFlywheelMotor;
//   public Shooter() {
//     leftFlywheelMotor = new WPI_TalonFX(Constants.LEFT_FLYWHEEL_MOTOR_ID);
//     rightFlywheelMotor = new WPI_TalonFX(Constants.RIGHT_FLYWHEEL_MOTOR_ID);

//     leftFlywheelMotor.setInverted(true);
//     rightFlywheelMotor.setInverted(false);

//     config = new BreakerFlywheelConfig(flywheelKp, flywheelKi, flywheelKd, flywheelPosTol, flywheelVelTol, flywheelGearing, flywheelMomentOfInertiaJulesKgMetersSquared, modelKalmanTrust, encoderKalmanTrust, lqrVelocityErrorTolerance, lqrControlEffort);
//     flywheel = new BreakerFlywheel(config, leftFlywheelMotor, rightFlywheelMotor);
//   }

//   @Override
//   public void periodic() {
//       flywheel.startFlywheel();
//   }
// }
