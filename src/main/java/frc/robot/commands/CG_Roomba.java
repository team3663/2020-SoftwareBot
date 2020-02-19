package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Constants.IntakeConstants;
import frc.robot.commands.C_SetArmPosition;
import frc.robot.subsystems.SS_Intake;
import frc.robot.util.IntakePosition;

public class CG_Roomba extends ParallelCommandGroup {

    //=====INSTANCE VARIABLES=====//
    private SS_Intake ss_Intake;

    //=====CONSTRUCTOR=====//
    public CG_Roomba(boolean extended, SS_Intake intake) {
        this.ss_Intake = intake;
        
        /*
        If extended is true, this command will extend the intake arm, and spin the pickup motor to intake 
        power cells. Otherwise, it will fully retract the intake arm, and stop the pickup motor.
        */
        if(extended) {
            addCommands(
                new C_SetArmPosition(ss_Intake, IntakePosition.FULLY_EXTENDED),
                new C_SetIntakeSpeed(ss_Intake, IntakeConstants.INTAKE_SPEED)
            );
        } else {
            addCommands(
                new C_SetArmPosition(ss_Intake, IntakePosition.FULLY_RETRACTED),
                new C_SetIntakeSpeed(ss_Intake, 0.0)
            );
        }
    }
}