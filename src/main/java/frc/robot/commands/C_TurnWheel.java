/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;

public class C_TurnWheel extends CommandBase {

  /**
   * Creates a new C_SpinWheel.
 * @param SS_ControlPanel 
   */
  public C_TurnWheel() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(Robot.ss_ControlPanel); 
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    Robot.ss_ControlPanel.setSpeed(RobotContainer.controller.getRawAxis(Constants.R_STICK_Y_AXIS));
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
