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
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.drivers.Vision;


public class SS_Shooter extends SubsystemBase {

  //Known RPMs for different distances. Column one: feet, Column two: RPM. 0 feet is parked directly infront of the power port
  private final int[][] KNOWN_RPM = new int[][] {
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
  private final int HOOD_FAR_DISTANCE = 10; //The distance at which the hood switches to the far angle

  //the correction multiplier in the code that is fixed (the other one, correctionMultiplier, can be changed during a match)
  private final double WHEEL_GEAR_RATIO_MULTIPLIER = 1;
  
  private final int SHOT_FINISHED_RPM_THRESHOLD = 100;

  //Wheel PID constants (These values are tuned correctly for the software robot)
  private final double KP = 0.0005;
  private final double KI = 0.000001;
  private final double KD = 0;

  private final double CONFIDENCE_THRESHOLD = 97; //the threshold or the percent wanted to shoot at
  private final double CONFIDENCE_TIME = 1; //time we want to be in the confidence band before shooting

  private Vision vision;

  private CANSparkMax wheel;
  private CANEncoder encoder;
  private CANPIDController PID;
  private DoubleSolenoid hood;

  private Timer confidenceTimer;

  private int targetRPM = 0; //the target RPM when not updating from vision
  private int shotRPM = 0; //the RPM at the time of the shot

  private boolean wheelSpinning = false;
  private boolean updateFromVision = false;
  private double correctionMultiplier = 1;

  //Network tables for telemetry
  private NetworkTableEntry targetRPMEntry;
  private NetworkTableEntry currentRPMEntry;
  private NetworkTableEntry wheelSpinningEntry;
  private NetworkTableEntry shootingConfidenceEntry;

  public SS_Shooter(Vision vision) {
    this.vision = vision;
    wheel = new CANSparkMax(Constants.FLY_WEEL_MOTOR, MotorType.kBrushless);
    wheel.setInverted(true);
    encoder = wheel.getEncoder();
    encoder.setVelocityConversionFactor(WHEEL_GEAR_RATIO_MULTIPLIER);
    PID = wheel.getPIDController();
    PID.setOutputRange(0, 1);

    //set Wheel PID constants
    PID.setP(KP);
    PID.setI(KI);
    PID.setD(KD);
    //hoodAngle = new DoubleSolenoid(1, 2); //TODO

    initTelemetry();

    confidenceTimer = new Timer();
    confidenceTimer.start();
  }

  private void initTelemetry() {
    ShuffleboardTab shooterTab = Shuffleboard.getTab("Shooter");
    targetRPMEntry = shooterTab.add("Target RPM", 0)
      .withPosition(0, 0)
      .withSize(1, 1)
      .getEntry();
    currentRPMEntry = shooterTab.add("Current RPM", 0)
      .withPosition(0, 1)
      .withSize(1, 1)
      .getEntry();
    shootingConfidenceEntry = shooterTab.add("RPM Confidence", 0)
      .withPosition(1, 0)
      .withSize(1, 1)
      .getEntry();
    wheelSpinningEntry = shooterTab.add("Spinning", false)
      .withWidget("Boolean Box")
      .withPosition(1, 1)
      .withSize(1, 1)
      .getEntry();
  }

  @Override
  public void periodic() {
    updateShooter();
    updateTelemetry();
  }

  //update the targetRPM
  private void updateShooter() {
    if(wheelSpinning) {
      if(updateFromVision) {
        double distance = vision.getDistance();
        if(distance > 0) {
          setRPM(calculateRPM(distance));
        }
      } else {
        setRPM(targetRPM);
      }
    } else {
      setRPM(0);
    }
  }

   //push telemetry to Shuffleboard
  private void updateTelemetry() {
    targetRPMEntry.setNumber(targetRPM);
    currentRPMEntry.setNumber(encoder.getVelocity());
    shootingConfidenceEntry.setNumber(getShotConfidence());
    wheelSpinningEntry.setBoolean(wheelSpinning);
  }

  /**
   * spins and updates the wheel from vision
   * @param spinning true if wheel should spin
   */
  public void setSpinning(boolean spinning) {
    updateFromVision = true;
    wheelSpinning = spinning;
  }

  /**
   * spins wheel based on target distance
   * @param spinning true if wheel should spin
   * @param targetDistance the distance from the target that RPM is based on
   */
  public void setSpinning(boolean spinning, double targetDistance) {
    updateFromVision = false;
    wheelSpinning = spinning;
    targetRPM = calculateRPM(targetDistance);
  }

  public void shootPrep() {
    setHoodFar(true);
    shotRPM = (int)encoder.getVelocity();
  }

  public boolean shotFinished() {
    //return shotRPM - encoder.getVelocity() < SHOT_FINISHED_RPM_THRESHOLD;
    return true; //TODO
  }

  /**
   * Sets the multiplier for correcting shooting distance
   * @param correctionMultiplier the new correction multiplier
   */
  public void setCorrectionMultiplier(double correctionMultiplier) {
    this.correctionMultiplier = correctionMultiplier;
  }

  /**
   * @return the percentage of confidence for the shot based on wheel velocity (from 0-100)
   */
  public int getShotConfidence() {
    //if the wheel is not spinning, we have no confidence in making a shot
    if(!wheelSpinning) {
      return 0;
    }

    //get percentage of current speed to target speed
    double confidence = (encoder.getVelocity() / targetRPM) * 100;
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
    return (int)Math.min(100, confidenceTimePercent);
  }

  /**
   * Returns the non-fixed correction multiplier
   * @return the correction multiplier
   */
  public double getCorrection() {
    return correctionMultiplier;
  }

  public boolean isSpinning() {
    return wheelSpinning;
  }

  /**
   * Set target RPM for the wheel
   * @param RPM target RPM for the wheel
   */
  private void setRPM(int RPM) {
    PID.setReference(RPM * correctionMultiplier, ControlType.kVelocity);
  }

  /**
   * FOR TEST COMMANDS ONLY! Directly sets shooter target RPM
   * Specifically meant for the C_ShootRPMTesting command which is used for finding the correct RPM for various distances
   */
  public void testSetTargetRPM(int RPM) {
    targetRPM = RPM;
  }

  /**
   * Convert distance to correct RPM for shooting power cells
   * @param distance distance to convert to RPMs (in feet)
   * @return RPMs converted from distance
   */
  private int calculateRPM(double distance) {
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
  private int linearInterpolation(int index, double distance) {
    //find the range between the two known distances
    double distanceRange = KNOWN_RPM[index][DISTANCE_COLUMN] - KNOWN_RPM[index - 1][DISTANCE_COLUMN];
    double RPMRange = (KNOWN_RPM[index][RPM_COLUMN] - KNOWN_RPM[index - 1][RPM_COLUMN]);
    //calculate the new distance between the known distances linearly
    return (int)((distance - KNOWN_RPM[index - 1][DISTANCE_COLUMN]) / distanceRange * RPMRange) + KNOWN_RPM[index - 1][RPM_COLUMN];
  }

  /**
   * set the position of the hood
   * @param far if true, set hood to position for far shooting, otherwise set it to the near position
   */
  private void setHoodFar(boolean far) {
    if(far) {
      //hood.set(RobotMap.SHOOTER_FAR_ANGLE);
    } else {
      //hood.set(RobotMap.SHOOTER_NEAR_ANGLE);
    }
  }
}