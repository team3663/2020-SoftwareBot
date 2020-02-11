/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SS_Feeder;
import frc.robot.subsystems.SS_Shooter;
import frc.robot.subsystems.SS_Feeder.FeedRate;
import frc.robot.subsystems.SS_Feeder.State;

public class C_ShootBelt extends CommandBase {
  /**
   * Creates a new C_ShootBelt.
   */
  private SS_Feeder feeder;
  private SS_Shooter shooter;
  private Joystick joystick;
  private final int SHOT_CONFIDENCT_THRESHOLD = 80;
  public C_ShootBelt(SS_Feeder feeder, SS_Shooter shooter, Joystick joystick) {
    this.feeder = feeder;
    this.shooter = shooter;
    this.joystick = joystick;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    feeder.resetEncoder();
    feeder.setState(State.SHOOT_ONE);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(shooter.getShotConfidence() >= SHOT_CONFIDENCT_THRESHOLD){
      feeder.setRPM(FeedRate.SHOOT);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    feeder.setState(State.IDLE);
    feeder.setRPM(FeedRate.IDLE);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if((shooter.isShotFinished() && !joystick.getRawButton(1)) || feeder.getBeltDistance() >= feeder.REV_PER_FULL_FEED){
      return true;
    }else if(shooter.isShotFinished() && joystick.getRawButton(1)){
      feeder.setState(State.SHOOT_CONTINOUS);
    }
    return false;
  }
}
