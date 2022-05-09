// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util.math.interpolation;

import java.util.HashMap;

import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.BreakerLib.physics.BreakerVector2;

/** Add your docs here. */
public class BreakerInterpolateingVectorMap {
    private HashMap<Double, BreakerVector2> indexesAndVectors;
    public BreakerInterpolateingVectorMap(HashMap<Double, BreakerVector2> indexesAndVectors) {
        this.indexesAndVectors = indexesAndVectors;
    }

    public BreakerVector2 getInterpolatedVector(Double interpolendValue) {
        boolean highKeySet = false;
        Double lowKey = interpolendValue;
        Double highKey = interpolendValue;
        for (Double key: indexesAndVectors.keySet()) {
            if (key <= interpolendValue && !(key <= lowKey)) {
                lowKey = key;
            } else if (key >= interpolendValue && !(key >= highKey && !highKeySet)) {
                highKey = key;
            }
        }

        BreakerVector2 lowVec = indexesAndVectors.get(lowKey);
        BreakerVector2 highVec =  indexesAndVectors.get(highKey);

        double lowF = lowVec.getForce();
        Rotation2d lowR = lowVec.getForceRotation();

        double highF = highVec.getForce();
        Rotation2d highR = highVec.getForceRotation();

        double interF = interpolate(interpolendValue, lowKey, highKey, lowF, highF);
        double interR = interpolate(interpolendValue, lowKey, highKey, lowR.getRadians(), highR.getRadians());

        return BreakerVector2.fromForceAndRotation(new Rotation2d(interR), interF);
    }

    private double interpolate(double knownX, double lowX, double highX, double lowY, double highY) {
       return (((knownX - lowX) * (highY - lowY)) / (highX - lowX)) + lowY;

    }
}
