package frc.robot;

import org.frcteam2910.common.robot.input.XboxController;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.CG_Roomba;
import frc.robot.commands.C_SetArmPosition;
import frc.robot.commands.C_SetIntakeSpeed;
import frc.robot.subsystems.SS_Intake;
import frc.robot.util.IntakePosition;

public class RobotContainer {

    //=====INSTANCE VARIABLES=====//
    private SS_Intake ss_Intake;

    private XboxController m_driverController;

    //=====CONSTRUCTOR=====//
    public RobotContainer() {
        ss_Intake = new SS_Intake();
        m_driverController = new XboxController(OIConstants.kDriverControllerPort);
        
        configureButtonBindings();
    }

    //=====SETS THE CONTROLS FOR THE COMMANDS=====//
    private void configureButtonBindings() {
        /*
        This button has two commands: when pressed, it will fully extend the intake arm (position 3),
        and intake power cells. When it is released, it will fully retract the intake arm,
        and stop intaking power cells.
        */
        m_driverController.getAButton()
                .whenPressed(new InstantCommand(() -> new CG_Roomba(true, ss_Intake)));
        m_driverController.getAButton()
                .whenReleased(new InstantCommand(() -> new CG_Roomba(false, ss_Intake)));

        //When pressed, this button sets the intake arm to position 0 (turns off both pistons).
        m_driverController.getBButton()
                .whenPressed(new InstantCommand(() -> new C_SetArmPosition(ss_Intake, IntakePosition.FULLY_RETRACTED)));

        //When pressed, this buttons sets the intake arm to position 2 (turns off the short piston).
        m_driverController.getXButton()
                .whenPressed(new InstantCommand(() -> new C_SetArmPosition(ss_Intake, IntakePosition.SHORT_RETRACT)));

        //When pressed, this button sets the intake arm to position 1 (turns off the long piston).
        m_driverController.getYButton()
                .whenPressed(new InstantCommand(() -> new C_SetArmPosition(ss_Intake, IntakePosition.LONG_RETRACT)));

        //While held, this button turns the wheels on the intake arm to spit out power cells.
        m_driverController.getBackButton()
                .whileHeld(new InstantCommand(() -> new C_SetIntakeSpeed(ss_Intake, IntakeConstants.OUTTAKE_SPEED)));

        //While held, this button turns the wheels on the intake arm to intake power cells.
        m_driverController.getStartButton()
                .whileHeld(new InstantCommand(() -> new C_SetIntakeSpeed(ss_Intake, IntakeConstants.INTAKE_SPEED)));
    }

    //=====RETURNS THE XBOX CONTROLLER=====//
    public XboxController getDriveController() {
        return m_driverController;
    }
}