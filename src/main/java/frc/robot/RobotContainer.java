
package frc.robot;

import frc.robot.commands.C_TurnWheel;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {
    private static SS_ControlPanel ss_ControlPanel = new SS_ControlPanel();
    public final C_TurnWheel c_TurnWheel = new C_TurnWheel(ss_ControlPanel);
    public static Joystick controller = new Joystick(Constants.GAMEPAD_1);

    public RobotContainer(){
        configureButtonBindings();
        /*
        ss_ControlPanel.setDefaultCommand()
        new RunCommand(() -> ss_ControlPanel())
        */
    }

    private void configureButtonBindings(){
        new JoystickButton(controller ,Constants.Button_A)
        .whenPressed(() -> c_TurnWheel.execute())
        .whenReleased(() -> c_TurnWheel.end(true));

        //new Joystick(Constants.GAMEPAD_1)
        //.getRawButtonPressed(Constants.Button_A)-> ss_ControlPanel.setSpeed(0.1);        
    }
}