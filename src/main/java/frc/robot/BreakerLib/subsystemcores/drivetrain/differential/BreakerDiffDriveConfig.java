// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.subsystemcores.drivetrain.differential;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import frc.robot.BreakerLib.util.BreakerMath;
import frc.robot.BreakerLib.util.BreakerUnits;

/** Add your docs here. */
public class BreakerDiffDriveConfig {
    private DifferentialDriveKinematics kinematics;
    private double robotTrackWidthInches;
    private double ticksPerRotation;
    private double ticksPerInch;
    private double gearRatioTo1;
    private double wheelDiameter;
    private double wheelCircumference;
    private double getTicksPerWheelRotation;
    private double feedForwardKs;
    private double feedForwardKv;
    private double feedForwardKa;
    // WHY TWO SEPARATE PIDControllers?!
    private PIDController leftPID; 
    private PIDController rightPID;

    public BreakerDiffDriveConfig(double ticksPerRotation, double gearRatioTo1, double wheelDiameter, 
        double feedForwardKs, double feedForwardKv, double feedForwardKa, double robotTrackWidthInches, PIDController leftPID, PIDController rightPID) {
        
        kinematics = new DifferentialDriveKinematics(BreakerUnits.inchesToMeters(robotTrackWidthInches));
    
        ticksPerInch = BreakerMath.getTicksPerInch(ticksPerRotation, gearRatioTo1, wheelDiameter);
        wheelDiameter = BreakerMath.getCircumferenceFromDiameter(wheelDiameter);
        getTicksPerWheelRotation = BreakerMath.getTicksPerRotation(ticksPerRotation, gearRatioTo1);

        ticksPerRotation = this.ticksPerRotation;
        gearRatioTo1 = this.gearRatioTo1;
        feedForwardKs = this.feedForwardKs;
        feedForwardKv = this.feedForwardKv;
        feedForwardKa = this.feedForwardKa;
        robotTrackWidthInches = this.robotTrackWidthInches;
        leftPID = this.leftPID;
        rightPID = this.rightPID;
    }

    public void setPidTolerences(double[] tolerences) {
        leftPID.setTolerance(tolerences[0], tolerences[1]);
        rightPID.setTolerance(tolerences[2], tolerences[3]);
    }

    public double getTicksPerInch() {
        return ticksPerInch;
    }

    public double getEncoderTicks() {
        return ticksPerRotation;
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
}
