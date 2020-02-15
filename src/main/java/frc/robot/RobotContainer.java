package frc.robot;

//import edu.wpi.first.wpilibj.XboxController;

import org.frcteam2910.common.robot.input.XboxController;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.CG_Roomba;
import frc.robot.commands.C_IntakeIn;
import frc.robot.commands.C_IntakeOut;
import frc.robot.commands.C_SetArmPosition;
import frc.robot.subsystems.SS_Intake;
import frc.robot.util.IntakePosition;

public class RobotContainer {
    private SS_Intake ss_Intake = new SS_Intake();

    private XboxController m_driverController = new XboxController(OIConstants.kDriverControllerPort);

    public RobotContainer() {
        configureButtonBindings();
    }

    private void configureButtonBindings() {
        m_driverController.getAButton()
                .whenPressed(new InstantCommand(() -> new CG_Roomba(true, ss_Intake)));
        m_driverController.getAButton()
                .whenReleased(new InstantCommand(() -> new CG_Roomba(false, ss_Intake)));

        m_driverController.getBButton()
                .whenPressed(new InstantCommand(() -> new C_SetArmPosition(ss_Intake, IntakePosition.FULLY_RETRACTED)));

        m_driverController.getXButton()
                .whenPressed(new InstantCommand(() -> new C_SetArmPosition(ss_Intake, IntakePosition.SHORT_RETRACT)));

        m_driverController.getYButton()
                .whenPressed(new InstantCommand(() -> new C_SetArmPosition(ss_Intake, IntakePosition.LONG_RETRACT)));

        m_driverController.getBackButton()
                .whileHeld(new InstantCommand(() -> new C_IntakeOut(ss_Intake)));

        m_driverController.getStartButton()
                .whileHeld(new InstantCommand(() -> new C_IntakeIn(ss_Intake)));
    }

    public XboxController getDriveController() {
        return m_driverController;
    }
}