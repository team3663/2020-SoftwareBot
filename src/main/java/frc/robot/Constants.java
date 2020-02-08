/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * All constants for robot control are found here.
 */
public class Constants {
    public static final int DRIVE_CONTROLLER_ID = 0;

        //shooter constants
        //Ports
        public static final int FLY_WEEL_MOTOR = 9;
        public static final int BALL_FEED_MOTOR = 1; //TODO
        public static final int SHOOTER_ANGLE_SOLENOID = 2; //TODO
            //Positions of solenoid for near and far shooting angles
        public static final DoubleSolenoid.Value SHOOTER_NEAR_ANGLE = DoubleSolenoid.Value.kForward; //TODO
        public static final DoubleSolenoid.Value SHOOTER_FAR_ANGLE = DoubleSolenoid.Value.kReverse; //TODO
    
        //Intake constants
        public static final int INTAKE_WHEEL_MOTOR = 3; //TODO
        public static final int INTAKE_SOLENOID_FORWARD = 4; //TODO
        public static final int INTAKE_SOLENOID_REVERSE = 5; //TODO
    
        //feeder constants
        public static final int FEEDER_BELT_MOTOR = 6; //TODO
}
