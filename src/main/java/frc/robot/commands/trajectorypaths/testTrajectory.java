// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.trajectorypaths;

import java.util.ArrayList;
import java.util.List;

import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.Trajectory.State;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.BreakerLib.auto.trajectory.BreakerStartTrajectoryPath;
import frc.robot.BreakerLib.auto.trajectory.diff.BreakerRamsete;
import frc.robot.BreakerLib.devices.sensors.BreakerPigeon2;
import frc.robot.BreakerLib.subsystemcores.drivetrain.differential.BreakerDiffDrive;
import frc.robot.subsystems.Drive;

public class testTrajectory extends SequentialCommandGroup {
  /** Creates a new testTrajectory. */
  Trajectory partOne;
  TrajectoryConfig config;
  Pose2d startingPose;
  Pose2d endPose;
  Translation2d WP1;
  Translation2d WP2;
  List<Translation2d> waypoints;
  public testTrajectory(Drive drivetrain, BreakerPigeon2 pigeon2) {

    startingPose = new Pose2d(0, 0, Rotation2d.fromDegrees(0.0));
    endPose = new Pose2d(0,1, Rotation2d.fromDegrees(0.0));
    config = new TrajectoryConfig(0.1, 0.5);
    WP1 = new Translation2d(0, 0.25);
    WP2 = new Translation2d(0, 0.75);
    waypoints = new ArrayList<>();
    waypoints.add(WP1);
    waypoints.add(WP2);
    

    // creates first trajecotry to follow
    partOne = TrajectoryGenerator.generateTrajectory(startingPose, waypoints, endPose, config); 

    addCommands(
      new BreakerStartTrajectoryPath(drivetrain.getBaseDrivetrain(), startingPose, pigeon2),
      new BreakerRamsete(partOne, drivetrain.getBaseDrivetrain(), drivetrain, 0.0, 1.0, 0.1, 0.5, 3.0, true)
    );
  }
}
