/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SS_Shooter;
import frc.robot.Robot;
import frc.robot.drivers.Vision;
import frc.robot.subsystems.SS_Shooter;


/**
 * @author Jesse Hoffman or PreacherDHM
 * 
 * @see See Jesse if you have eny queshtions.
 * 
 * @since
 * C_Shoot gets the range of the target and ajust the FPS (Feet Per Second)
 * acording to the distance of the target.
 * If the arc is too low to shoot into the target it will go to the second firing posion.
 * 
 */
public class C_Shoot extends CommandBase {
/**
 * Creates a new C_Shoot.
 */
  private static final double maxShootRange = 100; //sets the max shooter FPS
  private static final double heatUpRange = 20;
  private static final double ShooterStopBlockTime = 500;

  private Joystick joystick;
  
  private Vision vision;
  private SS_Shooter shooter;
  private Timer timer;

  private double gitTargetDistance = 0;

  public C_Shoot(Vision vision, SS_Shooter shooter) {
    joystick = new Joystick(0);
    this.shooter = shooter;
    this.vision = vision;
    addRequirements(shooter);

    timer = new Timer();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    heatUpShooter();
    shooterRange();
    vision.updateTelemetry();
    //fire();

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(final boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }

  public void fire(){
    
    shooter.shoot();

  }

  private void heatUpShooter(){

    if(gitTargetDistance <= heatUpRange || joystick.getRawButton(1)){
      shooter.startSpinning();
    }else{
      shooter.stopSpinning();
    }

  }

  public void shooterRange(){
    
    if(vision.getValidTarget()) {//|| joystick.getRawButtonPressed(1)){
      gitTargetDistance = vision.getDistance();
      //timer.stop();
      //timer.reset();
    }//else{                                          

      //timer.start();

    //}

    // if(timer.get() > ShooterStopBlockTime){
    //   shooter.stopSpinning();
    // }

    if(vision.getDistance() <= maxShootRange){

      shooter.setTargetDistance(gitTargetDistance);

    }else{

      shooter.setTargetDistance(maxShootRange);
    }
  }
}