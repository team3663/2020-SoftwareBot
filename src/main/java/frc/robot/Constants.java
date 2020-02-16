package frc.robot;

public final class Constants{
    //public static final int kMyMotorId = 1; //another way to name it
    public static final class OIConstants{
        public static final int MY_MOTOR_ID = 1;
        public static final int L_STICK_Y_AXIS = 1;
        public static final int R_STICK_Y_AXIS = 5;
        public static final int GAMEPAD_1 = 0;
        public static final int A_BUTTON = 1;
        public static final int B_BUTTON = 2;

    }
    public static final class DriveConstants {
        // for VictorSPX motor controllers
        public static final int L_VICTOR_SPX_ID = 12;
        public static final int R_VICTOR_SPX_ID = 10;

        // for TalonSRX motor controllers
        public static final int L_TALON_SRX_ID = 0;
        public static final int R_TALON_SRX_ID = 1;

        // for SparkMax motor controllers
        public static final int SPARK_MAX_ID = 20;
      }


    public static final class ClimberConstants {
        public static final double ROLL_L_SPEED = -0.05;
        public static final double ROLL_R_SPEED = 0.05;
        
        public static final double TIMED_L_SPEED = -0.02;
        public static final double TIMED_R_SPEED = 0.02;
        public static final long TIMED_DURATION = 1000;   // unit is ms (millisecond)
        
      }
}