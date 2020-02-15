package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.C_SetArmPosition;
import frc.robot.subsystems.SS_Intake;
import frc.robot.util.IntakePosition;
import frc.robot.commands.C_IntakeIn;

public class CG_Roomba extends ParallelCommandGroup {
    private SS_Intake ss_Intake;

    public CG_Roomba(boolean extend, SS_Intake intake) {
        this.ss_Intake = intake;
        if(extend) {
            addCommands(
                new C_SetArmPosition(ss_Intake, IntakePosition.FULLY_EXTENDED),
                new C_IntakeIn(ss_Intake)
            );
        } else {
            addCommands(
                new C_SetArmPosition(ss_Intake, IntakePosition.FULLY_RETRACTED),
                new InstantCommand(() -> ss_Intake.setPickupMotorSpeed(0.0))
            );
        }
    }
}