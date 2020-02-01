package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.C_SetArmPosition;
import frc.robot.commands.C_SetPickUpMotorSpeed;
import frc.robot.commands.commandgroups.CG_Roomba;

import frc.robot.util.IntakePosition;

public class OI {
    public static final int GAMEPAD_1 = 0;

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

    public Joystick driveController = new Joystick(GAMEPAD_1);
    
    public Button a_button = new JoystickButton(driveController, BUTTON_A);
    public Button b_button = new JoystickButton(driveController, BUTTON_B);
    public Button x_button = new JoystickButton(driveController, BUTTON_X);
    public Button y_button = new JoystickButton(driveController, BUTTON_Y);
    public Button r_bumper = new JoystickButton(driveController, R_BUMPER);
    
    private Robot mRobot;

    public OI(Robot robot) {
        mRobot = robot;
    }

    public OI() {
        a_button.whenPressed(new C_SetArmPosition(IntakePosition.EXTENDED));
        b_button.whenPressed(new C_SetArmPosition(IntakePosition.RETRACTED));
        x_button.whenPressed(new C_SetArmPosition(IntakePosition.HALF_RETRACT));
        y_button.whileHeld(new C_SetPickUpMotorSpeed(0.5));
        r_bumper.whileHeld(new CG_Roomba());
    }

    public void registerControls() {
        a_button.whenPressed(new C_SetArmPosition(IntakePosition.EXTENDED));
        b_button.whenPressed(new C_SetArmPosition(IntakePosition.RETRACTED));
        x_button.whenPressed(new C_SetArmPosition(IntakePosition.HALF_RETRACT));
        y_button.whileHeld(new C_SetPickUpMotorSpeed(0.5));
        r_bumper.whileHeld(new CG_Roomba());
    }

    public Robot getRobot() {
        return mRobot;
    }
}