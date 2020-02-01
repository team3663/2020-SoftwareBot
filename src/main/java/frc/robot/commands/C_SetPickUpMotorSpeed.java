package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class C_SetPickUpMotorSpeed extends CommandBase {
    private double pickUpSpeed = 0.5;

    public C_SetPickUpMotorSpeed() {
        addRequirements(Robot.ss_Intake);
    }

    //@Override
    public void execute() {
        Robot.getIntake().setPickupMotorSpeed(pickUpSpeed);
    }
    
    //@Override
    public boolean isFinished() {
        return false;
    }

    //@Override
    public void end() {
        Robot.getIntake().setPickupMotorSpeed(0.0);
    }

    //@Override
    public void interrupted() {
        end();
    }
}