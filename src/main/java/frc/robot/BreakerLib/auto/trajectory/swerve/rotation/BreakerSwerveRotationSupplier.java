// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.auto.trajectory.swerve.rotation;

import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.function.Supplier;

import javax.print.Doc;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.interpolation.Interpolatable;
import frc.robot.BreakerLib.util.math.interpolation.BreakerInterpolableDouble;
import frc.robot.BreakerLib.util.math.interpolation.interpolateingmaps.BreakerInterpolateingTreeMap;

/** Add your docs here. */
public class BreakerSwerveRotationSupplier {
    private BreakerRotationPoint[] rotationPoints;
    private Supplier<Rotation2d> externalSupplier;
    private BreakerInterpolateingTreeMap<Double, BreakerInterpolableDouble> interMap;
    private boolean usesSupplier;
    private double currentTime = 0.0;
    public BreakerSwerveRotationSupplier(BreakerRotationPoint... rotationPoints) {
        this.rotationPoints = rotationPoints;
        usesSupplier = false;
        for (BreakerRotationPoint point: rotationPoints) {
            interMap.put(point.getTimeOfRotation(), new BreakerInterpolableDouble(point.getRotation().getRadians()));
        }
    }

    public BreakerSwerveRotationSupplier(Supplier<Rotation2d> externalSupplier) {
        this.externalSupplier = externalSupplier;
        usesSupplier = true;
    }

    public void setCurrentTime(double currentTime) {
        this.currentTime = currentTime;
    }

    public Rotation2d getRotation() {
        if (usesSupplier) {
            return externalSupplier.get();
        }
        return new Rotation2d(interMap.getInterpolatedValue(currentTime).getValue());
    }
}
