/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class C_TimeRoll extends CommandBase {
  /**
   * Creates a new TimeRoll.
   */
  private final SS_ControlPanel ss_ControlPanel;
  private double speed;
  private final double DURATION = 10.;     // 1000 ms = 1 second
  private Timer timer = new Timer();
  private double currentTime;
  public C_TimeRoll(SS_ControlPanel subsystem, double speed) {
    // Use addRequirements() here to declare subsystem dependencies.
    ss_ControlPanel = subsystem;
    this.speed = speed;
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    currentTime = timer.get();
    ss_ControlPanel.setSpeed(speed, speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    ss_ControlPanel.setSpeed(0.0, 0.0);
    timer.stop();
    timer.reset();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(currentTime > DURATION) {
      return true;
  }
  else {
      return false;
    }
  }
}
