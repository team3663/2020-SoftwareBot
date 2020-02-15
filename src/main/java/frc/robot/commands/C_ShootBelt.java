/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SS_Feeder;
import frc.robot.subsystems.SS_Shooter;
//import frc.robot.subsystems.SS_Feeder.FeedRate;
//import frc.robot.subsystems.SS_Feeder.State;

public class C_ShootBelt extends CommandBase {
  /**
   * Creates a new C_ShootBelt.
   */
  private SS_Feeder feeder;
  private SS_Shooter shooter;
  private boolean isFinished;
  private final int SHOT_CONFIDENCE_THRESHOLD = 80;
  public C_ShootBelt(SS_Feeder feeder, SS_Shooter shooter, boolean isFinished) {
    this.feeder = feeder;
    this.shooter = shooter;
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //feeder.resetEncoder();
    //feeder.setState(State.SHOOT_ONE);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(shooter.getShotConfidence() >= SHOT_CONFIDENCE_THRESHOLD){
      //feeder.setFeedRate(FeedRate.SHOOT);
    }
    if(shooter.isShotFinished()){
      //feeder.setState(State.SHOOT_CONTINOUS);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
   // feeder.setState(State.IDLE);
    //feeder.setFeedRate(FeedRate.IDLE);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // if((shooter.isShotFinished() && !controller.getAButton().get()) || feeder.getBeltDistance() >= feeder.REV_PER_FULL_FEED){
    //   return true;
    // }else if(shooter.isShotFinished() && controller.getAButton().get()){
    //   feeder.setState(State.SHOOT_CONTINOUS);
    // }
    return false;//(shooter.isShotFinished() && isFinished) || feeder.getBeltDistance() >= feeder.REV_PER_FULL_FEED;
  }
}
