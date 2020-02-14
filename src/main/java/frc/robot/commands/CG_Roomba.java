package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.C_SetArmPosition;
import frc.robot.subsystems.SS_Intake;
import frc.robot.util.IntakePosition;
import frc.robot.commands.C_IntakeIn;

public class CG_Roomba extends ParallelCommandGroup {
    private final SS_Intake ss_Intake = new SS_Intake();

    public CG_Roomba() {
        addCommands(
            new C_SetArmPosition(ss_Intake, IntakePosition.FULLY_EXTENDED),
            new C_IntakeIn(ss_Intake)
        );
    }
}