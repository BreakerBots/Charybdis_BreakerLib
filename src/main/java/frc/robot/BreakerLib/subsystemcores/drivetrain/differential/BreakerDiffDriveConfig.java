// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.subsystemcores.drivetrain.differential;

import com.fasterxml.jackson.databind.JsonNode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import frc.robot.BreakerLib.util.BreakerLog;
import frc.robot.BreakerLib.util.BreakerMath;
import frc.robot.BreakerLib.util.BreakerUnits;
import frc.robot.BreakerLib.util.robotconfiguration.BreakerConfigManager;

/** Add your docs here. */
public class BreakerDiffDriveConfig {
    private DifferentialDriveKinematics kinematics;
    private double robotTrackWidthInches;
    private double ticksPerEncoderRotation;
    private double ticksPerInch;
    private double gearRatioTo1;
    private double wheelDiameter;
    private double wheelCircumference;
    private double getTicksPerWheelRotation;
    private double feedForwardKs;
    private double feedForwardKv;
    private double feedForwardKa;
    private double slowModeForwardMultiplier = 1;
    private double slowModeTurnMultiplier = 1;
    private PIDController leftPID; 
    private PIDController rightPID;
    /** Maunaly creates a new config and bypasses the automatic json config assigner */
    public BreakerDiffDriveConfig(double ticksPerEncoderRotation, double gearRatioTo1, double wheelDiameter, 
        double feedForwardKs, double feedForwardKv, double feedForwardKa, double robotTrackWidthInches, PIDController leftPID, PIDController rightPID) {
       
        this.wheelDiameter = wheelDiameter;
        this.ticksPerEncoderRotation = ticksPerEncoderRotation;
        this.gearRatioTo1 = gearRatioTo1;
        this.feedForwardKs = feedForwardKs;
        this.feedForwardKv = feedForwardKv;
        this.feedForwardKa = feedForwardKa;
        this.robotTrackWidthInches = robotTrackWidthInches;
        this.leftPID = leftPID;
        this.rightPID = rightPID;
            
        kinematics = new DifferentialDriveKinematics(BreakerUnits.inchesToMeters(robotTrackWidthInches));
     
        wheelCircumference = BreakerMath.getCircumferenceFromDiameter(wheelDiameter);
        ticksPerInch = BreakerMath.getTicksPerInch(ticksPerEncoderRotation, gearRatioTo1, wheelDiameter);
        getTicksPerWheelRotation = BreakerMath.getTicksPerRotation(ticksPerEncoderRotation, gearRatioTo1);
    }

    // public BreakerDiffDriveConfig() {
    //     try {
    //         JsonNode thisconfig = (BreakerConfigManager.getConfig("differentialDrivetrain").path("constants"));
    //         this.wheelDiameter = thisconfig.path("wheelDiameter").asDouble();
    //         this.ticksPerEncoderRotation = thisconfig.path("encoderTicksPerMotorRotation").asDouble();
    //         System.out.println("a");
    //         this.gearRatioTo1 = thisconfig.path("gearRatioToOne").asDouble();
    //         System.out.println("a");
    //         this.feedForwardKs = thisconfig.path("feedForwardKs").asDouble();
    //         System.out.println("a");
    //         this.feedForwardKv = thisconfig.path("feedForwardKv").asDouble();
    //         System.out.println("a");
    //         this.feedForwardKa = thisconfig.path("feedForwardKa").asDouble();
    //         System.out.println("a");
    //         this.robotTrackWidthInches = thisconfig.path("trackWidthInches").asDouble();
    //         System.out.println("a");
    //         this.leftPID = new PIDController(thisconfig.path("ramseteKp").asDouble(), thisconfig.path("ramseteKi").asDouble(), thisconfig.path("ramseteKd").asDouble());
    //         System.out.println("a");
    //         this.rightPID = new PIDController(thisconfig.path("ramseteKp").asDouble(), thisconfig.path("ramseteKi").asDouble(), thisconfig.path("ramseteKd").asDouble());
    //         System.out.println("a");

    //         kinematics = new DifferentialDriveKinematics(BreakerUnits.inchesToMeters(robotTrackWidthInches));
    //         System.out.println("a");
     
    //         wheelCircumference = BreakerMath.getCircumferenceFromDiameter(wheelDiameter);
    //         ticksPerInch = BreakerMath.getTicksPerInch(ticksPerEncoderRotation, gearRatioTo1, wheelDiameter);
    //         getTicksPerWheelRotation = BreakerMath.getTicksPerRotation(ticksPerEncoderRotation, gearRatioTo1);

    //     } catch (Exception e) {
    //        BreakerLog.logError("FAILED_TO_PARSE_CONFIG " + e);
    //     }
    // }

    public void setSlowModeMultipliers(double forwardMult, double turnMult) {
        slowModeForwardMultiplier = forwardMult;
        slowModeTurnMultiplier = turnMult;
    }

    public void setPidTolerences(double[] tolerences) {
        leftPID.setTolerance(tolerences[0], tolerences[1]);
        rightPID.setTolerance(tolerences[2], tolerences[3]);
    }

    public double getTicksPerInch() {
        return ticksPerInch;
    }

    public double getEncoderTicks() {
        return ticksPerEncoderRotation;
    }

    public double getGearRatioTo1() {
        return gearRatioTo1;
    }

    public double getGetTicksPerWheelRotation() {
        return getTicksPerWheelRotation;
    }

    public double getWheelCircumference() {
        return wheelCircumference;
    }

    public double getWheelDiameter() {
        return wheelDiameter;
    }

    public double getFeedForwardKa() {
        return feedForwardKa;
    }

    public double getFeedForwardKs() {
        return feedForwardKs;
    }

    public double getFeedForwardKv() {
        return feedForwardKv;
    }

    public double getRobotTrackWidthInches() {
        return robotTrackWidthInches;
    }

    public DifferentialDriveKinematics getKinematics() {
        return kinematics;
    }

    public PIDController getLeftPID() {
        return leftPID;
    }

    public PIDController getRightPID() {
        return rightPID;
    }

    public double getSlowModeForwardMultiplier() {
        return slowModeForwardMultiplier;
    }

    public double getSlowModeTurnMultiplier() {
        return slowModeTurnMultiplier;
    }
}
