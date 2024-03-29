// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.auto.trajectory.swerve;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import frc.robot.BreakerLib.position.odometry.BreakerGenericOdometer;
import frc.robot.BreakerLib.subsystem.cores.drivetrain.swerve.BreakerSwerveDrive;

/** Add your docs here. */
public class BreakerFollowSwerveTrajectoryConfig {

    private PIDController xPosPID;
    private PIDController yPosPID;
    private ProfiledPIDController tAngPID;
    private BreakerSwerveDrive drivetrain;
    private Constraints constraints;
    private BreakerGenericOdometer odometer;

    public BreakerFollowSwerveTrajectoryConfig(BreakerSwerveDrive drivetrain, PIDController xPositionPID, PIDController yPositionPID,
            double thetaAngleKp, double thetaAngleKi, double thetaAngleKd, double maxAllowedThetaVel,
            double maxAllowedThetaAccel) {
        xPosPID = xPositionPID;
        yPosPID = yPositionPID;
        constraints = new Constraints(maxAllowedThetaVel, maxAllowedThetaAccel);
        tAngPID = new ProfiledPIDController(thetaAngleKp, thetaAngleKi, thetaAngleKd, constraints);
        this.drivetrain = drivetrain;
        this.odometer = drivetrain;
    }

    public BreakerFollowSwerveTrajectoryConfig(BreakerSwerveDrive drivetrain, BreakerGenericOdometer odometer, PIDController xPositionPID, PIDController yPositionPID,
            double thetaAngleKp, double thetaAngleKi, double thetaAngleKd, double maxAllowedThetaVel,
            double maxAllowedThetaAccel) {
        xPosPID = xPositionPID;
        yPosPID = yPositionPID;
        constraints = new Constraints(maxAllowedThetaVel, maxAllowedThetaAccel);
        tAngPID = new ProfiledPIDController(thetaAngleKp, thetaAngleKi, thetaAngleKd, constraints);
        this.drivetrain = drivetrain;
        this.odometer = odometer;
    }

    public void setPidTolerences(double[] tolernces) {
        xPosPID.setTolerance(tolernces[0], tolernces[1]);
        yPosPID.setTolerance(tolernces[2], tolernces[3]);
        tAngPID.setTolerance(tolernces[4], tolernces[5]);
    }

    public ProfiledPIDController getThetaAngPID() {
        return tAngPID;
    }

    public PIDController getxPosPID() {
        return xPosPID;
    }

    public PIDController getyPosPID() {
        return yPosPID;
    }

    public BreakerSwerveDrive getDrivetrain() {
        return drivetrain;
    }

    public BreakerGenericOdometer getOdometer() {
        return odometer;
    }
}
