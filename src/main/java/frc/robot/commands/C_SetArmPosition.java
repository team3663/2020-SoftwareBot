package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_SetArmPosition extends Command {
    private boolean extended;

    public C_SetArmPosition() {
        requires(Robot.getIntake());
    }

    public C_SetArmPosition(boolean extended) {
        requires(Robot.ss_Intake);
        this.extended = extended;
    }

    @Override
    protected void execute() {
        Robot.getIntake().setArmPosition(extended);
    }
    
    @Override
    protected boolean isFinished() {
        return true;
    }
}