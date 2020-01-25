package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class C_SetArmPosition extends CommandBase {
    private double pickUpSpeed;
    private boolean extended;

    public C_SetArmPosition() {
        addRequirements(Robot.ss_Intake);
    }

    public C_SetArmPosition(boolean extended) {
        addRequirements(Robot.ss_Intake);
        this.extended = extended;
    }

    public C_SetArmPosition(double pickUpSpeed) {
        addRequirements(Robot.ss_Intake);
        this.pickUpSpeed = pickUpSpeed;
        Robot.getIntake().setBrakeMode();
    }

    public void execute() {
        Robot.getIntake().setArmPosition(extended);
        Robot.getIntake().setPickupMotorSpeed(pickUpSpeed);
    }
    
    public boolean isFinished() {
        return true;
    }
}