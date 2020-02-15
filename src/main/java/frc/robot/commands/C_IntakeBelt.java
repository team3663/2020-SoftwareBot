/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SS_Feeder;
import frc.robot.subsystems.SS_Feeder.FeedRate;
import frc.robot.subsystems.SS_Feeder.State;

public class C_IntakeBelt extends CommandBase {
  /**
   * Creates a new C_Intake.
   */
  private SS_Feeder feeder;
  public C_IntakeBelt(SS_Feeder feeder) {
    this.feeder = feeder;
    addRequirements(feeder);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    feeder.setState(State.INTAKE);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(feeder.entryIsValidTarget() && !feeder.exitIsValidTarget()){
      feeder.setFeedRate(FeedRate.LOAD);
    }else{
      feeder.setFeedRate(FeedRate.IDLE);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    feeder.setFeedRate(FeedRate.IDLE);
    feeder.setState(State.IDLE);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return feeder.exitIsValidTarget();
  }
}
