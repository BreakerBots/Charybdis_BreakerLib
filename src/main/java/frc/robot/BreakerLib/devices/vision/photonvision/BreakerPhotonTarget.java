// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.BreakerLib.devices.vision.photonvision;

// import java.lang.annotation.Retention;
// import java.util.List;
// import java.util.function.Supplier;

// import javax.xml.crypto.dsig.Transform;

// import org.photonvision.PhotonUtils;
// import org.photonvision.targeting.PhotonTrackedTarget;
// import org.photonvision.targeting.TargetCorner;

// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.math.geometry.Rotation2d;
// import edu.wpi.first.math.geometry.Transform2d;
// import edu.wpi.first.math.geometry.Translation2d;
// import edu.wpi.first.math.util.Units;
// import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import frc.robot.BreakerLib.subsystemcores.drivetrain.BreakerGenericDrivetrain;
// import frc.robot.BreakerLib.util.BreakerMath;
// import frc.robot.BreakerLib.util.BreakerUnits;

// /** Add your docs here. */
// public class BreakerPhotonTarget extends SubsystemBase {
//     private BreakerPhotonCamera camera;
//     private double targetHightInches;
//     private BreakerGenericDrivetrain drivetrain;
//     private Pose2d targetLocation;
//     private double maxTargetCordinateDeveationInches;
//     private PhotonTrackedTarget assignedTarget;
//     private Supplier<PhotonTrackedTarget> assignedTargetSupplier;
//     private boolean targetPreAssigned;
//     private boolean assignedTargetFound;
//     public BreakerPhotonTarget(BreakerPhotonCamera camera, BreakerGenericDrivetrain drivetrain, Pose2d targetLocation, double targetHightInches, double maxTargetCordinateDeveationInches) {
//         this.camera = camera;
//         this.drivetrain = drivetrain;
//         this.targetLocation = targetLocation;
//         this.targetHightInches = targetHightInches;
//         this.maxTargetCordinateDeveationInches = maxTargetCordinateDeveationInches;
//         targetPreAssigned = false;
//         assignedTargetFound = false;
//     }

//     public BreakerPhotonTarget(BreakerPhotonCamera camera, Supplier<PhotonTrackedTarget> assignedTargetSupplier, double targetHightInches) {
//         this.camera = camera;
//         this.assignedTargetSupplier = assignedTargetSupplier;
//         assignedTarget = assignedTargetSupplier.get();
//         assignedTargetFound = (assignedTarget == null) ? false : true;
//         this.targetHightInches = targetHightInches;
//     }

//     private void findAssignedTarget() {
//         int runs = 0;
//         boolean foundTgt = false;
//         if (!targetPreAssigned && camera.getCameraHasTargets()) {
//             for (PhotonTrackedTarget prospTgt: camera.getAllRawTrackedTargets()) {
//                 double distanceMeters = PhotonUtils.calculateDistanceToTargetMeters(BreakerUnits.inchesToMeters(camera.getCameraHeightIns()), BreakerUnits.inchesToMeters(targetHightInches), Math.toRadians(camera.getCameraAngle()), Math.toRadians(prospTgt.getPitch()));
//                 Translation2d prospTgtTransl = PhotonUtils.estimateCameraToTargetTranslation(distanceMeters, Rotation2d.fromDegrees(prospTgt.getYaw()));
//                 Transform2d prospTgtTransf = PhotonUtils.estimateCameraToTarget(prospTgtTransl, targetLocation, drivetrain.getOdometryPoseMeters().getRotation());
//                 Pose2d prospTgtPose = drivetrain.getOdometryPoseMeters().transformBy(prospTgtTransf);

//                 if (BreakerMath.getIsRoughlyEqualTo(prospTgtPose.getX(), targetLocation.getX(), BreakerUnits.inchesToMeters(maxTargetCordinateDeveationInches)) &&
//                     BreakerMath.getIsRoughlyEqualTo(prospTgtPose.getY(), targetLocation.getY(), BreakerUnits.inchesToMeters(maxTargetCordinateDeveationInches))) {
//                         assignedTarget = prospTgt;
//                         foundTgt = true;
//                 }
//                 runs ++;
//             }
//             if (runs >= camera.getNumberOfCameraTargets() && foundTgt == true) {
//                 assignedTargetFound = true;
//             } else {
//                 assignedTargetFound = false;
//             }
//         } else if (camera.getCameraHasTargets()) {
//             assignedTarget = assignedTargetSupplier.get();
//             assignedTargetFound = (assignedTarget == null) ? false : true;
//         }
//     }

//     public double getTargetDistanceMeters() {
//         return PhotonUtils.calculateDistanceToTargetMeters(BreakerUnits.inchesToMeters(camera.getCameraHeightIns()), BreakerUnits.inchesToMeters(targetHightInches), Math.toRadians(camera.getCameraAngle()), Math.toRadians(getPitch()));
//     }

//     public double getTargetDistanceInches() {
//         return BreakerUnits.metersToInches(getTargetDistanceMeters());
//     }

//     public Translation2d getTargetTranslationFromCamera() {
//         return PhotonUtils.estimateCameraToTargetTranslation(getTargetDistanceMeters(), Rotation2d.fromDegrees(getYaw()));
//     }

//     public Translation2d getTargetTranslationFromField() {
//         return drivetrain.getOdometryPoseMeters().getTranslation().plus(getTargetTranslationFromCamera());
//     }

//     public double getYaw() {
//         return assignedTarget.getYaw();
//     }

//     public double getPitch() {
//         return assignedTarget.getPitch();
//     }

//     public double getSkew() {
//         return assignedTarget.getSkew();
//     }

//     public double getArea() {
//         return assignedTarget.getArea();
//     }

//     public List<TargetCorner> getTargetCorners() {
//         return assignedTarget.getCorners();
//     }

//     public boolean getAssignedTargetFound() {
//         return assignedTargetFound;
//     }

//     @Override
//     public void periodic() {
//         findAssignedTarget();
//     }




// }
