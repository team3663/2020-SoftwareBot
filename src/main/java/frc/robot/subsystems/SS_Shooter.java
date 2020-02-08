/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.playingwithfusion.TimeOfFlight;
import com.playingwithfusion.TimeOfFlight.RangingMode;
import com.revrobotics.CANEncoder;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class SS_Shooter extends SubsystemBase {

  //Known RPMs for different distances. Column one: feet, Column two: RPM. 0 feet is parked directly infront of the power port
  private final double[][] KNOWN_RPM = new double[][] {
    {0, 0},
    {10, 3100},
    {15, 3200},
    {20, 3500},
    {25, 4000},
    {30, 4500},
    {35, 5500}
  };
  private final int DISTANCE_COLUMN = 0; //column index for distance values
  private final int RPM_COLUMN = 1; //column index for RPM values
  private final double HOOD_FAR_DISTANCE = 10; //The distance at which the hood switches to the far angle
  private final double SHOOTING_CONFIDENCE_THRESHOLD = 80;

  //the correction multiplier in the code that is fixed (the other one, correctionMultiplier, can be changed during a match)
  private final double WHEEL_GEAR_RATIO_MULTIPLIER = 1;
  private final double FEEDER_BELT_GEAR_RATIO_MULTIPLIER = 1;

  //Wheel PID constants (These values are tuned correctly for the software robot)
  private final double WHEEL_KP = 0.0005;
  private final double WHEEL_KI = 0.000001;
  private final double WHEEL_KD = 0;
 
  //Feeder PID constants
  private final double FEEDER_BELT_KP = 0.0001;
  private final double FEEDER_BELT_KI = 0.0000005;
  private final double FEEDER_BELT_KD = 0;

  private final double DEFAULT_FEED_SPEED = 500; //default speed of the belt feeder

  private final double CONFIDENCE_THRESHOLD = 97; //the threshold or the percent wanted to shoot at
  private final double CONFIDENCE_TIME = 1; //time we want to be in the confidence band before shooting

  public final double FEEDER_SHOOT_RPM = 100; //how fast the feeder should be running when we are shooting
  public final double FEEDER_LOAD_RPM = 100; //how fast the feeder should be running when indexing the balls
  public final double REV_PER_FULL_FEED = 1500; //amount of revolutions before the feeder fully indexes all balls

  private CANSparkMax wheel;
  private CANEncoder wheelEncoder;
  private CANPIDController wheelPID;
  private CANSparkMax feederBelt;
  private CANPIDController feederBeltPID;
  private DoubleSolenoid hoodAngle;

  private TimeOfFlight entrySensor;
  private TimeOfFlight exitSensor;
  private final double ENTRY_VALID_RANGE = 300;
  private final double EXIT_VALID_RANGE = 300;

  private Timer confidenceTimer;

  private double targetRPM = 0;
  private double feederTargetRPM = 0;
  private boolean wheelSpinning = false;
  private boolean isInShootingMode = false;
  private double correctionMultiplier = 1;

  private HoodPosition targetHoodPosition = HoodPosition.NEAR;
  private FeederState feederState = FeederState.IDLE;

  public SS_Shooter() {
    wheel = new CANSparkMax(Constants.FLY_WEEL_MOTOR, MotorType.kBrushless);
    wheel.setInverted(true);
    wheelEncoder = wheel.getEncoder();
    wheelEncoder.setVelocityConversionFactor(WHEEL_GEAR_RATIO_MULTIPLIER);
    wheelPID = wheel.getPIDController();
    wheelPID.setOutputRange(0, 1);
    //set Wheel PID constants
    wheelPID.setP(WHEEL_KP);
    wheelPID.setI(WHEEL_KI);
    wheelPID.setD(WHEEL_KD);
    //hoodAngle = new DoubleSolenoid(1, 2); //TODO

    feederBelt = new CANSparkMax(Constants.FEEDER_BELT_MOTOR, MotorType.kBrushless);
    feederBelt.setIdleMode(IdleMode.kBrake);
    feederBeltPID = feederBelt.getPIDController();
    feederBeltPID.setP(FEEDER_BELT_KP);
    feederBeltPID.setI(FEEDER_BELT_KI);
    feederBeltPID.setD(FEEDER_BELT_KD);
    feederBelt.getEncoder().setVelocityConversionFactor(FEEDER_BELT_GEAR_RATIO_MULTIPLIER); //set feeder gear ratio

    //Sensors for Feeder
    entrySensor = new TimeOfFlight(Constants.ENTRY_SENSOR);
    exitSensor = new TimeOfFlight(Constants.EXIT_SENSOR);
    entrySensor.setRangingMode(RangingMode.Short, 100);
    exitSensor.setRangingMode(RangingMode.Short, 100);

    confidenceTimer = new Timer();
    confidenceTimer.start();
  }

  @Override
  public void periodic() {
    //continually update the targetRPM
    if(wheelSpinning) {
      setRPM(targetRPM);
    } else {
      setRPM(0);
    }

    switch (feederState) {
      case IDLE:
        
        break;
    
      case SHOOT_PREP:

        break;
      
      case SHOOT_ONE:
        break;

      case SHOOT_CONTINOUS:
        break;
      
      case INTAKE_PREP:
        break;

      case INTAKE:
        break;
      default:
        break;
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

    //push telemetry to the smart dashboard
    SmartDashboard.putNumber("target RPM", targetRPM);
    SmartDashboard.putNumber("Current shooter RPM", getCurrentRPM());
    SmartDashboard.putNumber("shooting confidence", getShotConfidence());
    SmartDashboard.putBoolean("wheel spinning", wheelSpinning);

    SmartDashboard.putNumber("Feeder RPM", getFeederRPM());
    SmartDashboard.putNumber("Feeder Target RPM", feederTargetRPM);
    SmartDashboard.putNumber("Entry Range", getEntryRange());
    SmartDashboard.putBoolean("Entry Is Valid Target", entryIsValidTarget());
    SmartDashboard.putNumber("Exit Range", getExitRange());
    SmartDashboard.putBoolean("Exit is Valid Target", exitIsValidTarget());
  }

  /**
   * Sets the distance to shoot for
   * @param targetDistance distance to shoot (in feet)
   */
  public void setTargetDistance(double targetDistance) {
    targetRPM = calculateRPM(targetDistance);
    targetHoodPosition = calculateHoodPosition(targetDistance);
    setHoodPosition(targetHoodPosition);
  }

  /**
   * FOR TESTING ONLY (use setTargetDistance when possible)
   * @param targetRPM the target RPM for the wheel
   */
  public void setTargetRPM(double targetRPM) {
    this.targetRPM = targetRPM;
  }

  /**
   * FOR TESTING ONLY (use setTargetDistance when possible)
   * @param power power to set to the motor from 0 to 1
   */
  public void setPower(double power) {
    wheel.set(Math.max(0, power));
  }

  /**
   * start spinning the flywheel
   */
  public void startSpinning() {
    wheelSpinning = true;
  }

  /**
   * stop spinning the flywheel
   */
  public void stopSpinning() {
    wheelSpinning = false;
  }

  /**
   * Run the belt feeder at the default speed
   */
  public void runFeeder() {
    setFeederRPM(DEFAULT_FEED_SPEED);
  }

  /**
   * stop the belt feeder
   */
  public void stopFeeder() {
    setFeederRPM(0);
  }

  /**
   * shoot one ball
   */
  public void shoot() { //MOVE BELLT HERE
    isInShootingMode = true;
  }

  /**
   * Set the RPM of the feeder belt
   * @param RPM the RPM for the feeder belt
   */
  public void setFeederRPM(double RPM) {
    feederBeltPID.setReference(RPM, ControlType.kVelocity);
  }

  public double getFeederRPM() {
    return feederBelt.getEncoder().getVelocity();
  }

  public void resetFeederEncoder(){
    feederBelt.getEncoder().setPosition(0);
  }
  /**
   * @return the percentage of confidence for the shot based on wheel velocity (from 0-100)
   */
  public double getShotConfidence() {
    //if the wheel is not spinning, we have no confidence in making a shot
    if(!wheelSpinning) {
      return 0;
    }

    //get percentage of current speed to target speed
    double confidence = (getCurrentRPM() / targetRPM) * 100;
    //fix percentage values over 100
    if(confidence > 100) {
      confidence = 100 - (confidence - 100);
    }

    //reset the confidence timer if we are not within the band of error percent we want
    if(confidence < CONFIDENCE_THRESHOLD) {
      confidenceTimer.reset();
    }
    //calculate the percent of the amount of time we want to be in the confidence range to how long we actually are in it
    double confidenceTimePercent = (confidenceTimer.get() / CONFIDENCE_TIME) * 100;
    //prevents from returning confidences over 100
    return Math.min(100, confidenceTimePercent);
  }

  /**
   * Sets the multiplier for correcting shooting distance
   * @param correctionMultiplier the new correction multiplier
   */
  public void setCorrection(double correctionMultiplier) {
    this.correctionMultiplier = correctionMultiplier;
  }

  /**
   * Returns the non-fixed correction multiplier
   * @return the correction multiplier
   */
  public double getCorrection() {
    return correctionMultiplier;
  }

  public boolean isWheelSpinning() {
    return wheelSpinning;
  }

  /**
   * Set target RPM for the wheel
   * @param RPM target RPM for the wheel
   */
  private void setRPM(double RPM) {
    wheelPID.setReference(RPM * correctionMultiplier, ControlType.kVelocity);
  }

  /**
   * returns the RPM of the wheel
   * @return the RPM of the wheel
   */
  private double getCurrentRPM() {
    return wheelEncoder.getVelocity();
  }

  /**
   * Convert distance to correct RPM for shooting power cells
   * @param distance distance to convert to RPMs (in feet)
   * @return RPMs converted from distance
   */
  private double calculateRPM(double distance) {
    distance = Math.round(distance); //round the distance so that 5.1 feet does not use the value for 6 feet
    int index = 0;
    for(int i = 0; i < KNOWN_RPM.length; i++) {
      double currentDistance = KNOWN_RPM[i][DISTANCE_COLUMN];
      //If the rounded distance equals a known distance, use that known distance
      if(currentDistance == distance) {
        return KNOWN_RPM[i][RPM_COLUMN];
      } else if(currentDistance > distance) {
        //if the index is at the lowest distance, return the lowest RPM
        if(i == 0) {
          return KNOWN_RPM[0][RPM_COLUMN];
        }
        index = i;
        return linearInterpolation(index, distance);
      }
    }
    return KNOWN_RPM[KNOWN_RPM.length - 1][RPM_COLUMN];
  }

  /**
   * Interpolate the RPM of that the shooter should spin based on the distance and the index of the closest greater RPM in the table
   * @param index  the index of the closest greater RPM in the table
   * @param distance the distance away from the target
   * @return the needed RPM for the distance
   */
  private double linearInterpolation(int index, double distance) {
    //find the range between the two known distances
    double distanceRange = KNOWN_RPM[index][DISTANCE_COLUMN] - KNOWN_RPM[index - 1][DISTANCE_COLUMN];
    double RPMRange = (KNOWN_RPM[index][RPM_COLUMN] - KNOWN_RPM[index - 1][RPM_COLUMN]);
    //calculate the new distance between the known distances linearly
    return ((distance - KNOWN_RPM[index - 1][DISTANCE_COLUMN]) / distanceRange * RPMRange) + KNOWN_RPM[index - 1][RPM_COLUMN];
  }

  /**
   * set the target angle psotion of the hood based on a distance and updates the target hood position
   * @param targetDistance The distance used in calculating the hood angle position
   */
  private HoodPosition calculateHoodPosition(double distance) {
    if(distance >= HOOD_FAR_DISTANCE) {
      return HoodPosition.FAR;
    } else {
      return HoodPosition.NEAR;
    }
  }

  public FeederState getFeederState(){
    return feederState;
  }

  private void setFeederState(FeederState state){
    feederState = state;
  }
  /**
   * Set the hood position
   * @param hoodPosition the angle position of the hood
   */
  private void setHoodPosition(HoodPosition hoodPosition) {
    switch(hoodPosition) {
      case NEAR:
        //hoodAngle.set(RobotMap.SHOOTER_FAR_ANGLE);
        break;
      case FAR:
        //hoodAngle.set(RobotMap.SHOOTER_NEAR_ANGLE);
        break;
    }
  }

  public enum HoodPosition {
    NEAR,
    FAR
  }

  public enum FeederState{
    IDLE,
    SHOOT_PREP,
    INTAKE,
    INTAKE_PREP,
    SHOOT_CONTINOUS,
    SHOOT_ONE
  }
  
  public void setIdle(){
    setFeederRPM(0);
    setFeederState(FeederState.IDLE);
  }
  public void shootPrep(){
    
    setFeederState(FeederState.IDLE);
  }
  public double getEntryRange(){
    return entrySensor.getRange();
  }
  public double getExitRange(){
    return exitSensor.getRange();
  }
  public boolean entryIsValidTarget(){
    return entrySensor.getRange() <= ENTRY_VALID_RANGE;
  }

  public boolean exitIsValidTarget(){
    return exitSensor.getRange() <= EXIT_VALID_RANGE;
  }
}
