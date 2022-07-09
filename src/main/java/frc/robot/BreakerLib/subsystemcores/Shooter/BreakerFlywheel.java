// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.subsystemcores.shooter;

import java.util.EnumMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BreakerLib.control.statespace.BreakerFlywheelStateSpace;
import frc.robot.BreakerLib.util.BreakerLog;
import frc.robot.BreakerLib.util.math.BreakerUnits;
import frc.robot.BreakerLib.util.testsuites.BreakerGenericTestSuiteImplamentation;
import frc.robot.BreakerLib.util.testsuites.flywheelSuite.BreakerFlywheelTestSuite;

/** A class representing a robot's shooter flywheel and its assocated controle loop */
public class BreakerFlywheel extends SubsystemBase implements BreakerGenericTestSuiteImplamentation<BreakerFlywheelTestSuite> {
    private PIDController flyPID;
    private double flywheelTargetRSU = 0;
    private MotorControllerGroup flywheel;
    private WPI_TalonFX lFlyMotor;
    private BreakerFlywheelStateSpace flySS;
    private BreakerFlywheelTestSuite testSuite;

    public BreakerFlywheel(BreakerFlywheelConfig config, WPI_TalonFX... flywheelMotors) {
        flyPID = new PIDController(config.getFlywheelKp(), config.getFlywheelKi(), config.getFlywheelKd());
        flyPID.setTolerance(config.getFlywheelVelTol(), config.getFlywheelAccelTol());
        flySS = new BreakerFlywheelStateSpace(config.getFlywheelKv(),
                config.getFlywheelKs(), config.getModelKalmanTrust(),
                config.getEncoderKalmanTrust(), config.getLqrVelocityErrorTolerance(), config.getLqrControlEffort(),
                flywheelMotors);
        flywheel = new MotorControllerGroup(flywheelMotors);
        lFlyMotor = flywheelMotors[0];

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
}
