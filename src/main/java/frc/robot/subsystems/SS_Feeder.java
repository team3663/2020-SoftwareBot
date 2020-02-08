/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.playingwithfusion.TimeOfFlight;
import com.playingwithfusion.TimeOfFlight.RangingMode;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class SS_Feeder extends SubsystemBase {

  private final double FEEDER_BELT_GEAR_RATIO_MULTIPLIER = 1;

  //Feeder PID constants
  private final double KP = 0.0001;
  private final double KI = 0.0000005;
  private final double KD = 0;

  public final int FEEDER_SHOOT_RPM = 100; //how fast the feeder should be running when we are shooting
  public final int FEEDER_LOAD_RPM = 100; //how fast the feeder should be running when indexing the balls

  public final int REV_PER_FULL_FEED = 1500; //amount of revolutions before the feeder fully indexes all balls

  private final double ENTRY_VALID_RANGE = 300;
  private final double EXIT_VALID_RANGE = 300;

  private CANEncoder feederBeltEncoder;

  private CANSparkMax belt;
  private CANPIDController beltPID;

  private TimeOfFlight entrySensor;
  private TimeOfFlight exitSensor;

  private double targetRPM = 0;
  private State state = State.IDLE;


  public SS_Feeder() {
    belt = new CANSparkMax(Constants.FEEDER_BELT_MOTOR, MotorType.kBrushless);
    belt.setIdleMode(IdleMode.kBrake);
    beltPID = belt.getPIDController();
    beltPID.setP(KP);
    beltPID.setI(KI);
    beltPID.setD(KD);
    feederBeltEncoder = belt.getEncoder();
    feederBeltEncoder.setVelocityConversionFactor(FEEDER_BELT_GEAR_RATIO_MULTIPLIER); //set feeder gear ratio

    //Sensors for Feeder
    entrySensor = new TimeOfFlight(Constants.ENTRY_SENSOR);
    exitSensor = new TimeOfFlight(Constants.EXIT_SENSOR);
    entrySensor.setRangingMode(RangingMode.Short, 100);
    exitSensor.setRangingMode(RangingMode.Short, 100);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Feeder RPM", getRPM());
    SmartDashboard.putNumber("Entry Range", getEntryRange());
    SmartDashboard.putBoolean("Entry Is Valid Target", entryIsValidTarget());
    SmartDashboard.putNumber("Exit Range", getExitRange());
    SmartDashboard.putBoolean("Exit is Valid Target", exitIsValidTarget());
    SmartDashboard.putNumber("Feeder Target RPM", targetRPM);
  }

  public enum State {
    IDLE,
    SHOOT_PREP,
    INTAKE,
    INTAKE_PREP,
    SHOOT_CONTINOUS,
    SHOOT_ONE
  }

  public enum FeedRate {
    LOAD,
    RETURN,
    SHOOT,
    IDLE
  }

  /**
   * Set the RPM of the feeder belt based on the FeedRate enum
   * @param rate the rate for the belt
   */
  public void setRPM(FeedRate rate) {
    int speed = 0;
    switch(rate) {
      case LOAD:
        speed = FEEDER_LOAD_RPM;
        break;
      case RETURN:
        speed = -FEEDER_LOAD_RPM;
        break;
      case SHOOT:
        speed = FEEDER_SHOOT_RPM;
        break;
      case IDLE:
        speed = 0;
        break;
    }
    beltPID.setReference(speed, ControlType.kVelocity);
  }

  public int getRPM() {
    return (int)belt.getEncoder().getVelocity();
  }

  public void resetEncoder() {
    belt.getEncoder().setPosition(0);
  }

  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public double getBeltDistance() {
    return feederBeltEncoder.getPosition();
  }

  public double getEntryRange() {
    return entrySensor.getRange();
  }

  public double getExitRange() {
    return exitSensor.getRange();
  }

  public boolean entryIsValidTarget() {
    return entrySensor.getRange() <= ENTRY_VALID_RANGE;
  }

  public boolean exitIsValidTarget() {
    return exitSensor.getRange() <= EXIT_VALID_RANGE;
  }

    // if(isInShootingMode &&  getShotConfidence() >= SHOOTING_CONFIDENCE_THRESHOLD) {
    //   feederTargetRPM = FEEDER_SHOOT_RPM;
    //   if(feederBelt.getEncoder().getPosition() >= REV_PER_FULL_FEED){
    //     isInShootingMode = false;
    //     feederTargetRPM = 0;
    //   }
    // } else {
    //   if(entryIsValidTarget() && !exitIsValidTarget()) {
    //     feederTargetRPM = FEEDER_LOAD_RPM;
    //   } else {
    //     feederTargetRPM = 0;
    //   }
    // }
    // setFeederRPM(feederTargetRPM);
}
