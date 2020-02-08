/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SS_Shooter;
import frc.robot.subsystems.SS_Shooter.FeederState;

public class C_IntakePrep extends CommandBase {
  private SS_Shooter shooter;
  public C_IntakePrep(SS_Shooter shooter) {
    this.shooter = shooter;
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooter.resetFeederEncoder();
    shooter.setFeederState(FeederState.INTAKE_PREP);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //shooter.setFeederRPM(-shooter.FEEDER_LOAD_RPM);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.setFeederRPM(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return shooter.entryIsValidTarget() || Math.abs(shooter.getBeltDistance()) >= shooter.REV_PER_FULL_FEED;
  }
}
