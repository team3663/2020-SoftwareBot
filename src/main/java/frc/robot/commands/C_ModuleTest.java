/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.SS_ModuleTest;

public class C_ModuleTest extends Command {
  public C_ModuleTest() {
    requires(Robot.getModuleTest());
  }

  private double targetAngle = 0.0;

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    /*if(Robot.getOI().getController().getBButton().get()){
      targetAngle = 0;
    }
    if(Robot.getOI().getController().getYButton().get()){
      targetAngle = Math.PI / 2;
    }
    if(Robot.getOI().getController().getXButton().get()) {
      targetAngle = Math.PI;
    }
    if(Robot.getOI().getController().getAButton().get()) {
      targetAngle = 3 * Math.PI / 2;
    }*/
    
    Robot.getModuleTest().setAngle(targetAngle);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.getModuleTest().setAngle(0);
  }

  @Override
  protected void interrupted() {
    end();
  }
}
