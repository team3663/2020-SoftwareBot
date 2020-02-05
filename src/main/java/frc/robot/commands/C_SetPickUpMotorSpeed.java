package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.Robot;

public class C_SetPickUpMotorSpeed extends CommandBase {
    private double pickUpSpeed = 0;

    public C_SetPickUpMotorSpeed() {
        addRequirements(Robot.ss_Intake);
    }

    //@Override
    public void execute() {
        if(Robot.oi.driveController.getRawButton(OI.L_BUMPER)) {
            pickUpSpeed = 0.5;
        }
        else if(Robot.oi.driveController.getRawButton(OI.R_BUMPER)) {
            pickUpSpeed = -0.5;
        }
        else if(Robot.oi.driveController.getRawButton(OI.BUTTON_START)) {
            pickUpSpeed = 0;
        }

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