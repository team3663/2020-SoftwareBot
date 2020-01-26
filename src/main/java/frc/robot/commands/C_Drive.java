/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import org.frcteam2910.common.math.Vector2;
import org.frcteam2910.common.robot.input.Axis;
import org.frcteam2910.common.robot.input.DPadButton.Direction;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.SS_Drivebase;

public class C_Drive extends Command {
  OI oi = Robot.getOI();
  SS_Drivebase ss_DriveBase = Robot.getDrivebase();
  public C_Drive() {
    requires(Robot.getDrivebase());
  }

  @Override
  protected void execute() {
    double forward = Robot.getOI().getDriveForwardAxis().get(true);
    double strafe = -Robot.getOI().getDriveStrafeAxis().get(true);
    double rotation = Robot.getOI().getDriveRotationAxis().get(true);
    
    ss_DriveBase.drive(new Vector2(forward, strafe), rotation, true);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.getDrivebase().drive(Vector2.ZERO, 0.0, false);
  }

  @Override
  protected void interrupted() {
    end();
  }
}
