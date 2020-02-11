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
import frc.robot.subsystems.SS_Feeder.FeedRate;
import frc.robot.subsystems.SS_Feeder.State;

public class C_ShootPrep extends CommandBase {
  
  private SS_Shooter shooter;
  private SS_Feeder feeder;
  private double initialDistance = 0; //initial distance of the feeder belt

  public C_ShootPrep(SS_Shooter shooter, SS_Feeder feeder) {
    this.shooter = shooter;
    this.feeder = feeder;
    initialDistance = feeder.getBeltDistance();
    addRequirements(shooter, feeder);
  }

  @Override
  public void initialize() { 
    shooter.setSpinning(true); 
    feeder.setState(State.SHOOT_PREP); 
  }

  @Override
  public void end(boolean interrupted) {
    feeder.setRPM(FeedRate.IDLE);
  }

  @Override
  public boolean isFinished() {
    //check if there is a ball at the end of the feeder ready to go into the shooter or if the belt has already travelled far
    //enough to move a ball al the way across the belt (meaning no balls are on the belt)
    return feeder.exitIsValidTarget() || feeder.getBeltDistance() - initialDistance >= feeder.REV_PER_FULL_FEED;
  }
}
