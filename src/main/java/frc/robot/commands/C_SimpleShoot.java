/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.drivers.Vision;
import frc.robot.subsystems.SS_Shooter;

/**
 * A simple shoot method for testing. It passes the calculated distance from the limelight directly into the shooter subsystem.
 */
public class C_SimpleShoot extends CommandBase {

  private SS_Shooter shooter;
  private Vision vision;

  public C_SimpleShoot(Vision vision, SS_Shooter shooter) {
    this.shooter = shooter;
    this.vision = vision;
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.startSpinning();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooter.setTargetDistance(vision.getDistance());
    vision.updateTelemetry();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stopSpinning();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}