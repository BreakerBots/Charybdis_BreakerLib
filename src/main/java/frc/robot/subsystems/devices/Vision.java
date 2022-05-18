// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.devices;

import java.lang.annotation.Retention;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.BreakerLib.devices.vision.photonvision.BreakerPhotonCamera;
import frc.robot.BreakerLib.devices.vision.photonvision.BreakerPhotonTarget;
import frc.robot.BreakerLib.position.geometry.BreakerPose3d;
import frc.robot.BreakerLib.position.geometry.BreakerRotation3d;
import frc.robot.BreakerLib.position.geometry.BreakerTransform3d;
import frc.robot.BreakerLib.position.geometry.BreakerTranslation3d;
import frc.robot.BreakerLib.subsystemcores.drivetrain.BreakerGenericDrivetrain;
import frc.robot.BreakerLib.subsystemcores.drivetrain.differential.BreakerDiffDrive;
import frc.robot.BreakerLib.util.math.BreakerMath;
import frc.robot.subsystems.Drive;

/** Add your docs here. */
public class Vision {
    private BreakerPhotonCamera ballCam;
    private BreakerPhotonCamera tgtCam;
    private BreakerPhotonTarget bestTargetBC;
    private BreakerPhotonTarget hubTarget;
    private BreakerPose3d hubPosition;
    private BreakerDiffDrive baseDrive;
    public Vision(Drive drivetrain) {
        baseDrive = drivetrain.getBaseDrivetrain();
        ballCam = new BreakerPhotonCamera("ballCamera", verticalFOV, horizontalFOV, new BreakerTransform3d(new BreakerTranslation3d(metersX, metersY, metersZ), new BreakerRotation3d(pitch, yaw, roll)));
        tgtCam = new BreakerPhotonCamera("targetCamera", verticalFOV, horizontalFOV, new BreakerTransform3d(new BreakerTranslation3d(metersX, metersY, metersZ), new BreakerRotation3d(pitch, yaw, roll)));
        bestTargetBC = new BreakerPhotonTarget(ballCam, ballCam.getBestTarget(), targetHightInches);
        hubPosition = new BreakerPose3d(x, y, rotation);
        hubTarget = new BreakerPhotonTarget(tgtCam, drivetrain.getBaseDrivetrain(), hubPosition, 12.0);
    }

    public Rotation2d getHubPositionRelativeToRobot() {
        Rotation2d estAng = baseDrive.getOdometryPoseMeters().getRotation().plus(BreakerMath.getPointAngleRelativeToOutherPoint(baseDrive.getOdometryPoseMeters().getTranslation(), hubPosition.get2dPoseComponent().getTranslation()));
        return hubTarget.getAssignedTargetFound() ? Rotation2d.fromDegrees(hubTarget.getYaw()) : estAng;
    }

    public BreakerPhotonCamera getBallCam() {
        return ballCam;
    }

    public BreakerPhotonCamera getTargetCam() {
        return tgtCam;
    }

    public BreakerPhotonTarget getHubTarget() {
        return hubTarget;
    }

    public boolean hasTargetsBC() {
        return ballCam.hasTargets();
    }

    public boolean hasTargetsTC() {
        return tgtCam.hasTargets();
    }

    public BreakerPhotonTarget getBestTargetBC() {
        return bestTargetBC;
    }
}
