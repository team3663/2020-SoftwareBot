package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.commands.C_SetArmPosition;

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
    
    private Robot mRobot;

    public OI(Robot robot) {
        mRobot = robot;
    }

    public void registerControls() {
        a_button.whenPressed(new C_SetArmPosition(IntakePosition.FULLY_EXTENDED));
        b_button.whenPressed(new C_SetArmPosition(IntakePosition.FULLY_RETRACTED));
        x_button.whenPressed(new C_SetArmPosition(IntakePosition.SHORT_RETRACT));
        y_button.whenPressed(new C_SetArmPosition(IntakePosition.LONG_RETRACT));
    }

    public Robot getRobot() {
        return mRobot;
    }
}