/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.test.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.drivers.TimeOfFlightSensor;

public class C_SensorTest extends CommandBase {
  /**
   * Creates a new C_SensorTest.
   */
  private TimeOfFlightSensor sensor;
  private TimeOfFlightSensor sensorTwo;
  public C_SensorTest(TimeOfFlightSensor sensor, TimeOfFlightSensor sensorTwo) {
    this.sensor = sensor;
    this.sensorTwo = sensorTwo;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    SmartDashboard.putNumber("Distance", sensor.getDistance());
    SmartDashboard.putNumber("DistanceTwo", sensorTwo.getDistance());
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
}
