/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Simple command to delay the robot for a moment in autonomous
 */
public class C_Delay extends CommandBase {
  double delay = 0;
  Timer timer = new Timer();

  /**
   * @param delay time to delay in seconds
   */
  public C_Delay(double delay) {
    this.delay = delay;
  }

  @Override
  public void initialize() {
    timer.start();
  }

  @Override
  public boolean isFinished() {
    return timer.get() >= delay;
  }
}
