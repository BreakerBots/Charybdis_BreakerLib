// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.trajectorypaths;

import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.Trajectory;
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
  Pose2d startingPose;
  public testTrajectory(Drive drivetrain, BreakerPigeon2 pigeon2) {

    // creates first trajecotry to follow
    partOne = TrajectoryGenerator.generateTrajectory(start, interiorWaypoints, end, config);

    // sets the field relative pose that the robot will be starting in
    startingPose = new Pose2d(x, y, rotation);

    addCommands(
      new BreakerStartTrajectoryPath(drivetrain.getBaseDrivetrain(), startingPose, pigeon2),
      new BreakerRamsete(partOne, drivetrain.getBaseDrivetrain(), drivetrain, ramseteB, ramseteZeta, maxVel, maxAccel, maxVoltage)
    );
  }
}
