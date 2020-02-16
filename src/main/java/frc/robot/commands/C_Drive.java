/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;
import frc.robot.subsystems.SS_ControlPanel;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class C_Drive extends CommandBase {
  /**
   * Creates a new C_Drive.
   */
  private final SS_ControlPanel ss_ControlPanel;
  private DoubleSupplier speedLeft;
  private DoubleSupplier speedRight;
  private double scale = 0.5;

  public C_Drive(SS_ControlPanel subsystem, DoubleSupplier speedLeft, DoubleSupplier speedRight) {
    // Use addRequirements() here to declare subsystem dependencies.
    ss_ControlPanel = subsystem;
    this.speedLeft = speedLeft;
    this.speedRight = speedRight;
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    ss_ControlPanel.tankDrive(speedLeft.getAsDouble() * scale, speedRight.getAsDouble() * scale);
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
