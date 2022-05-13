// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.devices;

import java.lang.annotation.Retention;

import frc.robot.BreakerLib.devices.vision.photonvision.BreakerPhotonCamera;
import frc.robot.BreakerLib.devices.vision.photonvision.BreakerPhotonTarget;
import frc.robot.BreakerLib.position.BreakerRotation3d;
import frc.robot.BreakerLib.position.BreakerTransform3d;
import frc.robot.BreakerLib.position.BreakerTranslation3d;

/** Add your docs here. */
public class Vision {
    private BreakerPhotonCamera ballCam;
    private BreakerPhotonCamera tgtCam;
    private BreakerPhotonTarget bestTargetBC;
    public Vision() {
        ballCam = new BreakerPhotonCamera("ballCamera", verticalFOV, horizontalFOV, new BreakerTransform3d(new BreakerTranslation3d(metersX, metersY, metersZ), new BreakerRotation3d(pitch, yaw, roll)));
        tgtCam = new BreakerPhotonCamera("targetCamera", verticalFOV, horizontalFOV, new BreakerTransform3d(new BreakerTranslation3d(metersX, metersY, metersZ), new BreakerRotation3d(pitch, yaw, roll)));
        bestTargetBC = new BreakerPhotonTarget(ballCam, ballCam.getBestTarget(), targetHightInches);
    }

    public boolean hasTargetsBC() {
        return ballCam.getCameraHasTargets();
    }

    public boolean hasTargetsTC() {
        return tgtCam.getCameraHasTargets();
    }

    public BreakerPhotonTarget getBestTargetBC() {
        return bestTargetBC;
    }
}
