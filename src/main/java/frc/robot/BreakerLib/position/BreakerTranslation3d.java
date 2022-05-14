// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.position;

import edu.wpi.first.math.geometry.Translation2d;

/** Represents an objects position in the X, Y, and Z axies */
public class BreakerTranslation3d {

    private double metersX;
    private double metersY;
    private double metersZ;

    /**
     * Parameters follow field coordinate system.
     */
    public BreakerTranslation3d(double metersX, double metersY, double metersZ) {
        this.metersX = metersX;
        this.metersY = metersY;
        this.metersZ = metersZ;
    }

    public Translation2d get2dTranslationComponent() {
        return new Translation2d(metersX, metersY);
    }

    public double getMetersX() {
        return metersX;
    }

    public double getMetersY() {
        return metersY;
    }

    public double getMetersZ() {
        return metersZ;
    }

    public BreakerTranslation3d plus(BreakerTranslation3d outher) {
        return new BreakerTranslation3d(this.metersX + outher.metersX, this.metersY + outher.metersY, this.metersZ + outher.metersZ);
    }
}
