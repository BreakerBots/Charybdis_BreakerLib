// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.subsystemcores.drivetrain.swerve;

import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.robot.BreakerLib.util.BreakerMath;
import frc.robot.BreakerLib.util.BreakerUnits;
import frc.robot.BreakerLib.util.selftest.DeviceHealth;

/** Add your docs here. */
public class BreakerSwerveModule {
    private BreakerSwerveDriveConfig config;
    private PIDController drivePID;
    private PIDController anglePID;
    private SimpleMotorFeedforward driveFF;
    private WPI_TalonFX turnMotor;
    private WPI_TalonFX driveMotor;
    private DeviceHealth turnMotorHealth = DeviceHealth.NOMINAL;
    private DeviceHealth driveMotorHealth = DeviceHealth.NOMINAL;
    private DeviceHealth overallHealth = DeviceHealth.NOMINAL;
    private String faults = null;
    public BreakerSwerveModule(WPI_TalonFX driveMotor, WPI_TalonFX turnMotor, BreakerSwerveDriveConfig config) {
        this.config = config;
        this.turnMotor = turnMotor;
        this.driveMotor = driveMotor;
        anglePID = new PIDController(config.getModuleAnglekP(), config.getModuleAnglekI(), config.getModuleAngleKd());
        drivePID = new PIDController(config.getModuleVelkP(), config.getModuleVelkI(), config.getModuleVelKd());
        if (config.getTolerencesHaveBeenSet()) {
            drivePID.setTolerance(config.getPidTolerences()[0], config.getPidTolerences()[1]);
            anglePID.setTolerance(config.getPidTolerences()[2], config.getPidTolerences()[3]);
        }
        driveFF = new SimpleMotorFeedforward(config.getVelFeedForwardKs(), config.getVelFeedForwardKv());
    }
    
    public void setModuleTarget(Rotation2d tgtAngle, double speedMetersPreSec) {
        turnMotor.set(anglePID.calculate(getModuleAngle(), tgtAngle.getDegrees()));
        driveMotor.set(drivePID.calculate(getModuleVelMetersPerSec(), speedMetersPreSec) + (driveFF.calculate(speedMetersPreSec) / driveMotor.getBusVoltage()));
    }

    public void setModuleTarget(SwerveModuleState targetState) {
        turnMotor.set(anglePID.calculate(getModuleAngle(), targetState.angle.getDegrees()));
        driveMotor.set(drivePID.calculate(getModuleVelMetersPerSec(), targetState.speedMetersPerSecond) + (driveFF.calculate(targetState.speedMetersPerSecond) / driveMotor.getBusVoltage()));
    }

    public double getModuleAngle() {
        return (getTurnMotorTicks() / BreakerMath.getTicksPerRotation(2048, config.getTurnMotorGearRatioToOne()) * 360);
    }

    public double getTurnMotorTicks() {
        return turnMotor.getSelectedSensorPosition();
    }
    
    public double getModuleVelMetersPerSec() {
       return BreakerUnits.inchesToMeters(BreakerMath.ticksToInches(driveMotor.getSelectedSensorVelocity() * 10,
        BreakerMath.getTicksPerInch(2048, config.getDriveMotorGearRatioToOne(), config.getWheelDiameter())));
    }

    public SwerveModuleState getModuleState() {
        return new SwerveModuleState(getModuleVelMetersPerSec(), Rotation2d.fromDegrees(getModuleAngle()));
    }

    public boolean atAngleSetpoint() {
        return anglePID.atSetpoint();
    }

    public boolean atVelSetpoint() {
        return drivePID.atSetpoint();
    }

    public boolean atSetModuleState() {
        return (atAngleSetpoint() && atVelSetpoint());
    }

    public void runModuleSelfCheck() {
        faults = null;
        Faults curTurnFaults = new Faults();
        Faults curDriveFaults = new Faults();
        turnMotor.getFaults(curTurnFaults);
        driveMotor.getFaults(curDriveFaults);
        if (curDriveFaults.HardwareFailure) {
            driveMotorHealth = DeviceHealth.INOPERABLE;
            faults += " DRIVE_MOTOR_FAIL ";
        }
        if (curTurnFaults.HardwareFailure) {
            turnMotorHealth = DeviceHealth.INOPERABLE;
            faults += " TURN_MOTOR_FAIL ";
        }
        if (curTurnFaults.HardwareFailure ^ curDriveFaults.HardwareFailure) {
            overallHealth = DeviceHealth.FAULT;
        } else if (curTurnFaults.HardwareFailure && curDriveFaults.HardwareFailure) {
            overallHealth = DeviceHealth.INOPERABLE;
        }
        if (curTurnFaults.SupplyUnstable) {
            faults += " TURN_MOTOR_UNSTABLE_SUPPLY ";
            turnMotorHealth = (turnMotorHealth != DeviceHealth.INOPERABLE) ? DeviceHealth.FAULT : turnMotorHealth;
        }
        if (curDriveFaults.SupplyUnstable) {
            faults += " DRIVE_MOTOR_UNSTABLE_SUPPLY ";
            driveMotorHealth = (driveMotorHealth != DeviceHealth.INOPERABLE) ? DeviceHealth.FAULT : driveMotorHealth;
        }
        if (curTurnFaults.UnderVoltage) {
            faults += " TURN_MOTOR_UNDER_6.5V ";
            turnMotorHealth = (turnMotorHealth != DeviceHealth.INOPERABLE) ? DeviceHealth.FAULT : turnMotorHealth;
        }
        if (curDriveFaults.UnderVoltage) {
            faults += " DRIVE_MOTOR_UNDER_6.5V ";
            driveMotorHealth = (driveMotorHealth != DeviceHealth.INOPERABLE) ? DeviceHealth.FAULT : driveMotorHealth;
        }
        if (curTurnFaults.SensorOutOfPhase) {
            faults += " TURN_SENSOR_OUT_OF_PHASE ";
            turnMotorHealth = (turnMotorHealth != DeviceHealth.INOPERABLE) ? DeviceHealth.FAULT : turnMotorHealth;
        }
        if (curDriveFaults.SensorOutOfPhase) {
            faults += " DRIVE_SENSOR_OUT_OF_PHASE ";
            driveMotorHealth = (driveMotorHealth != DeviceHealth.INOPERABLE) ? DeviceHealth.FAULT : driveMotorHealth;
        }
        if (!curDriveFaults.HardwareFailure && !curTurnFaults.HardwareFailure && !curTurnFaults.SupplyUnstable && !curDriveFaults.SupplyUnstable) {
            faults = null;
            driveMotorHealth = DeviceHealth.NOMINAL;
            turnMotorHealth = DeviceHealth.NOMINAL;
            overallHealth = DeviceHealth.NOMINAL;
        }
    }

    /** returns the modules health as an array [0] = overall, [1] = drive motor, [2] = turn motor.
     */
    public DeviceHealth[] getModuleHealth() {
        DeviceHealth[] healths = new DeviceHealth[3];
        healths[0] = overallHealth;
        healths[1] = driveMotorHealth;
        healths[2] = turnMotorHealth;
        return healths;
    }

    public boolean moduleHasFault() {
        return overallHealth != DeviceHealth.NOMINAL;
    }

    public String getModuleFaults() {
        return faults;
    }


}
