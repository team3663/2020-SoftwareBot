
package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class RobotContainer {
    public static final int GAMEPAD_1 = 0;
    public static Joystick controller = new Joystick(GAMEPAD_1);

    //private final SS_ControlPanel ss_ControlPanel = new SS_ControlPanel();
    //public final C_TurnWheel c_TurnWheel = new C_TurnWheel();

    public RobotContainer(){

        configureButtonBindings();
        /*
        ss_ControlPanel.setDefaultCommand(
        new RunCommand(() -> ss_ControlPanel   
        ))
        )
        */
    }

    private void configureButtonBindings(){
        /*
        new JoystickButton(ss_ControlPanel, Button.R_STICK_Y_AXIS)
        .whenPressed(() -> ss_ControlPanel.setMaxOutput(0.5))
        .whenReleased(() -> ss_ControlPanel.setMaxOutput(1));
        */
    }
}