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

public class C_ShootPrep extends CommandBase {
  
  private Vision vision;
  private SS_Shooter shooter;

  public C_ShootPrep(Vision vision, SS_Shooter shooter) {
    this.vision = vision;
    this.shooter = shooter;
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.startSpinning();
    //shooter.setFeederRPM(shooter.FEEDER_LOAD_RPM);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooter.setTargetDistance(vision.getDistance());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //shooter.setFeederRPM(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //return shooter.exitIsValidTarget();
    return false;
  }
}
