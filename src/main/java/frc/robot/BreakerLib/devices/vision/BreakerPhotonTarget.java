// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.devices.vision;

import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonTrackedTarget;

import frc.robot.BreakerLib.util.BreakerUnits;

/** Add your docs here. */
public class BreakerPhotonTarget {
    BreakerPhotonCamera camera;
    public BreakerPhotonTarget(BreakerPhotonCamera camera) {
        this.camera = camera;
    }

    private double getTotalImageAreaInsFromDistanceAndFOV(double distanceM, double horizontalFOV, double verticalFOV) {
        double dist = BreakerUnits.metersToInches(distanceM);
        double horizL = 2 * (dist / Math.tan(Math.toRadians(horizontalFOV)));
        double vertL = 2 * (dist / Math.tan(Math.toRadians(verticalFOV)));
        return (horizL * vertL);
    }

    private void findAssignedTarget() {
        for (PhotonTrackedTarget prospTgt: camera.getAllRawTrackedTargets()) {
            PhotonUtils.calculateDistanceToTargetMeters(BreakerUnits.metersToInches(camera.getCameraHeightIns()), targetHeightMeters, cameraPitchRadians, Math.toRadians(prospTgt.getPitch()));
            
        }
    }
}
