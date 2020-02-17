/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

/**
 * A command group that runs an array of commands. Designed to work with the Autonomous builder
 */
public class AutonomousRunner extends SequentialCommandGroup {

  public AutonomousRunner(Command[] commands) {
    addCommands(commands);
  }
}
