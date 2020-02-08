/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;


//===================================IMPORTS=======================================//
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SS_Shooter;
import frc.robot.drivers.Vision;
//=================================================================================//

//=================================================================================//
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
//=================================================================================//
public class C_Shoot extends CommandBase {

//=================================================================================//

  //CONSTANCE
  private final double MAX_SHOOT_RANGE = 35;                // max shooting range in feet
  private final double SHOOTER_STOP_BLOCK_TIME = 500;       // in seconds
  private final double SHOOTER_STOP_OUT_OF_RANGE_TIME = 15; // in seconds
  //DRIVERS
  private Vision vision;
  //JOYSTICKS
  private Joystick joystick;
  //SUBSYSTEM
  private SS_Shooter shooter;
  //TIMERS
  private Timer bockedTimer;  //timerBlocked is the timer that starts when we are blocked or cant see the target.
  private Timer outOfRangeTimer;  //outOfRangeTimer is the timer that starts when the robot is out of rage of the target.
  //PRIVATE VAREBLES
  private double getTargetDistance = 0;

//=================================================================================//



//===============================CONSTRUCTOR=======================================//
  public C_Shoot(Vision vision, SS_Shooter shooter) {
    //SETTER
    this.shooter = shooter;
    this.vision = vision;
    //CREATING NEW INSTNACES
    joystick = new Joystick(0);
    bockedTimer = new Timer();
    outOfRangeTimer = new Timer();
    //REQUARMENTS
    addRequirements(shooter);
  }
//=================================================================================//



//==================================INITIALIZE=====================================//
  @Override
  public void initialize() {
  }
//=================================================================================//



//==================================EXECUTE========================================//
  @Override
  public void execute() {

    preheatShooter();
    shooterHasTarget();
    vision.updateTelemetry();
    

  }
//=================================================================================//



//====================================END==========================================//
  @Override
  public void end(final boolean interrupted) {
  }
//=================================================================================//



//================================IS=FINISHED=======================================//
  @Override
  public boolean isFinished() {
    return false;
  }
//=================================================================================//


//==================================FIRE===========================================//
//          IF THE A BUTTON IS PREST THEN IT WILL RUN THE SHOOT METHED
  public void fire(){
    if(joystick.getRawButton(4)){
      shooter.shoot();
    }
  }
//=================================================================================//


   
//=============================PREHEAT=SHOOTER=====================================//
//PREHEAT-SHOOTER SEES IF THE BLCOK-TIMER OR THE OUT OF RANGE TIMER IS OUT OF BOUNDS
  private void preheatShooter(){

    if(bockedTimer.get() > SHOOTER_STOP_BLOCK_TIME || outOfRangeTimer.get() > SHOOTER_STOP_OUT_OF_RANGE_TIME){

      shooter.stopSpinning();

    }else{

      shooter.stopSpinning();

    }

  }
//=================================================================================//



//=============================SHOOTER=OUT=OFRNAGE=================================//
//IF THE CURENT DISTANCE IS GRATOR THEN THE MAX_SHOOT_RANGE IT WILL START THE 
//                            OUT-OF-RANGE-TIMER

  public void isShooterOutOfRange(){

    if(vision.getDistance() < MAX_SHOOT_RANGE){

      getTargetDistance = vision.getDistance();
      outOfRangeTimer.stop();
      outOfRangeTimer.reset();

    }else{

      outOfRangeTimer.start();

    }
  }
//=================================================================================//

//=============================SHOOTER=HAS=TARGET==================================//
//    IF THERE IS A VALED TARGET IT WILL SET THE DISTACN ELSE IT WILL START THE
//                                 BOCKED-TIMER 

  public void shooterHasTarget(){
    
    if(vision.getValidTarget()) {

      getTargetDistance = vision.getDistance();
      bockedTimer.stop();
      bockedTimer.reset();

    }else{                                          

      bockedTimer.start();

    }
  }
//=================================================================================//
}