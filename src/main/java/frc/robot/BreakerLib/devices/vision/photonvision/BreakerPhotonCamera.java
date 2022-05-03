// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.BreakerLib.devices.vision.photonvision;

// import java.lang.annotation.Target;

// import org.photonvision.PhotonCamera;
// import org.photonvision.PhotonTargetSortMode;
// import org.photonvision.targeting.PhotonPipelineResult;
// import org.photonvision.targeting.PhotonTrackedTarget;

// /** Add your docs here. */
// public class BreakerPhotonCamera {
//     private PhotonCamera camera;
//     private double cameraAngle;
//     private double cameraHeightIns;
//     private double verticalFOV;
//     private double horizontalFOV;
//     public BreakerPhotonCamera(String cameraName, double cameraAngle, double cameraHeightIns, double verticalFOV, double horizontalFOV) {
//         camera = new PhotonCamera(cameraName);
//         this.cameraAngle = cameraAngle;
//         this.cameraHeightIns = cameraHeightIns;
//         this.verticalFOV = verticalFOV;
//         this.horizontalFOV = horizontalFOV;
//     }

//     public PhotonPipelineResult getLatestRawResult() {
//         return camera.getLatestResult();
//     }

//     public Boolean getCameraHasTargets() {
//         return getLatestRawResult().hasTargets();
//     }


//     public PhotonTrackedTarget[] getAllRawTrackedTargets() {
//         return getLatestRawResult().targets.toArray(new PhotonTrackedTarget[getLatestRawResult().targets.size()]);
//     }

//     public int getNumberOfCameraTargets() {
//         return getAllRawTrackedTargets().length;
//     }

//     public double getPipelineLatancyMiliseconds() {
//         return getLatestRawResult().getLatencyMillis();
//     }

//     public void setPipelineNumber(int pipeNum) {
//         camera.setPipelineIndex(pipeNum);
//     }

//     public int getCurrentPipelineNumber() {
//         return camera.getPipelineIndex();
//     }

//     public PhotonTrackedTarget getBestTarget() {
//         return getLatestRawResult().getBestTarget();
//     }

//     public double getCameraHeightIns() {
//         return cameraHeightIns;
//     }

//     public double getCameraAngle() {
//         return cameraAngle;
//     }

//     public double getVerticalFOV() {
//         return verticalFOV;
//     }

//     public double getHorizontalFOV() {
//         return horizontalFOV;
//     }

// }
