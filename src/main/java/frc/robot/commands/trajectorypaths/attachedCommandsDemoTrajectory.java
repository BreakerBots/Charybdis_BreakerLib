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
import frc.robot.BreakerLib.auto.trajectory.management.conditionalcommand.BreakerPositionTriggeredCommand;
import frc.robot.BreakerLib.auto.trajectory.management.conditionalcommand.BreakerTimeTriggeredCommand;
import frc.robot.BreakerLib.devices.sensors.BreakerPigeon2;
import frc.robot.commands.intake.ToggleIntake;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Intake;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class attachedCommandsDemoTrajectory extends SequentialCommandGroup {
  /** Creates a new attachedCommandsDemoTrajectory. */
   BreakerTrajectoryPath partOne;
   TrajectoryConfig config;
   Pose2d startingPose;
   Pose2d endPose;
   Translation2d WP1;
   Translation2d WP2;
   List<Translation2d> waypoints;

   public attachedCommandsDemoTrajectory(Drive drivetrain, BreakerPigeon2 pigeon2, Intake intake) {
 
     startingPose = new Pose2d(0, 0, Rotation2d.fromDegrees(0));
     endPose = new Pose2d(4, 0, Rotation2d.fromDegrees(0));
     config = new TrajectoryConfig(0.5, 0.5);
     waypoints = new ArrayList<>();
     waypoints.add(new Translation2d(1, 1));
     waypoints.add(new Translation2d(2, 0));
     waypoints.add(new Translation2d(3, -1));
 
     // creates first trajecotry to follow
     partOne = new BreakerTrajectoryPath(
       TrajectoryGenerator.generateTrajectory(startingPose, waypoints, endPose, config),
       new BreakerPositionTriggeredCommand(
         new Pose2d(2, 0, Rotation2d.fromDegrees(-45)), 
         drivetrain.getBaseDrivetrain()::getOdometryPoseMeters, 
         new Pose2d(0.25, 0.25, Rotation2d.fromDegrees(180)), new ToggleIntake(intake)
         )
        );
 
     addCommands(
       new BreakerStartTrajectoryPath(drivetrain.getBaseDrivetrain(), startingPose, pigeon2),
       new BreakerRamsete(partOne, drivetrain.getBaseDrivetrain(), drivetrain, 2.0, 0.7, 0.3, 0.5, 0.75, true)
     );
  }
}
