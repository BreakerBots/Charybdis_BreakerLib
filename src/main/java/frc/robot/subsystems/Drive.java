// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.BreakerLib.devices.misic.BreakerRoboRio;
import frc.robot.BreakerLib.devices.sensors.BreakerPigeon2;
import frc.robot.BreakerLib.subsystemcores.drivetrain.BreakerGenericDrivetrain;
import frc.robot.BreakerLib.subsystemcores.drivetrain.differential.BreakerDiffDrive;
import frc.robot.BreakerLib.subsystemcores.drivetrain.differential.BreakerDiffDriveConfig;
import frc.robot.BreakerLib.util.BreakerMotorControl;

public class Drive extends SubsystemBase {
  /** Creates a new Drive. */
  private BreakerDiffDrive drivetrain;
  private BreakerDiffDriveConfig driveConfig;

  private WPI_TalonFX left1;
  private WPI_TalonFX left2;
  private WPI_TalonFX left3;
  private WPI_TalonFX right1;
  private WPI_TalonFX right2;
  private WPI_TalonFX right3;

  private WPI_TalonFX[] leftMotors;
  private WPI_TalonFX[] rightMotors;

  private PIDController leftSideRamsetePID;
  private PIDController rightSideRamsetePID;

  public Drive(BreakerPigeon2 pigeon2) {

    left1 = new WPI_TalonFX(Constants.L1_ID);
    left2 = new WPI_TalonFX(Constants.L2_ID);
    left3 = new WPI_TalonFX(Constants.L3_ID);
    right1 = new WPI_TalonFX(Constants.R1_ID);
    right2 = new WPI_TalonFX(Constants.R2_ID);
    right3 = new WPI_TalonFX(Constants.R3_ID);

    leftMotors = BreakerMotorControl.createMotorArray(left1, left2, left3);
    rightMotors = BreakerMotorControl.createMotorArray(right1, right2, right3);
    
    leftSideRamsetePID = new PIDController(Constants.DRIVE_RS_PID_KP, Constants.DRIVE_RS_PID_KI, Constants.DRIVE_RS_PID_KD);
    leftSideRamsetePID.setTolerance(0.02, 0.05);
    rightSideRamsetePID = new PIDController(Constants.DRIVE_RS_PID_KP, Constants.DRIVE_RS_PID_KI, Constants.DRIVE_RS_PID_KD);
    rightSideRamsetePID.setTolerance(0.02, 0.05);

    driveConfig = new BreakerDiffDriveConfig(Constants.TALON_FX_TICKS, Constants.DRIVE_GEAR_RATIO, Constants.DRIVE_COLSON_DIAMETER, 
      Constants.DRIVE_FF_KS, Constants.DRIVE_FF_KV, Constants.DRIVE_FF_KA, Constants.DRIVE_TRACK_WIDTH, leftSideRamsetePID, rightSideRamsetePID);
    driveConfig.setSlowModeMultipliers(Constants.DRIVE_SLOW_MODE_FWD_MULTIPLIER, Constants.DRIVE_SLOW_MODE_TURN_MULTIPLIER);

    drivetrain = new BreakerDiffDrive(leftMotors, rightMotors, false, true, pigeon2, driveConfig);

    drivetrain.setOdometry(new Pose2d(0, 0, Rotation2d.fromDegrees(0)), pigeon2.getRawAngles()[0]);
  }

  public BreakerDiffDrive getBaseDrivetrain() {
    return drivetrain;
  }

  @Override
  public void periodic() {
    drivetrain.updateOdometry();
    System.out.println(drivetrain.getOdometryPoseMeters().toString() + " Ticks R: " + drivetrain.getRightDriveTicks() + " Ticks L: " + drivetrain.getLeftDriveTicks());
  }
}
