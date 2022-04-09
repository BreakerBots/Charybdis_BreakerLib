// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.subsystemcores.drivetrain.swerve;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.BreakerLib.devices.BreakerGenaricDevice;
import frc.robot.BreakerLib.devices.sensors.BreakerPigeon2;
import frc.robot.BreakerLib.subsystemcores.drivetrain.BreakerGenericDrivetrain;
import frc.robot.BreakerLib.util.BreakerUnits;
import frc.robot.BreakerLib.util.selftest.DeviceHealth;

public class BreakerSwerveDrive implements BreakerGenericDrivetrain, BreakerGenaricDevice {
  private BreakerSwerveDriveConfig config;
  // [0] = frontLeft, [1] = frontRight, [2] = backLeft, [3] = backRight
  private SwerveModuleState[] targetModuleStates;
  SwerveModuleState[] currentModuleStates;

  private BreakerSwerveModule frontLeftModule;
  private BreakerSwerveModule frontRightModule;
  private BreakerSwerveModule backLeftModule;
  private BreakerSwerveModule backRightModule;

  private BreakerPigeon2 pigeon2;

  private SwerveDriveOdometry odometer;

  private String deviceName = "Swerve_Drivetrain";
  private String faults = null;
  /** Constructs a new swerve based drivetrain
   * @param config - the confiuration values for the drivetrain's charicotristics and behavor, passed in as a "BreakerSwerveDriveConfig" object
   * @param swerveModules - The four swerve drive modules that make up the drivetrain, must be passed in the same order shown below
   */
  public BreakerSwerveDrive(BreakerSwerveDriveConfig config, BreakerPigeon2 pigeon2, BreakerSwerveModule frontLeftModule, BreakerSwerveModule frontRightModule, BreakerSwerveModule backLeftModule, BreakerSwerveModule backRightModule) {
    odometer = new SwerveDriveOdometry(config.getKinematics(), Rotation2d.fromDegrees(pigeon2.getRawAngles()[0]));
    this.config = config;
    this.frontLeftModule = frontRightModule;
    this.frontRightModule = frontRightModule;
    this.backLeftModule = backLeftModule;
    this.backRightModule = backRightModule;
    this.pigeon2 = pigeon2;
  }

  public void setRawModuleStates(SwerveModuleState[] targetModuleStates) {
    frontLeftModule.setModuleTarget(targetModuleStates[0]);
    frontRightModule.setModuleTarget(targetModuleStates[1]);
    backLeftModule.setModuleTarget(targetModuleStates[2]);
    backRightModule.setModuleTarget(targetModuleStates[3]);
  }

  /** Standard drivetrain movement command, specifyes robot speed in each axis including robot rotation (radian per second). 
   * All values are relative to the robot's orientation. */
  public void move(double forwardVelMetersPerSec, double horizontalVelMetersPerSec, double radPerSec) {
    ChassisSpeeds speeds = new ChassisSpeeds(forwardVelMetersPerSec, horizontalVelMetersPerSec, radPerSec);
    targetModuleStates = config.getKinematics().toSwerveModuleStates(speeds);
    setRawModuleStates(targetModuleStates);
  }

  public void stop() {
    move(0, 0, 0);
  }

  public void moveWithPrecentImput(double forwardPercent, double horizontalPercent, double turnPercent) {
    move((forwardPercent * config.getMaxForwardVel()), (horizontalPercent * config.getMaxSidewaysVel()), (turnPercent * config.getMaxAngleVel()));
  }

  public void moveRelativeToField(double forwardVelMetersPerSec, double horizontalVelMetersPerSec, double radPerSec) {
    double gyroRad = Math.toRadians(pigeon2.getYaw());
    double newFwd = forwardVelMetersPerSec * Math.cos(gyroRad) + horizontalVelMetersPerSec * Math.sin(gyroRad);
    double newHorz = -forwardVelMetersPerSec * Math.sin(gyroRad) + horizontalVelMetersPerSec * Math.cos(gyroRad);
    move(newFwd, newHorz, radPerSec);
  }

  public void moveWithPrecentImputRelativeToField(double forwardPercent, double horizontalPercent, double turnPercent) {
    double fwdV = forwardPercent * config.getMaxForwardVel();
    double horzV = horizontalPercent * config.getMaxSidewaysVel();
    double thetaV = turnPercent * config.getMaxAngleVel();
    moveRelativeToField(fwdV, horzV, thetaV);
  }

  public SwerveModuleState[] getSwerveModuleStates() {
    currentModuleStates[0] = frontLeftModule.getModuleState();
    currentModuleStates[1] = frontRightModule.getModuleState();
    currentModuleStates[2] = backLeftModule.getModuleState();
    currentModuleStates[3] = backRightModule.getModuleState();
    return currentModuleStates;
  }

  @Override
  public void setOdometry(Pose2d poseMeters, double gyroAngle) {
    odometer.resetPosition(poseMeters, Rotation2d.fromDegrees(gyroAngle));
    
  }

  @Override
  public Object getOdometer() {
    return odometer;
  }

  @Override
  public void updateOdometry() {
    odometer.update(Rotation2d.fromDegrees(pigeon2.getRawAngles()[0]), getSwerveModuleStates());
  }

  @Override
  public double[] getOdometryPosition() {
    double [] pos = new double [3];
    pos[0] = pigeon2.getRawAngles()[0];
    pos[1] = BreakerUnits.metersToInches(odometer.getPoseMeters().getX());
    pos[2] = BreakerUnits.metersToInches(odometer.getPoseMeters().getY());
    return pos;
  }

  @Override
  public Pose2d getOdometryPoseMeters() {
    return odometer.getPoseMeters();
  }

  public BreakerSwerveDriveConfig getConfig() {
      return config;
  }

  @Override
  public void runSelfTest() {
    // TODO Auto-generated method stub
    faults = null;
    frontLeftModule.runModuleSelfCheck();
    frontRightModule.runModuleSelfCheck();
    backLeftModule.runModuleSelfCheck();
    backRightModule.runModuleSelfCheck();
    if (frontLeftModule.moduleHasFault() || frontRightModule.moduleHasFault() || backLeftModule.moduleHasFault() || backRightModule.moduleHasFault()) {
    faults = (" Front_Left: " + frontLeftModule.getModuleFaults() + " Front_Right: " + frontRightModule.getModuleFaults() + 
    " Back_Left: " + backLeftModule.getModuleFaults() + " Back_Right: " + backRightModule.getModuleFaults() + " ");
    }
  }

  @Override
  public DeviceHealth getHealth() {
    return hasFault() ? DeviceHealth.FAULT : DeviceHealth.NOMINAL;
  }

  @Override
  public String getFaults() {
    return faults;
  }

  @Override
  public String getDeviceName() {
    return deviceName;
  }

  @Override
  public boolean hasFault() {
    return (faults != null);
  }

  @Override
  public void setDeviceName(String newName) {
    deviceName = newName;
    
  }


}
