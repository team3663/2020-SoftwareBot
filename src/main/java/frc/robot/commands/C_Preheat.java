/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.drivers.Vision;
import frc.robot.subsystems.SS_Shooter;


public class C_Preheat extends CommandBase {
  /**
   * Creates a new C_Preheat.
   */
  private Vision vision;
  private SS_Shooter shooter;

  private final double MAX_SHOOTING_RANGE = 30;
  private final double MAX_OUT_OF_RANGE_TIME = 15;
  private final double MAX_BLOCKED_TIME = 15;

  private Timer blockedTimer;
  private Timer outOfRangeTimer;

  public C_Preheat(SS_Shooter shooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.shooter = shooter;
    this.vision = new Vision();
    this.blockedTimer = new Timer();
    this.outOfRangeTimer = new Timer();
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    UpdatePreheat();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  /**
   * @return Returs if the target is in range.
   */
  public boolean Range(){
    return vision.getDistance() < MAX_SHOOTING_RANGE;
  }
  /**
   * @return Returns if the camera is blocked.
   */
  public boolean blocked(){
    return false;
  }

/**
 * @param UpdatePreheat this updates the shooter if we are being blocded and if we are in range.
 */
  public void UpdatePreheat(){
    if(Range()){
      if(!blocked()){
        shooter.setSpinning(true);
        blockedTimer.stop();
        blockedTimer.reset();
      }else{
        isBlocked();
      }
      outOfRangeTimer.stop();
      outOfRangeTimer.reset();
    }else{
      notInRange();
    }
    
  }

  /**
   * @param notInRange if the outOfRangeTimer is higher then the MAX_OUT_OF_RANGE_TIME then go into lob speed.
   */
  public void notInRange(){
    outOfRangeTimer.start();
    if(outOfRangeTimer.get() > MAX_OUT_OF_RANGE_TIME){
      shooter.setSpinning(true,0);
    }
    
  }

  /**
   * @param isBlocked if we are blocked of a certen timer it will go into lob speed
   */
  public void isBlocked(){
    blockedTimer.start();
    if(blockedTimer.get() > MAX_BLOCKED_TIME){
      shooter.setSpinning(true,0);
    }
  }

}
