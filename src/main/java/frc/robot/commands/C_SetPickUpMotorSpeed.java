package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class C_SetPickUpMotorSpeed extends Command {
    private double pickUpSpeed;

    public C_SetPickUpMotorSpeed() {
        requires(Robot.getIntake());
    }

    public C_SetPickUpMotorSpeed(double pickUpSpeed) {
        requires(Robot.getIntake());
        this.pickUpSpeed = pickUpSpeed;
        Robot.getIntake().setBrakeMode();
    }

    @Override
    protected void execute() {
        Robot.getIntake().setPickupMotorSpeed(pickUpSpeed);
    }
    
    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
        Robot.getIntake().setPickupMotorSpeed(0.0);
    }

    @Override
    protected void interrupted() {
        end();
    }
}