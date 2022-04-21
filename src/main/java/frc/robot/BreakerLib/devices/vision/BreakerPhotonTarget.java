// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.devices.vision;

import java.util.List;
import java.util.function.Supplier;

import javax.xml.crypto.dsig.Transform;

import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BreakerLib.subsystemcores.drivetrain.BreakerGenericDrivetrain;
import frc.robot.BreakerLib.util.BreakerMath;
import frc.robot.BreakerLib.util.BreakerUnits;

/** Add your docs here. */
public class BreakerPhotonTarget extends SubsystemBase {
    private BreakerPhotonCamera camera;
    private double targetAreaInches;
    private double targetHightInches;
    private BreakerGenericDrivetrain drivetrain;
    private Pose2d targetLocation;
    private double maxTargetAreaDeveationInches;
    private double maxTargetCordinateDeveationInches;
    private PhotonTrackedTarget assignedTarget;
    private Supplier<PhotonTrackedTarget> assignedTargetSupplier;
    private boolean targetPreAssigned;
    private boolean targetHasBeenAssigned;
    public BreakerPhotonTarget(BreakerPhotonCamera camera, BreakerGenericDrivetrain drivetrain, Pose2d targetLocation, double targetHightInches, double maxTargetAreaDeveationInches, double maxTargetCordinateDeveationInches) {
        this.camera = camera;
        this.drivetrain = drivetrain;
        this.targetLocation = targetLocation;
        this.targetHightInches = targetHightInches;
        this.maxTargetCordinateDeveationInches = maxTargetCordinateDeveationInches;
        this.maxTargetAreaDeveationInches = maxTargetAreaDeveationInches;
        targetPreAssigned = false;
        targetHasBeenAssigned = false;
    }

    public BreakerPhotonTarget(BreakerPhotonCamera camera, Supplier<PhotonTrackedTarget> assignedTargetSupplier, double targetHightInches) {
        this.camera = camera;
        this.assignedTargetSupplier = assignedTargetSupplier;
        assignedTarget = assignedTargetSupplier.get();
        this.targetHightInches = targetHightInches;
    }

    private double getTotalImageAreaInsFromDistanceAndFOV(double distanceMeters, double horizontalFOV, double verticalFOV) {
        double dist = BreakerUnits.metersToInches(distanceMeters);
        double horizL = 2 * (dist / Math.tan(Math.toRadians(horizontalFOV)));
        double vertL = 2 * (dist / Math.tan(Math.toRadians(verticalFOV)));
        return (horizL * vertL);
    }

    private void findAssignedTarget() {
        if (!targetPreAssigned && camera.getCameraHasTargets()) {
            for (PhotonTrackedTarget prospTgt: camera.getAllRawTrackedTargets()) {
                double distanceMeters = PhotonUtils.calculateDistanceToTargetMeters(BreakerUnits.metersToInches(camera.getCameraHeightIns()), BreakerUnits.inchesToMeters(targetHightInches), Math.toRadians(camera.getCameraAngle()), Math.toRadians(prospTgt.getPitch()));
                double totalA = getTotalImageAreaInsFromDistanceAndFOV(distanceMeters, camera.getHorizontalFOV(), camera.getVerticalFOV());
                double pta = (totalA * prospTgt.getArea());
                Translation2d prospTgtTransl = PhotonUtils.estimateCameraToTargetTranslation(distanceMeters, Rotation2d.fromDegrees(prospTgt.getYaw()));
                Transform2d prospTgtTransf = PhotonUtils.estimateCameraToTarget(prospTgtTransl, targetLocation, drivetrain.getOdometryPoseMeters().getRotation());
                Pose2d prospTgtPose = drivetrain.getOdometryPoseMeters().transformBy(prospTgtTransf);

                if (BreakerMath.getIsRoughlyEqualTo(pta, targetAreaInches, maxTargetAreaDeveationInches) &&
                    BreakerMath.getIsRoughlyEqualTo(prospTgtPose.getX(), targetLocation.getX(), BreakerUnits.inchesToMeters(maxTargetCordinateDeveationInches)) &&
                    BreakerMath.getIsRoughlyEqualTo(prospTgtPose.getY(), targetLocation.getY(), BreakerUnits.inchesToMeters(maxTargetCordinateDeveationInches))) {
                        assignedTarget = prospTgt;
                        targetHasBeenAssigned = true;
                }
            }
        } else if (camera.getCameraHasTargets()) {
            assignedTarget = assignedTargetSupplier.get();
        }
    }

    public double getYaw() {
        return assignedTarget.getYaw();
    }

    public double getPitch() {
        return assignedTarget.getPitch();
    }

    public double getSkew() {
        return assignedTarget.getSkew();
    }

    public double getArea() {
        return assignedTarget.getArea();
    }

    public List<TargetCorner> fwfe() {
        return assignedTarget.getCorners();
    }

    @Override
    public void periodic() {
        findAssignedTarget();
    }




}
