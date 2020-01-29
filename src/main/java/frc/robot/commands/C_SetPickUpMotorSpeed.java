package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class C_SetPickUpMotorSpeed extends CommandBase {
    private double pickUpSpeed;

    public C_SetPickUpMotorSpeed() {
        addRequirements(Robot.getIntake());
    }

    public C_SetPickUpMotorSpeed(double pickUpSpeed) {
        addRequirements(Robot.getIntake());
        this.pickUpSpeed = pickUpSpeed;
        Robot.getIntake().setBrakeMode();
    }

    //@Override
    public void execute() {
        Robot.getIntake().setPickupMotorSpeed(pickUpSpeed);
    }
    
    //@Override
    /*public boolean isFinished() {
        return true;
    }*/

    //@Override
    public void end() {
        Robot.getIntake().setPickupMotorSpeed(0.0);
    }

    //@Override
    public void interrupted() {
        end();
    }
}