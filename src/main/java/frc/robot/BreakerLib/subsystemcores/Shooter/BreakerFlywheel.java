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
import frc.robot.BreakerLib.control.BreakerPIDF;
import frc.robot.BreakerLib.control.statespace.BreakerFlywheelStateSpace;
import frc.robot.BreakerLib.devices.BreakerGenericDevice;
import frc.robot.BreakerLib.util.BreakerCTREUtil;
import frc.robot.BreakerLib.util.BreakerLog;
import frc.robot.BreakerLib.util.BreakerTriplet;
import frc.robot.BreakerLib.util.math.BreakerUnits;
import frc.robot.BreakerLib.util.math.averages.BreakerGenericAveragingList;
import frc.robot.BreakerLib.util.powermanagement.BreakerPowerManagementConfig;
import frc.robot.BreakerLib.util.powermanagement.DevicePowerMode;
import frc.robot.BreakerLib.util.selftest.DeviceHealth;
import frc.robot.BreakerLib.util.selftest.SelfTest;
import frc.robot.BreakerLib.util.testsuites.BreakerGenericTestSuiteImplementation;
import frc.robot.BreakerLib.util.testsuites.flywheelSuite.BreakerFlywheelTestSuite;

/** A class representing a robot's shooter flywheel and its assocated controle loop */
public class BreakerFlywheel extends SubsystemBase implements BreakerGenericTestSuiteImplementation<BreakerFlywheelTestSuite>, BreakerGenericDevice {
    private BreakerPIDF flyPIDF;
    private double flywheelTargetRPM = 0;
    private MotorControllerGroup flywheel;
    private WPI_TalonFX lFlyMotor;
    private WPI_TalonFX[] motors;
    //private BreakerFlywheelStateSpace flySS;
    private BreakerFlywheelTestSuite testSuite;

    private String deviceName = " flywheel ", faults = null;
    private DeviceHealth health = DeviceHealth.NOMINAL;
    

    public BreakerFlywheel(BreakerPIDF flyPIDF, WPI_TalonFX... flywheelMotors) {
        // flySS = new BreakerFlywheelStateSpace(config.getFlywheelMomentOfInertaJKgMetersSq(),
        //         config.getFlywheelGearRatioToOne(), config.getModelKalmanTrust(),
        //         config.getEncoderKalmanTrust(), config.getLqrVelocityErrorTolerance(), config.getLqrControlEffort(), flywheelMotors);
        this.flyPIDF = flyPIDF;

        flywheel = new MotorControllerGroup(flywheelMotors);
        lFlyMotor = flywheelMotors[0];
        motors = flywheelMotors;
        testSuite = new BreakerFlywheelTestSuite(this);
        SelfTest.autoRegisterDevice(this);
    }

    public void setFlywheelSpeed(double flywheelTargetSpeedRPM) {
        flywheelTargetRPM = flywheelTargetSpeedRPM;
    }

    public double getFlywheelVelRSU() {
        return lFlyMotor.getSelectedSensorVelocity();
    }

    public double getFlywheelRPM() {
        return BreakerUnits.falconRSUtoRPM(getFlywheelVelRSU());
    }

    public double getFlywheelTargetRPM() {
        return flywheelTargetRPM;
    }

    /** sets flywheel speed to 0 RPM, controll loops remain enabled */
    public void stopFlywheel() {
        setFlywheelSpeed(0);
    }

    /** Stops flywheel and kills all assocated controll loops */
    public void killFlywheel() {
        //flySS.killLoop();
        flywheel.set(0);
        BreakerLog.logSuperstructureEvent("flywheel controll loops disabled");
    }

    public void startFlywheel() {
        //flySS.restartLoop();
        BreakerLog.logSuperstructureEvent("flywheel controll loops enabled");
    }

    private void runFlywheel() {
        //flySS.setSpeedRPM(BreakerUnits.falconRSUtoRPM(flywheelTargetSpU));
        double flySetSpd = flyPIDF.calculate(getFlywheelRPM(), flywheelTargetRPM) /** + flySS.getNextPrecentSpeed() */ ;
        System.out.println("Fly Set Spd: " + flySetSpd + " | Cur spd RPM: " + getFlywheelRPM() + " | PID-C: " + flyPIDF.getBasePidController().calculate(getFlywheelRPM(), flywheelTargetRPM) + " | FF-C: " + flyPIDF.getBaseFeedforwardController().calculate(flywheelTargetRPM) );
        flywheel.set(flySetSpd);
    }

    public boolean flywheelIsAtTargetVel() {
        return flyPIDF.atSetpoint();
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
