package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.util.IntakePosition;

public class C_SetArmPosition extends CommandBase {
    private IntakePosition position;

    public C_SetArmPosition() {
        addRequirements(Robot.ss_Intake);
    }

    public C_SetArmPosition(IntakePosition position) {
        addRequirements(Robot.ss_Intake);
        this.position = position;
    }

    //@Override
    public void execute() {
        Robot.getIntake().setArmPosition(position);
    }
    
    //@Override
    public boolean isFinished() {
        return true;
    }
}