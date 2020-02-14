/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.drivers.TimeOfFlightSensor;

public class SS_Feeder extends SubsystemBase {

  private final double FEEDER_BELT_GEAR_RATIO_MULTIPLIER = 1;

  //Feeder PID constants
  private final double KP = 0.0001;
  private final double KI = 0.0000005;
  private final double KD = 0;

  public final int FEEDER_SHOOT_RPM = 100; //how fast the feeder should be running when we are shooting
  public final int FEEDER_LOAD_RPM = 100; //how fast the feeder should be running when indexing the balls

  public final int REV_PER_FULL_FEED = 1500; //amount of revolutions before the feeder fully indexes all balls

  private final double ENTRY_VALID_RANGE = 30;
  private final double EXIT_VALID_RANGE = 30;

  private CANEncoder feederBeltEncoder;

  private CANSparkMax belt;
  private CANPIDController beltPID;

  private TimeOfFlightSensor entrySensor;
  private TimeOfFlightSensor exitSensor;

  private State state = State.IDLE;

  private FeedRate feedRate = FeedRate.IDLE;

  //network table entries for telemetry
  private NetworkTableEntry feedRateEntry;
  private NetworkTableEntry ballInEntranceEntry;
  private NetworkTableEntry ballInExitEntry;
  private NetworkTableEntry feederRPMEntry;
  private NetworkTableEntry exitRangeEntry;
  private NetworkTableEntry entryRangeEntry;

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
    entrySensor = new TimeOfFlightSensor(Constants.ENTRY_SENSOR);
    exitSensor = new TimeOfFlightSensor(Constants.EXIT_SENSOR);
    initTelemetry();
  }

  private void initTelemetry() {
    ShuffleboardTab shooterTab = Shuffleboard.getTab("Shooter"); //use the same tab as the shooter for displaying data

    feedRateEntry = shooterTab.add("Feed Rate", "unassigned")
      .withPosition(3, 0)
      .withSize(1, 1)
      .getEntry();
      feederRPMEntry = shooterTab.add("Feeder RPM", 0)
      .withPosition(3, 1)
      .withSize(1, 1)
      .getEntry();
    ballInEntranceEntry = shooterTab.add("Ball in entrance", false)
      .withWidget("Boolean Box")
      .withPosition(4, 0)
      .withSize(1, 1)
      .getEntry();
    ballInExitEntry = shooterTab.add("ball in exit", false)
      .withWidget("Boolean Box")
      .withPosition(4, 1)
      .withSize(1, 1)
      .getEntry();
    entryRangeEntry = shooterTab.add("Entry range", 0)
      .withPosition(5, 0)
      .withSize(1, 1)
      .getEntry();
    exitRangeEntry = shooterTab.add("Exit range", 0)
      .withPosition(5, 1)
      .withSize(1, 1)
      .getEntry();
  }

  @Override
  public void periodic() {
    updateTelemetry();
  }

  private void updateTelemetry() {
    feedRateEntry.setString(feedRate.toString());
    feederRPMEntry.setNumber(getRPM());
    ballInEntranceEntry.setBoolean(entryIsValidTarget());
    ballInExitEntry.setBoolean(exitIsValidTarget());
    entryRangeEntry.setNumber(getEntryRange());
    exitRangeEntry.setNumber(getExitRange());
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
    feedRate = rate;
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
    return entrySensor.getDistance();
  }

  public double getExitRange() {
    return exitSensor.getDistance();
  }

  public boolean entryIsValidTarget() {
    return entrySensor.getDistance() <= ENTRY_VALID_RANGE;
  }

  public boolean exitIsValidTarget() {
    return exitSensor.getDistance() <= EXIT_VALID_RANGE;
  }
}
