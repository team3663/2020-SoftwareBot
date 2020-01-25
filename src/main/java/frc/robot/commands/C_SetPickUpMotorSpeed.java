package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class C_SetPickUpMotorSpeed extends CommandBase {
    private double pickUpSpeed;

    public C_SetPickUpMotorSpeed() {
        addRequirements(Robot.ss_Intake);
    }

    public C_SetPickUpMotorSpeed(double pickUpSpeed) {
        addRequirements(Robot.ss_Intake);
        this.pickUpSpeed = pickUpSpeed;
        Robot.getIntake().setBrakeMode();
    }

    public void execute() {
        Robot.getIntake().setPickupMotorSpeed(pickUpSpeed);
    }
    
    public void end() {
        Robot.getIntake().setPickupMotorSpeed(0.0);
    }

    public void interrupted() {
        end();
    }
}