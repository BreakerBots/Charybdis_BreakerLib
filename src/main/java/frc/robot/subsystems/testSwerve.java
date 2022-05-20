// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.subsystems;

// import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
// import com.ctre.phoenix.sensors.WPI_CANCoder;

// import edu.wpi.first.math.geometry.Transform2d;
// import edu.wpi.first.math.geometry.Translation2d;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.BreakerLib.devices.sensors.BreakerPigeon2;
// import frc.robot.BreakerLib.factories.BreakerCANCoderFactory;
// import frc.robot.BreakerLib.subsystemcores.drivetrain.swerve.BreakerSwerveDrive;
// import frc.robot.BreakerLib.subsystemcores.drivetrain.swerve.BreakerSwerveDriveConfig;
// import frc.robot.BreakerLib.subsystemcores.drivetrain.swerve.swervemodules.BreakerMK4iSwerveModule;

// /** Add your docs here. */
// public class testSwerve extends SubsystemBase{

//     private BreakerSwerveDriveConfig config;
//     private BreakerSwerveDrive drivetrain;

//     private WPI_TalonFX driveFL;
//     private WPI_TalonFX turnFL;
//     private WPI_TalonFX driveFR;
//     private WPI_TalonFX turnFR;
//     private WPI_TalonFX driveBL;
//     private WPI_TalonFX turnBL;
//     private WPI_TalonFX driveBR;
//     private WPI_TalonFX turnBR;

//     private WPI_CANCoder encoderFL;
//     private WPI_CANCoder encoderFR;
//     private WPI_CANCoder encoderBL;
//     private WPI_CANCoder encoderBR;

//     private Translation2d transFL;
//     private Translation2d transFR;
//     private Translation2d transBL;
//     private Translation2d transBR;

//     private BreakerMK4iSwerveModule frontLeftModule;
//     private BreakerMK4iSwerveModule frontRightModule;
//     private BreakerMK4iSwerveModule backLeftModule;
//     private BreakerMK4iSwerveModule backRightModule;

//     private BreakerPigeon2 pigeon2;
    
//     public testSwerve(BreakerPigeon2 pigeon2) {
//         this.pigeon2 = pigeon2;

//         driveFL = new WPI_TalonFX(deviceNumber);
//         turnFL = new WPI_TalonFX(deviceNumber);
//         encoderFL = BreakerCANCoderFactory.createCANCoder(deviceID, absoluteSensorRange, absoluteOffsetDegress, invertEncoder);
//         transFL = new Translation2d(x, y);

//         driveFR = new WPI_TalonFX(deviceNumber);
//         turnFR = new WPI_TalonFX(deviceNumber);
//         encoderFR = BreakerCANCoderFactory.createCANCoder(deviceID, absoluteSensorRange, absoluteOffsetDegress, invertEncoder);
//         transFR = new Translation2d(x, y);
        
//         driveBL = new WPI_TalonFX(deviceNumber);
//         turnBL = new WPI_TalonFX(deviceNumber);
//         encoderBL = BreakerCANCoderFactory.createCANCoder(deviceID, absoluteSensorRange, absoluteOffsetDegress, invertEncoder);
//         transBL = new Translation2d(x, y);

//         driveBR = new WPI_TalonFX(deviceNumber);
//         turnBR = new WPI_TalonFX(deviceNumber);
//         encoderBR = BreakerCANCoderFactory.createCANCoder(deviceID, absoluteSensorRange, absoluteOffsetDegress, invertEncoder);
//         transBR = new Translation2d(x, y);

//         config = new BreakerSwerveDriveConfig(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, transFL, transFR, transBL, transBR);

//         frontLeftModule = new BreakerMK4iSwerveModule(driveFL, turnFL, encoderFL, config);
//         frontRightModule = new BreakerMK4iSwerveModule(driveFR, turnFR, encoderFR, config);
//         frontRightModule = new BreakerMK4iSwerveModule(driveFR, turnFR, encoderFR, config);
//         backRightModule = new BreakerMK4iSwerveModule(driveBR, turnBR, encoderBR, config);

//         drivetrain = new BreakerSwerveDrive(config, pigeon2, frontLeftModule, frontRightModule, backLeftModule, backRightModule);
//     }

//     public BreakerSwerveDrive getBaseDrivetrain() {
//         return drivetrain;
//     }

//     @Override
//     public void periodic() {
//         drivetrain.updateOdometry();
//     }
// }
