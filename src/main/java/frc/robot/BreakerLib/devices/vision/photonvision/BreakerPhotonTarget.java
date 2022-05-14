// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.devices.vision.photonvision;

import java.util.List;
import java.util.function.Supplier;

import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonTrackedTarget;
import org.photonvision.targeting.TargetCorner;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BreakerLib.position.BreakerPose3d;
import frc.robot.BreakerLib.subsystemcores.drivetrain.BreakerGenericDrivetrain;
import frc.robot.BreakerLib.util.math.BreakerMath;
import frc.robot.BreakerLib.util.math.BreakerUnits;

/** Tracks and identifies pre-defined targets with target data from photon camera. */
public class BreakerPhotonTarget extends SubsystemBase {

    private BreakerPhotonCamera camera;
    private double targetHeightInches;
    private BreakerGenericDrivetrain drivetrain;
    private Pose2d targetLocation;
    private double maxTargetCordinateDeveationInches;
    private PhotonTrackedTarget assignedTarget;
    private Supplier<PhotonTrackedTarget> assignedTargetSupplier;
    private boolean targetPreAssigned;
    private boolean assignedTargetFound;

    /** Creates a new BreakerPhotonTarget that will activly search for a target that meets its pre-difined paramaters
     * 
     * @param camera Photon camera.
     * @param drivetrain Robot drivetrain for position tracking.
     * @param targetPosition Position of target relative to field.
     * @param maxTargetCoordinateDeviationInches 
     */
    public BreakerPhotonTarget(BreakerPhotonCamera camera, BreakerGenericDrivetrain drivetrain,
            BreakerPose3d targetPosition, double maxTargetCoordinateDeviationInches) {
        this.camera = camera;
        this.drivetrain = drivetrain;
        targetLocation = targetPosition.get2dPoseComponent();
        this.targetHeightInches = Units.metersToInches(targetPosition.getTranslationComponent().getMetersZ());
        this.maxTargetCordinateDeveationInches = maxTargetCoordinateDeviationInches;
        targetPreAssigned = false;
        assignedTargetFound = false;
    }

    /** Creates a new BreakerPhotonTarget with a given predefined target.
     * 
     * @param camera Photon camera.
     * @param assignedTargetSupplier Supplies photon camera target.
     * @param targetHeightInches Target height from ground.
     */
    public BreakerPhotonTarget(BreakerPhotonCamera camera, Supplier<PhotonTrackedTarget> assignedTargetSupplier,
            double targetHeightInches) {
        this.camera = camera;
        this.assignedTargetSupplier = assignedTargetSupplier;
        assignedTarget = assignedTargetSupplier.get();
        assignedTargetFound = (assignedTarget == null) ? false : true;
        this.targetHeightInches = targetHeightInches;
    }

    /** Logic used to find a target. */
    private void findAssignedTarget() {
        int runs = 0;
        boolean foundTgt = false;
        if (!targetPreAssigned && camera.getCameraHasTargets()) {
            // Loops through tracked targets.
            for (PhotonTrackedTarget prospTgt : camera.getAllRawTrackedTargets()) {
                double distanceMeters = PhotonUtils.calculateDistanceToTargetMeters( // Distance from target based on constant parameters
                        BreakerUnits.inchesToMeters(camera.getCameraHeightIns()),
                        BreakerUnits.inchesToMeters(targetHeightInches), Math.toRadians(camera.getCameraAngle()),
                        Math.toRadians(prospTgt.getPitch()));

                Translation2d prospTgtTranslation = PhotonUtils.estimateCameraToTargetTranslation(distanceMeters, // Translation of target relative to camera
                        Rotation2d.fromDegrees(prospTgt.getYaw()));

                Transform2d prospTgtTransform = PhotonUtils.estimateCameraToTarget(prospTgtTranslation, targetLocation, // Transformation from target to camera.
                        drivetrain.getOdometryPoseMeters().getRotation());

                Pose2d prospTgtPose = drivetrain.getOdometryPoseMeters().transformBy(prospTgtTransform); // Target position relative to field.

                // checks if transform result is close enough to required target location
                if (BreakerMath.isRoughlyEqualTo(prospTgtPose.getX(), targetLocation.getX(),
                        Units.inchesToMeters(maxTargetCordinateDeveationInches)) &&
                        BreakerMath.isRoughlyEqualTo(prospTgtPose.getY(), targetLocation.getY(),
                                Units.inchesToMeters(maxTargetCordinateDeveationInches))) {
                    assignedTarget = prospTgt;
                    foundTgt = true;
                }
                runs++;
            }

            // checks if a target has been sugessfully found and assigned
            if (runs >= camera.getNumberOfCameraTargets() && foundTgt == true) {
                assignedTargetFound = true;
            } else {
                assignedTargetFound = false;
            }

        // assigns the pre-supplyed target if the camera has targets
        } else if (camera.getCameraHasTargets()) {
            assignedTarget = assignedTargetSupplier.get();
            assignedTargetFound = (assignedTarget == null) ? false : true;
        }
    }

    public double getTargetDistanceMeters() {
        return PhotonUtils.calculateDistanceToTargetMeters(BreakerUnits.inchesToMeters(camera.getCameraHeightIns()),
                BreakerUnits.inchesToMeters(targetHeightInches), Math.toRadians(camera.getCameraAngle()),
                Math.toRadians(getPitch()));
    }

    public double getTargetDistanceInches() {
        return Units.metersToInches(getTargetDistanceMeters());
    }

    public Translation2d getTargetTranslationFromCamera() {
        return PhotonUtils.estimateCameraToTargetTranslation(getTargetDistanceMeters(),
                Rotation2d.fromDegrees(getYaw()));
    }

    public Translation2d getTargetTranslationFromField() {
        return drivetrain.getOdometryPoseMeters().getTranslation().plus(getTargetTranslationFromCamera());
    }

    public Pose2d getRobotPose() {
        return PhotonUtils.estimateFieldToRobot(
                PhotonUtils.estimateCameraToTarget(getTargetTranslationFromCamera(), targetLocation,
                        drivetrain.getOdometryPoseMeters().getRotation()),
                targetLocation, camera.getCameraPositionRelativeToRobot());
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

    public List<TargetCorner> getTargetCorners() {
        return assignedTarget.getCorners();
    }

    public boolean getAssignedTargetFound() {
        return assignedTargetFound;
    }

    @Override
    public void periodic() {
        findAssignedTarget();
    }

}
