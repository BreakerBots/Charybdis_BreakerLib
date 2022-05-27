// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.physics.projectilemotion;

import javax.crypto.spec.GCMParameterSpec;

import frc.robot.BreakerLib.util.math.BreakerUnits;

/** Add your docs here. */
public class BreakerProjectile {
    private double massKg;
    private double dragCoeffiecnt;
    private double crossSectionalAreaMetersSq;
    private double termanalVelMetersPerSec;
    private double drag;
    private double termanalVelSq;
    public BreakerProjectile(double massKg, double dragCoeffiecnt, double crossSectionalAreaMetersSq, double termanalVelMetersPerSec) {
        this.massKg = massKg;
        this.dragCoeffiecnt = dragCoeffiecnt;
        this.crossSectionalAreaMetersSq = crossSectionalAreaMetersSq;
        this.termanalVelMetersPerSec = termanalVelMetersPerSec;
        termanalVelSq = Math.pow(termanalVelMetersPerSec, 2);
        drag = 0.5 * dragCoeffiecnt * BreakerUnits.AIR_DENCITY_KG_PER_METER_CUBED * crossSectionalAreaMetersSq * termanalVelSq;
    }
    
    public double getCrossSectionalAreaMetersSq() {
        return crossSectionalAreaMetersSq;
    }

    public double getDrag() {
        return drag;
    }

    public double getDragCoeffiecnt() {
        return dragCoeffiecnt;
    }

    public double getMassKg() {
        return massKg;
    }

    public double getTermanalVelMetersPerSec() {
        return termanalVelMetersPerSec;
    }

    public double getTermanalVelSq() {
        return termanalVelSq;
    }
}
