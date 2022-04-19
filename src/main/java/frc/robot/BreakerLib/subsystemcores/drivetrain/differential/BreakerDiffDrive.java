// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.subsystemcores.drivetrain.differential;

import java.io.ObjectInputFilter.Config;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import frc.robot.BreakerLib.devices.BreakerGenaricDevice;
import frc.robot.BreakerLib.devices.sensors.BreakerPigeon2;
import frc.robot.BreakerLib.subsystemcores.drivetrain.BreakerGenericDrivetrain;
import frc.robot.BreakerLib.util.BreakerMotorControl;
import frc.robot.BreakerLib.util.BreakerUnits;
import frc.robot.BreakerLib.util.selftest.DeviceHealth;

public class BreakerDiffDrive implements BreakerGenericDrivetrain, BreakerGenaricDevice {
  private WPI_TalonFX leftLead;
  private WPI_TalonFX[] leftMotors;
  private MotorControllerGroup leftDrive;

  private WPI_TalonFX rightLead;
  private WPI_TalonFX[] rightMotors;
  private MotorControllerGroup rightDrive;

  private DifferentialDrive diffDrive;
  private BreakerDiffDriveConfig driveConfig;

  private BreakerPigeon2 pigeon2;
  private DifferentialDriveOdometry driveOdometer;

  private String deviceName = "Differential_Drivetrain";

  private boolean isInSlowMode;
  
  /** Creates a new West Coast Drive. */
  public BreakerDiffDrive(WPI_TalonFX[] leftMotors, WPI_TalonFX[] rightMotors, boolean invertL, boolean invertR, BreakerPigeon2 pigeon2, BreakerDiffDriveConfig driveConfig) {
    this.leftMotors = leftMotors;
    leftLead = leftMotors[0];
    leftDrive = new MotorControllerGroup(leftMotors);
    leftDrive.setInverted(invertL);

    this.rightMotors = rightMotors;
    rightLead = rightMotors[0];
    rightDrive = new MotorControllerGroup(rightMotors);
    rightDrive.setInverted(invertR);

    diffDrive = new DifferentialDrive(leftDrive, rightDrive);

    driveOdometer = new DifferentialDriveOdometry(Rotation2d.fromDegrees(pigeon2.getRawAngles()[0]));

    this.driveConfig = driveConfig;
    this.pigeon2 = pigeon2;
  }

  /** Standard drive command, is affected by slow mode */
  public void arcadeDrive(double netSpeed, double turnSpeed) {
    if (isInSlowMode) {
      netSpeed *= driveConfig.getSlowModeForwardMultiplier();
      turnSpeed *= driveConfig.getSlowModeTurnMultiplier();
    }

    diffDrive.arcadeDrive(netSpeed, turnSpeed);
  }

  public void tankDrive(double leftSpeed, double rightSpeed) {
    diffDrive.tankDrive(leftSpeed, rightSpeed);
  }

  public void tankMoveVoltage(double leftVoltage, double rightVoltage) {
    leftDrive.setVoltage(leftVoltage);
    rightDrive.setVoltage(rightVoltage);
    diffDrive.feed();
  }

  public void resetDriveEncoders() {
    leftLead.setSelectedSensorPosition(0);
    rightLead.setSelectedSensorPosition(0);
  }

  public double getLeftDriveTicks() {
    return leftLead.getSelectedSensorPosition();
  }

  public double getLeftDriveInches() {
    return (getLeftDriveTicks() / driveConfig.getTicksPerInch());
  }

  public double getLeftDriveMeters() {
    return Units.inchesToMeters(getLeftDriveInches());
  }

  public double getRightDriveTicks() {
    return rightLead.getSelectedSensorPosition();
  }

  public double getRightDriveInches() {
    return getRightDriveTicks() / driveConfig.getTicksPerInch();
  }

  public double getRightDriveMeters() {
    return Units.inchesToMeters(getRightDriveInches());
  }

  public void setDrivetrainBrakeMode(boolean isEnabled) {
    for (WPI_TalonFX motorL: leftMotors) {
      BreakerMotorControl.setTalonBrakeMode(motorL, isEnabled);
    }
    for (WPI_TalonFX motorR: rightMotors) {
      BreakerMotorControl.setTalonBrakeMode(motorR, isEnabled);
    }
  }
  
  /** Returns an instance of the drivetrain's left side lead motor */
  public WPI_TalonFX getLeftLeadMotor() {
    return leftLead;
  }

  /** Returns an instance of the drivetrain's right side lead motor */
  public WPI_TalonFX getRightLeadMotor() {
    return rightLead;
  }

  public SimpleMotorFeedforward getFeedforward() {
    return new SimpleMotorFeedforward(driveConfig.getFeedForwardKs(), driveConfig.getFeedForwardKv(), driveConfig.getFeedForwardKa());
  }

  public DifferentialDriveKinematics getKinematics() {
    return driveConfig.getKinematics();
  }

  public PIDController getLeftPIDController() {
    return driveConfig.getLeftPID();
  }

  public PIDController getRightPIDController() {
    return driveConfig.getRightPID();
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds( BreakerUnits.inchesToMeters((leftLead.getSelectedSensorVelocity() / driveConfig.getTicksPerInch()) * 10),
     BreakerUnits.inchesToMeters((rightLead.getSelectedSensorVelocity() / driveConfig.getTicksPerInch()) * 10));
  }

  public void setSlowMode(boolean isEnabled) {
    isInSlowMode = isEnabled;
  }

  public boolean isInSlowMode() {
    return isInSlowMode;
  }

  @Override
  public void setOdometry(Pose2d poseMeters, double gyroAngle) {
    driveOdometer.resetPosition(poseMeters, Rotation2d.fromDegrees(gyroAngle));
  }

  @Override
  public Object getOdometer() {
    return driveOdometer; 
  }

  @Override
  public void updateOdometry() {
    resetDriveEncoders();
    driveOdometer.update(Rotation2d.fromDegrees(pigeon2.getRawAngles()[0]), getLeftDriveMeters(), getRightDriveMeters());
  }

  @Override
  public double[] getOdometryPosition() {
    Pose2d basePose = driveOdometer.getPoseMeters();
    double xPos = Units.metersToInches(basePose.getX());
    double yPos = Units.metersToInches(basePose.getY());
    double degPos = basePose.getRotation().getDegrees();
    return new double[] { xPos, yPos, degPos };
  }

  @Override
  public Pose2d getOdometryPoseMeters() {
    return driveOdometer.getPoseMeters();
  }

  @Override
  public void runSelfTest() {
    
  }

  @Override
  public DeviceHealth getHealth() {
    // TODO Auto-generated method stub
    return DeviceHealth.NOMINAL;
  }

  @Override
  public String getFaults() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String getDeviceName() {
    // TODO Auto-generated method stub
    return deviceName;
  }

  @Override
  public boolean hasFault() {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void setDeviceName(String newName) {
    deviceName = newName;
    
  }
}
