package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class C_SetArmPosition extends CommandBase {
    private boolean extended;

    public C_SetArmPosition() {
        addRequirements(Robot.getIntake());
    }

    public C_SetArmPosition(boolean extended) {
        addRequirements(Robot.ss_Intake);
        this.extended = extended;
    }

    //@Override
    public void execute() {
        Robot.getIntake().setArmPosition(extended);
    }
    
    //@Override
    public boolean isFinished() {
        return true;
    }
}