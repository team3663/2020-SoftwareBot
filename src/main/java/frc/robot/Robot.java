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
import frc.robot.commands.C_AutoSelectorTest;
import frc.robot.util.AutoSelector;

public class Robot extends TimedRobot {
  //private final RobotContainer container = new RobotContainer();
  AutoSelector selector = new AutoSelector();
  C_AutoSelectorTest selectorTest = new C_AutoSelectorTest(selector);

  @Override
  public void robotInit() {
  }

  @Override
  public void teleopInit() {
    selectorTest.schedule();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

}
