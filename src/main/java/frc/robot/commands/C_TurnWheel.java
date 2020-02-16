/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SS_ControlPanel;

public class C_TurnWheel extends CommandBase {

  /**
   * Creates a new C_SpinWheel.
 * @param SS_ControlPanel 
   */    
  private DoubleSupplier leftSpeed;
  private DoubleSupplier rightSpeed;
  private SS_ControlPanel ss_ControlPanel;

  public C_TurnWheel(SS_ControlPanel ss_ControlPanel, DoubleSupplier leftSpeed, DoubleSupplier rightSpeed) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.leftSpeed = leftSpeed;
    this.rightSpeed = rightSpeed;
    this.ss_ControlPanel = ss_ControlPanel;
    addRequirements(ss_ControlPanel); 
  }

// Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    ss_ControlPanel.setSpeed(leftSpeed.getAsDouble(), rightSpeed.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //Robot.ss_ControlPanel.setSpeed(0);
  }
 
  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}