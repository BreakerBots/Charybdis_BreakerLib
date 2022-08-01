// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.DRIVE_COLSON_DIAMETER;
import static frc.robot.Constants.DRIVE_FF_KA;
import static frc.robot.Constants.DRIVE_FF_KS;
import static frc.robot.Constants.DRIVE_FF_KV;
import static frc.robot.Constants.DRIVE_GEAR_RATIO;
import static frc.robot.Constants.DRIVE_RS_PID_KD;
import static frc.robot.Constants.DRIVE_RS_PID_KI;
import static frc.robot.Constants.DRIVE_RS_PID_KP;
import static frc.robot.Constants.DRIVE_SLOW_MODE_FWD_MULTIPLIER;
import static frc.robot.Constants.DRIVE_SLOW_MODE_TURN_MULTIPLIER;
import static frc.robot.Constants.DRIVE_TRACK_WIDTH;
import static frc.robot.Constants.L1_ID;
import static frc.robot.Constants.L2_ID;
import static frc.robot.Constants.L3_ID;
import static frc.robot.Constants.R1_ID;
import static frc.robot.Constants.R2_ID;
import static frc.robot.Constants.R3_ID;
import static frc.robot.Constants.TALON_FX_TICKS;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BreakerLib.devices.sensors.BreakerPigeon2;
import frc.robot.BreakerLib.subsystemcores.drivetrain.differential.BreakerDiffDrive;
import frc.robot.BreakerLib.subsystemcores.drivetrain.differential.BreakerDiffDriveConfig;
import frc.robot.BreakerLib.util.BreakerCTREUtil;

public class Drive extends SubsystemBase {
  private BreakerDiffDrive drivetrain;
  private BreakerDiffDriveConfig driveConfig;

  private WPI_TalonFX left1, left2, left3;
  private WPI_TalonFX right1, right2, right3;

  private WPI_TalonFX[] leftMotors, rightMotors;

  private PIDController leftSideRamsetePID, rightSideRamsetePID;

  /** Creates a new Drive. */
  public Drive(BreakerPigeon2 pigeon2) {

    left1 = new WPI_TalonFX(L1_ID);
    left2 = new WPI_TalonFX(L2_ID);
    left3 = new WPI_TalonFX(L3_ID);
    right1 = new WPI_TalonFX(R1_ID);
    right2 = new WPI_TalonFX(R2_ID);
    right3 = new WPI_TalonFX(R3_ID);

    leftMotors = BreakerCTREUtil.createMotorArray(left1, left2, left3);
    rightMotors = BreakerCTREUtil.createMotorArray(right1, right2, right3);

    leftSideRamsetePID = new PIDController(DRIVE_RS_PID_KP, DRIVE_RS_PID_KI, DRIVE_RS_PID_KD);
    leftSideRamsetePID.setTolerance(0.02, 0.05);
    rightSideRamsetePID = new PIDController(DRIVE_RS_PID_KP, DRIVE_RS_PID_KI, DRIVE_RS_PID_KD);
    rightSideRamsetePID.setTolerance(0.02, 0.05);

    driveConfig = new BreakerDiffDriveConfig(TALON_FX_TICKS, DRIVE_GEAR_RATIO, DRIVE_COLSON_DIAMETER, DRIVE_FF_KS,
        DRIVE_FF_KV, DRIVE_FF_KA, DRIVE_TRACK_WIDTH, leftSideRamsetePID, rightSideRamsetePID);
    driveConfig.setSlowModeMultipliers(DRIVE_SLOW_MODE_FWD_MULTIPLIER, DRIVE_SLOW_MODE_TURN_MULTIPLIER);

    drivetrain = new BreakerDiffDrive(leftMotors, rightMotors, false, true, pigeon2, driveConfig);
    drivetrain.setOdometryPosition(new Pose2d(0, 0, Rotation2d.fromDegrees(0)));
  }

  public BreakerDiffDrive getBaseDrivetrain() {
    return drivetrain;
  }

  @Override
  public void periodic() {
    // System.out.println(drivetrain.getOdometryPoseMeters().toString() + " Ticks R:
    // " + drivetrain.getRightDriveTicks()
    // + " Ticks L: " + drivetrain.getLeftDriveTicks());
  }
}
