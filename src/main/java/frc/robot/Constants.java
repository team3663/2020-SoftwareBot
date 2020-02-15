/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
<<<<<<< Updated upstream
public class Constants {
    //ROBOT DIMENSIONS
    public static final double TRACKWIDTH = 23.5;
    public static final double WHEELBASE = 23.63;

    //SWERVE CAN IDs
    public static final int FRONT_LEFT_DRIVE_MOTOR = 1;
    public static final int FRONT_RIGHT_DRIVE_MOTOR = 2;
    public static final int BACK_LEFT_DRIVE_MOTOR = 3;
    public static final int BACK_RIGHT_DRIVE_MOTOR = 4;

    public static final int FRONT_LEFT_ANGLE_MOTOR = 5;
    public static final int FRONT_RIGHT_ANGLE_MOTOR = 6;
    public static final int BACK_LEFT_ANGLE_MOTOR = 7;
    public static final int BACK_RIGHT_ANGLE_MOTOR = 8;

    //SWERVE AMPERAGE LIMITS
    public static final int DRIVE_MOTOR_AMP_LIMIT = 25;

    //CONTROLLERS
    public static final int DRIVE_CONTROLLER_ID = 0;

    //MISC
    static int joe = 200;
    public static final byte NAVX_UPDATE_RATE = (byte) joe;
=======
public final class Constants {
    public static final class OIConstants {
        public static final int DRIVER_CONTROLLER_ID = 0;
        public static final int L_STICK_X = 0;
        public static final int L_STICK_Y = 1;
        public static final int R_STICK_X = 4;
        public static final int R_STICK_Y = 5;   
        public static final int L_BUMPER = 5;
        public static final int R_BUMPER = 6;
      }
  
      public static final class DriveConstants {
        // for VictorSPX motor controllers
        public static final int L_VICTOR_SPX_ID = 12;
        public static final int R_VICTOR_SPX_ID = 10;

        // for TalonSRX motor controllers
        public static final int L_TALON_SRX_ID = 0;
        public static final int R_TALON_SRX_ID = 1;

        // for SparkMax motor controllers
        public static final int GONDOLA_SPARK_MAX_ID = 20;
        public static final int WENCH_SPARK_MAX_ID = 21;

      }
>>>>>>> Stashed changes
}

