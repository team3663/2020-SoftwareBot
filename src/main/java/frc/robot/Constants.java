/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

public class Constants {
    //=====DRIVE CONSTANTS=====//
    public static final class DriveConstants {
        public static final int intakeArmSolenoidModule = 1;
        public static final int[] intakeArmSolenoidPorts = new int[]{3, 2, 1, 0};
        
        public static final int powerCellPickUpMotor = 10;
    }

    //=====OI CONSTANTS=====//
    public static final class OIConstants {
        public static final int kDriverControllerPort = 0;
        
        public static final int L_STICK_X_AXIS = 0;
        public static final int L_STICK_Y_AXIS = 1;
        public static final int L_TRIGGER_AXIS = 2;
        public static final int R_TRIGGER_AXIS = 3;
        public static final int R_STICK_X_AXIS = 4;
        public static final int R_STICK_Y_AXIS = 5;

        public static final int BUTTON_A = 1;
        public static final int BUTTON_B = 2;
        public static final int BUTTON_X = 3;
        public static final int BUTTON_Y = 4;
        public static final int L_BUMPER = 5;
        public static final int R_BUMPER = 6;
        public static final int BUTTON_BACK = 7;
        public static final int BUTTON_START = 8;
        public static final int L_JOYCLICK = 9;
        public static final int R_JOYCLICK = 10;
    }

    //=====INTAKE ARM CONSTANTS=====//
    public static final class IntakeConstants {
        public static final double OUTTAKE_SPEED = -0.5;
        public static final double INTAKE_SPEED = 0.5;

        public static final int SWITCH_ID = 0;
    }
}
