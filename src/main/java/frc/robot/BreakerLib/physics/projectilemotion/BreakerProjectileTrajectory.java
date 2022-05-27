// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.physics.projectilemotion;

import org.opencv.core.Mat;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import frc.robot.BreakerLib.devices.sensors.BreakerUltrasonicSensor;
import frc.robot.BreakerLib.physics.BreakerVector3;
import frc.robot.BreakerLib.position.geometry.BreakerPose3d;
import frc.robot.BreakerLib.position.geometry.BreakerRotation3d;
import frc.robot.BreakerLib.position.movement.BreakerMovementState2d;
import frc.robot.BreakerLib.util.math.BreakerUnits;

/** Represents a drag affected object's balisitc trajectory without external propulsion (note: force vector matches field cordnate system and is field relative (EX: Z is up)) */
public class BreakerProjectileTrajectory {
    private BreakerProjectile projectile;
    private BreakerVector3 initialVels;
    private BreakerPose3d launchPoint;
    public BreakerProjectileTrajectory(BreakerProjectile projectile, BreakerVector3 initialVels, BreakerPose3d launchPoint) {
        this.projectile = projectile;
        this.initialVels = initialVels;
    }

    public Translation2d get2dTranslationAtGivenTime(double time) {
        double x = getHorizontalDistInGivenAxisAtGivenTime(time, initialVels.getForceX());
        double y = getHorizontalDistInGivenAxisAtGivenTime(time, initialVels.getForceY());
        return new Translation2d(x, y).plus(launchPoint.get2dPoseComponent().getTranslation());
    }

    private double getHorizontalDistInGivenAxisAtGivenTime(double time, double initialForce) {
        return (projectile.getTermanalVelSq() / BreakerUnits.METERS_PER_SECOND_SQUARED_IN_G) * Math.log((projectile.getTermanalVelSq() + (BreakerUnits.METERS_PER_SECOND_SQUARED_IN_G * initialForce * time)) / projectile.getTermanalVelSq());
    }

    private double getTimeAtGivenDistance(double distance, double initialForce) {
        double exp = Math.exp(distance / (projectile.getTermanalVelSq() / BreakerUnits.METERS_PER_SECOND_SQUARED_IN_G));
        double vtExp = (projectile.getTermanalVelSq() * exp) - projectile.getTermanalVelSq();
        return vtExp / BreakerUnits.METERS_PER_SECOND_SQUARED_IN_G / initialForce;
    }

    public double getTimeOfFightToTarget(Translation2d targedLocation) {
        return getTimeAtGivenDistance(targedLocation.getDistance(launchPoint.get2dPoseComponent().getTranslation()), Math.hypot(initialVels.getForceX(), initialVels.getForceY()));
    }
    
    private double getVelInGivenAxisAtGivenTime(double time, double initialForce) {
        return projectile.getTermanalVelSq() * initialForce / (projectile.getTermanalVelSq() + BreakerUnits.METERS_PER_SECOND_SQUARED_IN_G * initialForce * time);
    }

    public BreakerVector3 getForceVectorAtGivenTime(double time) {
        double x = getVelInGivenAxisAtGivenTime(time, initialVels.getForceX());
        double y = getVelInGivenAxisAtGivenTime(time, initialVels.getForceY());
        double z = getVelInGivenAxisAtGivenTime(time, initialVels.getForceZ());
        return new BreakerVector3(x, y, z);
    }

    public Translation2d getMovingLaunchCorectionAsNewTargetLocation(BreakerMovementState2d fieldRelativeMovementState, Translation2d targetLocation) {
        BreakerProjectileTrajectory trajectory = new BreakerProjectileTrajectory(projectile, new BreakerVector3(initialVels.getForceX() + fieldRelativeMovementState.getVelocityComponent().getLinearForces().getForceX(), initialVels.getForceY() + fieldRelativeMovementState.getVelocityComponent().getLinearForces().getForceY(), initialVels.getForceZ()), launchPoint);
        Translation2d cor = targetLocation.minus(trajectory.get2dTranslationAtGivenTime(getTimeOfFightToTarget(targetLocation)));
        return targetLocation.plus(cor);
    }

    public BreakerVector3 getMovingLaunchCorectionAsNewLaunchForces(BreakerMovementState2d fieldRelativeMovementState, Translation2d targetLocation) {
        BreakerProjectileTrajectory trajectory = new BreakerProjectileTrajectory(projectile, new BreakerVector3(initialVels.getForceX() + fieldRelativeMovementState.getVelocityComponent().getLinearForces().getForceX(), initialVels.getForceY() + fieldRelativeMovementState.getVelocityComponent().getLinearForces().getForceY(), initialVels.getForceZ()), launchPoint);
        double reqImpactTime = getTimeOfFightToTarget(targetLocation);
        BreakerVector3 reqImpactVec = getForceVectorAtGivenTime(reqImpactTime);
        BreakerVector3 predImpactVec = trajectory.getForceVectorAtGivenTime(reqImpactTime);
        BreakerVector3 corVec = reqImpactVec.minus(predImpactVec);
        return initialVels.plus(corVec);
    }
}
