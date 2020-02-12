package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.C_SetArmPosition;
import frc.robot.subsystems.SS_Intake;
import frc.robot.util.IntakePosition;

public class RobotContainer {
    private final SS_Intake ss_Intake = new SS_Intake();
    private IntakePosition position;

    private XboxController m_driverController = new XboxController(OIConstants.kDriverControllerPort);

    public RobotContainer() {
        CommandScheduler.getInstance().setDefaultCommand(ss_Intake, new C_SetArmPosition(ss_Intake, position));
        configureButtonBindings();
    }

    private void configureButtonBindings() {
        new JoystickButton(m_driverController, OIConstants.BUTTON_A)
            .whenPressed(new C_SetArmPosition(ss_Intake, IntakePosition.FULLY_EXTENDED));
        new JoystickButton(m_driverController, OIConstants.BUTTON_B)
            .whenPressed(new C_SetArmPosition(ss_Intake, IntakePosition.FULLY_RETRACTED));
        new JoystickButton(m_driverController, OIConstants.BUTTON_X)
            .whenPressed(new C_SetArmPosition(ss_Intake, IntakePosition.SHORT_RETRACT));
        new JoystickButton(m_driverController, OIConstants.BUTTON_Y)
            .whenPressed(new C_SetArmPosition(ss_Intake, IntakePosition.LONG_RETRACT));
    }

    public XboxController getDriveController() {
        return m_driverController;
    }
}