// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.subsystemcores.shooter;

import java.util.EnumMap;

import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BreakerLib.control.statespace.BreakerFlywheelStateSpace;
import frc.robot.BreakerLib.devices.BreakerGenericDevice;
import frc.robot.BreakerLib.util.BreakerCTREUtil;
import frc.robot.BreakerLib.util.BreakerLog;
import frc.robot.BreakerLib.util.BreakerTriplet;
import frc.robot.BreakerLib.util.math.BreakerUnits;
import frc.robot.BreakerLib.util.math.averages.BreakerGenericAverageingList;
import frc.robot.BreakerLib.util.powermanagement.BreakerPowerManagementConfig;
import frc.robot.BreakerLib.util.powermanagement.DevicePowerMode;
import frc.robot.BreakerLib.util.selftest.DeviceHealth;
import frc.robot.BreakerLib.util.testsuites.BreakerGenericTestSuiteImplamentation;
import frc.robot.BreakerLib.util.testsuites.flywheelSuite.BreakerFlywheelTestSuite;

/** A class representing a robot's shooter flywheel and its assocated controle loop */
public class BreakerFlywheel extends SubsystemBase implements BreakerGenericTestSuiteImplamentation<BreakerFlywheelTestSuite>, BreakerGenericDevice {
    private PIDController flyPID;
    private double flywheelTargetRSU = 0;
    private MotorControllerGroup flywheel;
    private WPI_TalonFX lFlyMotor;
    private WPI_TalonFX[] motors;
    private BreakerFlywheelStateSpace flySS;
    private BreakerFlywheelTestSuite testSuite;

    private String deviceName = " flywheel ", faults = null;
    private DeviceHealth health = DeviceHealth.NOMINAL;
    

    public BreakerFlywheel(BreakerFlywheelConfig config, WPI_TalonFX... flywheelMotors) {
        flyPID = new PIDController(config.getFlywheelKp(), config.getFlywheelKi(), config.getFlywheelKd());
        flyPID.setTolerance(config.getFlywheelVelTol(), config.getFlywheelAccelTol());
        flySS = new BreakerFlywheelStateSpace(config.getFlywheelKv(),
                config.getFlywheelKs(), config.getModelKalmanTrust(),
                config.getEncoderKalmanTrust(), config.getLqrVelocityErrorTolerance(), config.getLqrControlEffort(),
                flywheelMotors);
        flywheel = new MotorControllerGroup(flywheelMotors);
        lFlyMotor = flywheelMotors[0];
        motors = flywheelMotors;
        testSuite = new BreakerFlywheelTestSuite(this);
    }

    public void setFlywheelSpeed(double flywheelTargetSpeedRPM) {
        flywheelTargetRSU = BreakerUnits.RPMtoFalconRSU(flywheelTargetSpeedRPM);
    }

    public double getFlywheelVelRSU() {
        return lFlyMotor.getSelectedSensorVelocity();
    }

    public double getFlywheelRPM() {
        return BreakerUnits.falconRSUtoRPM(getFlywheelVelRSU());
    }

    public double getFlywheelTargetVelRSU() {
        return flywheelTargetRSU;
    }

    /** sets flywheel speed to 0 RPM, controll loops remain enabled */
    public void stopFlywheel() {
        setFlywheelSpeed(0);
    }

    /** Stops flywheel and kills all assocated controll loops */
    public void killFlywheel() {
        flySS.killLoop();
        flywheel.set(0);
        BreakerLog.logSuperstructureEvent("flywheel controll loops disabled");
    }

    public void startFlywheel() {
        flySS.restartLoop();
        BreakerLog.logSuperstructureEvent("flywheel controll loops enabled");
    }

    private void runFlywheel() {
        flySS.setSpeedRPM(BreakerUnits.falconRSUtoRPM(flywheelTargetRSU));
        double flySetSpd = flyPID.calculate(getFlywheelVelRSU(), flywheelTargetRSU) + flySS.getNextPrecentSpeed();
        System.out.println("Fly Set Spd: " + flySetSpd + "| target spd: " + flywheelTargetRSU + " | Cur spd RPM: " + getFlywheelRPM() + " | Kalman: " + flySS.getKalmanFilter().getK(0, 0));
        flywheel.set(flySetSpd);
    }

    public boolean flywheelIsAtTargetVel() {
        return flyPID.atSetpoint();
    }

    @Override
    public void periodic() {
        runFlywheel();
    }

    @Override
    public BreakerFlywheelTestSuite getTestSuite() {
        return testSuite;
    }

    @Override
    public void runSelfTest() {
       faults = null;
       health = DeviceHealth.NOMINAL;
       for (WPI_TalonFX mot: motors) {
           Faults motFaults = new Faults();
           mot.getFaults(motFaults);
           BreakerTriplet<DeviceHealth, String, Boolean> trip = BreakerCTREUtil.getMotorHealthFaultsAndConnectionStatus(motFaults, mot.getDeviceID());
           if (trip.getLeft() != DeviceHealth.NOMINAL) {
               faults += trip.getMiddle();
               health = health != DeviceHealth.NOMINAL ? trip.getLeft() : health;
           }
       }
    }

    @Override
    public DeviceHealth getHealth() {
        return health;
    }

    @Override
    public String getFaults() {
        // TODO Auto-generated method stub
        return faults;
    }

    @Override
    public String getDeviceName() {
        return deviceName;
    }

    @Override
    public boolean hasFault() {
        return health != DeviceHealth.NOMINAL;
    }

    @Override
    public void setDeviceName(String newName) {
        deviceName = newName;
        
    }

    @Override
    public DevicePowerMode managePower(BreakerPowerManagementConfig managementConfig) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void overrideAutomaticPowerManagement(DevicePowerMode manualPowerMode) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void returnToAutomaticPowerManagement() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean isUnderAutomaticControl() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public DevicePowerMode getPowerMode() {
        // TODO Auto-generated method stub
        return null;
    }
}
