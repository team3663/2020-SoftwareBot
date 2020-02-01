/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import org.frcteam2910.common.math.Vector2;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.SS_Drivebase;

public class C_Drive extends CommandBase {
  OI oi = Robot.getOI();
  SS_Drivebase ss_DriveBase = Robot.getDrivebase();
  private double defaultDeadbandRange = .16;
  private boolean squareInputs = true;
  public C_Drive() {
    addRequirements(Robot.getDrivebase());
  }

  @Override
  public void execute() {
    double forward = deadband(Robot.getOI().getDriveForwardAxis().get(), defaultDeadbandRange, squareInputs);
    Robot.controllerLeftYAxisEntry.setDouble(Robot.getOI().getDriveForwardAxis().getRaw());

    double strafe = deadband(Robot.getOI().getDriveStrafeAxis().get(), defaultDeadbandRange, squareInputs);
    Robot.controllerLeftXAxisEntry.setDouble(Robot.getOI().getDriveStrafeAxis().getRaw());

    double rotation = Robot.getOI().getDriveRotationAxis().get(true);
    Robot.controllerRightXAxisEntry.setDouble(Robot.getOI().getDriveRotationAxis().getRaw());

    ss_DriveBase.drive(new Vector2(forward, strafe), rotation, true);
  }

  @Override
  public boolean isFinished() {
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    Robot.getDrivebase().drive(Vector2.ZERO, 0.0, false);
  }

  /**
   * @param input controller axis input, from -1 to 1
   * @param range absolute range to deadband, usually around .05 to .20
   * @return deadbanded input
   */
  private double deadband(double input, double range) {
    if(Math.abs(input) < Math.abs(range)) {
      return 0;
    } 
    return input;
  }

  private double deadband(double input, double range, boolean squared) {
    if(Math.abs(input) < Math.abs(range)) {
      return 0;
    }
    return Math.pow(input, 2) * Math.signum(input);
  }
}
