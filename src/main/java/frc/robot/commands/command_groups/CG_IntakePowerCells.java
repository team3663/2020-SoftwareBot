package frc.robot.commands.command_groups;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.C_SetArmPosition;

public class CG_IntakePowerCells extends ParallelCommandGroup {
    public CG_IntakePowerCells() {
        addCommands(new C_SetArmPosition());
    }
}