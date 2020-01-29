package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class C_Roll extends CommandBase {
  private double speed = 0;

  public C_Roll() {
    addRequirements(Robot.ss_Climber);
  }

  //@Override
  public void execute() {
    Robot.ss_Climber.setRollLeft(speed);
    Robot.ss_Climber.setRollRight(speed);
  }

  //@Override
  public boolean isFinished() {
    return true;
  }

  //@Override
  public void end() {
  }

  //@Override
  public void interrupted() {
  }
}
