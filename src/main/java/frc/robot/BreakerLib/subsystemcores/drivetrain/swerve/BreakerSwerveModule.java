// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.subsystemcores.drivetrain.swerve;

import com.ctre.phoenix.motorcontrol.Faults;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.AbsoluteSensorRange;
import com.ctre.phoenix.sensors.CANCoderFaults;
import com.ctre.phoenix.sensors.WPI_CANCoder;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import frc.robot.BreakerLib.util.math.BreakerMath;
import frc.robot.BreakerLib.util.math.BreakerUnits;
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
    private DeviceHealth encoderHealth = DeviceHealth.NOMINAL;
    private String faults = null;
    private WPI_CANCoder turnEncoder;
    /** constructs a new swerve drive module, this class is meant to surve as an intermedairy between your swerve hardware and the BreakerSwerveDrive class
     * @param driveMotor - The TalonFX motor that moves the module's wheel linearly
     * @param turnMotor - The TalonFX motor that actuates module's wheel angle and changes the direction it is faceing
     * @param turnEncoder - The CTRE CANcoder magnetic encoder that the module uses to detirman wheel angle
     * @param config - The BreakerSwerveDriveConfig object that holds all constants for your drivetrain
     */
    public BreakerSwerveModule(WPI_TalonFX driveMotor, WPI_TalonFX turnMotor, WPI_CANCoder turnEncoder, BreakerSwerveDriveConfig config) {
        this.config = config;
        this.turnMotor = turnMotor;
        this.driveMotor = driveMotor;
        this.turnEncoder = turnEncoder;

        turnEncoder.configAbsoluteSensorRange(AbsoluteSensorRange.Unsigned_0_to_360);

        anglePID = new PIDController(config.getModuleAnglekP(), config.getModuleAnglekI(), config.getModuleAngleKd());
        drivePID = new PIDController(config.getModuleVelkP(), config.getModuleVelkI(), config.getModuleVelKd());
        driveFF = new SimpleMotorFeedforward(config.getVelFeedForwardKs(), config.getVelFeedForwardKv());

        if (config.getTolerencesHaveBeenSet()) {
            drivePID.setTolerance(config.getPidTolerences()[0], config.getPidTolerences()[1]);
            anglePID.setTolerance(config.getPidTolerences()[2], config.getPidTolerences()[3]);
        }
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
        return turnEncoder.getAbsolutePosition();
    }
    
    public double getModuleVelMetersPerSec() {
       return Units.inchesToMeters(BreakerMath.ticksToInches(driveMotor.getSelectedSensorVelocity() * 10,
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
        CANCoderFaults curEncoderFaults = new CANCoderFaults();
        turnMotor.getFaults(curTurnFaults);
        driveMotor.getFaults(curDriveFaults);
        turnEncoder.getFaults(curEncoderFaults);
        if (curDriveFaults.HardwareFailure) {
            driveMotorHealth = DeviceHealth.INOPERABLE;
            faults += " DRIVE_MOTOR_FAIL ";
        }
        if (curTurnFaults.HardwareFailure) {
            turnMotorHealth = DeviceHealth.INOPERABLE;
            faults += " TURN_MOTOR_FAIL ";
        }
        if (curTurnFaults.HardwareFailure ^ curDriveFaults.HardwareFailure) {
            overallHealth = (overallHealth != DeviceHealth.INOPERABLE) ? DeviceHealth.FAULT : overallHealth;
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
        if (curEncoderFaults.HardwareFault) {
            faults += " ABSOLUTE_ENCODER_FAIL ";
            overallHealth = (overallHealth != DeviceHealth.INOPERABLE) ? DeviceHealth.FAULT : overallHealth;
            encoderHealth = DeviceHealth.INOPERABLE;
        }
        if (curEncoderFaults.MagnetTooWeak) {
            faults += " ABSOLUET_ENCODER_WEAK_MAG ";
            encoderHealth = (encoderHealth != DeviceHealth.INOPERABLE) ? DeviceHealth.FAULT : encoderHealth;
            overallHealth = (overallHealth != DeviceHealth.INOPERABLE) ? DeviceHealth.FAULT : overallHealth;
        }
        if (!curDriveFaults.HardwareFailure && !curTurnFaults.HardwareFailure && !curTurnFaults.SupplyUnstable && !curDriveFaults.SupplyUnstable && !curEncoderFaults.MagnetTooWeak && !curEncoderFaults.HardwareFault) {
            faults = null;
            driveMotorHealth = DeviceHealth.NOMINAL;
            turnMotorHealth = DeviceHealth.NOMINAL;
            overallHealth = DeviceHealth.NOMINAL;
        }
    }

    /** returns the modules health as an array [0] = overall, [1] = drive motor, [2] = turn motor, [3] = CANcoder
     */
    public DeviceHealth[] getModuleHealth() {
        DeviceHealth[] healths = new DeviceHealth[4];
        healths[0] = overallHealth;
        healths[1] = driveMotorHealth;
        healths[2] = turnMotorHealth;
        healths[3] = encoderHealth;
        return healths;
    }

    public boolean moduleHasFault() {
        return overallHealth != DeviceHealth.NOMINAL;
    }

    public String getModuleFaults() {
        return faults;
    }


}
