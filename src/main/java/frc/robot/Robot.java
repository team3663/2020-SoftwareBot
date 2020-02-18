/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.RobotContainer;
import frc.robot.drivers.TimeOfFlightSensor;
import frc.robot.test.commands.C_SensorTest;

public class Robot extends TimedRobot {
  private final RobotContainer container = new RobotContainer();
  private final TimeOfFlightSensor sensor = new TimeOfFlightSensor(Constants.SENSOR_ID);
  @Override
  public void robotInit() {
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().schedule(new C_SensorTest(sensor));
    CommandScheduler.getInstance().run();
  }

}
