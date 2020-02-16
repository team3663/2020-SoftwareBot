
package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.ClimberConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.C_TurnWheel;
import frc.robot.subsystems.SS_ControlPanel;
import frc.robot.commands.C_TimeRoll;

public class RobotContainer {
    private static SS_ControlPanel ss_ControlPanel = new SS_ControlPanel();
    private final XboxController controller = new XboxController(OIConstants.GAMEPAD_1);

    public RobotContainer() {

        CommandScheduler.getInstance().setDefaultCommand(ss_ControlPanel,new C_TurnWheel(ss_ControlPanel,
            (() -> controller.getY(GenericHID.Hand.kLeft)),
            (() -> controller.getY(GenericHID.Hand.kRight))));

        configureButtonBindings();
        /*
         * ss_ControlPanel.setDefaultCommand() new RunCommand(() -> ss_ControlPanel())
         */
    }
    public XboxController getController2() {
        return controller;
    }

    private void configureButtonBindings() {
        new JoystickButton(controller, OIConstants.A_BUTTON)
        .whenPressed(new C_TimeRoll(ss_ControlPanel, ClimberConstants.TIMED_L_SPEED));
        new JoystickButton(controller, OIConstants.B_BUTTON)
        .whenPressed(new C_TimeRoll(ss_ControlPanel, ClimberConstants.TIMED_R_SPEED));

        /*new JoystickButton(controller ,Constants.A_BUTTON)
        .whenPressed(() -> c_TurnWheel.execute())
        .whenReleased(() -> c_TurnWheel.end(true));
            
        new Joystick(Constants.GAMEPAD_1)
        .getRawButtonPressed(Constants.Button_A)-> ss_ControlPanel.setSpeed(0.1);        
        */
    }
}
