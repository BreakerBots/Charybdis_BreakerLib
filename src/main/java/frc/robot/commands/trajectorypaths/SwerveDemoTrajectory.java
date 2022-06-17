// // Copyright (c) FIRST and other WPILib contributors.
// // Open Source Software; you can modify and/or share it under the terms of
// // the WPILib BSD license file in the root directory of this project.

// package frc.robot.commands.trajectorypaths;

// import edu.wpi.first.math.geometry.Pose2d;
// import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import frc.robot.BreakerLib.auto.trajectory.management.BreakerStartTrajectoryPath;
// import frc.robot.BreakerLib.auto.trajectory.swerve.BreakerFollowSwerveTrajectory;
// import frc.robot.BreakerLib.auto.trajectory.swerve.BreakerFollowSwerveTrajectoryConfig;
// import frc.robot.subsystems.Drive;

// // NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// // information, see:
// // https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
// public class SwerveDemoTrajectory extends SequentialCommandGroup {
//   /** Creates a new SwerveDemoTrajectory. */
//   public SwerveDemoTrajectory(Drive drivetrain) {
//     // Add your commands in the addCommands() call, e.g.
//     // addCommands(new FooCommand(), new BarCommand());
//     addCommands(
//       new BreakerStartTrajectoryPath(drivetrain.getBaseDrivetrain(), new Pose2d()),
//       new BreakerFollowSwerveTrajectory(new BreakerFollowSwerveTrajectoryConfig(null, null, null, m_currentCommandIndex, m_currentCommandIndex, m_currentCommandIndex, m_currentCommandIndex, m_currentCommandIndex), true, drivetrain, )
//     );
//   }
// }
