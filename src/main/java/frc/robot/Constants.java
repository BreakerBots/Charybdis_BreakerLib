// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    
    /** Talon FX integrated encoder ticks */
        public static final double TALON_FX_TICKS = 2048;
    
    // Drive Motor IDs
        public static final int L1_ID = 11;
        public static final int L2_ID = 12;
        public static final int L3_ID = 13;
        public static final int R1_ID = 14;
        public static final int R2_ID = 15;
        public static final int R3_ID = 16;

    // Misic drive constants
        /** Gear ratio for drivetrain (rotation of motors to rotation of wheel) (assume (value) to 1) */
        public static final double DRIVE_GEAR_RATIO = 8.49; 
        /** Diameter of drivetrain's Colson wheels, in inches */
        public static final double DRIVE_COLSON_DIAMETER = 3.83;
        public static final double DRIVE_SLOW_MODE_FWD_MULTIPLIER = 0.5;
        public static final double DRIVE_SLOW_MODE_TURN_MULTIPLIER = 0.5;

    // Intake constants
        /** CAN ID of the left indexer motor */
        public static int LEFT_INDEXER_ID = 22;
        /** CAN ID of the right indexer motor */
        public static int RIGHT_INDEXER_ID = 23;
        /** CAN ID of the intake arm's motor */
        public static int PRIM_INTAKE_ID = 21;
        public static double LEFT_INDEXER_SPEED = 0.8;
        public static double RIGHT_INDEXER_SPEED = 0.8;
        public static double PRIM_INTAKE_SPEED = 1.0;

    // Pnumatics Constants
        /** CAN ID of the pnumatics controle module */
        public static int PCM_ID = 5;
        public static int INTAKESOL_FWD = 2;
        public static int INTAKESOL_REV = 0;



}
