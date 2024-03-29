// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.trajectorypaths;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.BreakerLib.auto.trajectory.diff.BreakerRamsete;
import frc.robot.BreakerLib.auto.trajectory.management.BreakerStartTrajectoryPath;
import frc.robot.BreakerLib.auto.trajectory.management.BreakerTrajectoryPath;
import frc.robot.subsystems.Drive;

public class CircleDemoTrajectory extends SequentialCommandGroup {
  /** Creates a new testTrajectory. */
  BreakerTrajectoryPath partOne;
  TrajectoryConfig config;
  Pose2d startingPose;
  Pose2d endPose;
  Translation2d WP1;
  Translation2d WP2;
  List<Translation2d> waypoints;
  public CircleDemoTrajectory(Drive drivetrain) {

    startingPose = new Pose2d(0, 0, Rotation2d.fromDegrees(0));
    endPose = new Pose2d(0, 0, Rotation2d.fromDegrees(0));
    config = new TrajectoryConfig(0.5, 0.5);
    waypoints = new ArrayList<>();
    waypoints.add(new Translation2d(0.848, 0.352));
    waypoints.add(new Translation2d(1.2, 1.2));
    waypoints.add(new Translation2d(0.848, 2.048));
    waypoints.add(new Translation2d(0, 2.4));
    waypoints.add(new Translation2d(-0.848, 2.038));
    waypoints.add(new Translation2d(-1.2, 1.2));
    waypoints.add(new Translation2d(-0.848, 0.352));

    // creates first trajecotry to follow
    partOne = new BreakerTrajectoryPath(TrajectoryGenerator.generateTrajectory(startingPose, waypoints, endPose, config)); 

    addCommands(
      new BreakerStartTrajectoryPath(drivetrain.getBaseDrivetrain(), startingPose),
      new BreakerRamsete(partOne, drivetrain.getBaseDrivetrain(), 2.0, 0.7, true, drivetrain)
    );
  }
}
