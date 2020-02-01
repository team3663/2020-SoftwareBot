package frc.robot.commands.commandgroups;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.C_SetArmPosition;
import frc.robot.commands.C_SetPickUpMotorSpeed;
import frc.robot.util.IntakePosition;

public class CG_Roomba extends ParallelCommandGroup {
    public CG_Roomba() {
        addCommands(
            new C_SetArmPosition(IntakePosition.FULLY_EXTENDED),
            new C_SetPickUpMotorSpeed()
        );
    }
}