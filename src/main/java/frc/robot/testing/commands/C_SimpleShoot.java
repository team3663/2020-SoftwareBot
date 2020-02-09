/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.testing.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SS_Shooter;

/**
 * A simple shoot method for testing. It passes the calculated distance from the limelight directly into the shooter subsystem.
 */
public class C_SimpleShoot extends CommandBase {

  private SS_Shooter shooter;

  public C_SimpleShoot(SS_Shooter shooter) {
    this.shooter = shooter;
    addRequirements(shooter);
  }

  @Override
  public void initialize() {
    shooter.setSpinning(true);
  }

  @Override
  public void end(boolean interrupted) {
    shooter.setSpinning(false);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}