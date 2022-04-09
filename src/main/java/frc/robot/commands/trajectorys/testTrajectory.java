// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.trajectorys;

import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.Trajectory.State;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.BreakerLib.auto.trajectory.diff.BreakerRamsete;
import frc.robot.BreakerLib.subsystemcores.drivetrain.differential.BreakerDiffDrive;
import frc.robot.subsystems.Drive;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class testTrajectory extends SequentialCommandGroup {
  /** Creates a new testTrajectory. */
  Trajectory partOne;
  public testTrajectory(Drive drivetrain) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    partOne = TrajectoryGenerator.generateTrajectory(start, interiorWaypoints, end, config);
    addCommands(
      new BreakerRamsete(partOne, drivetrain.getBaseDrivetrain(), drivetrain, ramseteB, ramseteZeta, maxVel, maxAccel, maxVoltage)
    );
  }
}
