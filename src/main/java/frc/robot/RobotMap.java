/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

public class RobotMap 
{
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
    
}
