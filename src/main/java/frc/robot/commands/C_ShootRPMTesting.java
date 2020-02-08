/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import org.frcteam2910.common.robot.input.Controller;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.drivers.Vision;
import frc.robot.subsystems.SS_Shooter;
//import jdk.internal.jshell.tool.resources.version;

public class C_ShootRPMTesting extends CommandBase {

  private final int RPMS_PER_PRESS = 100;

  private Controller controller;
  private Vision vision;
  private SS_Shooter shooter;

  private int RPM = 500;
  private boolean pressed = false;

  public C_ShootRPMTesting(Controller controller, Vision vision, SS_Shooter shooter) {
    //Shuffleboard
    this.controller = controller;
    this.shooter = shooter;
    this.vision = vision;
    // Use addRequirements() here to declare subsystem dependencies.
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
    if(controller.getAButton().get()) {
      if(!pressed) {
        RPM += RPMS_PER_PRESS;
      }
      pressed = true;
    } else if(controller.getBButton().get()) {
      if(!pressed) {
        RPM -= RPMS_PER_PRESS;
      }
      pressed = false;
    } else {
      pressed = false;
    }
    shooter.setTargetRPM(RPM);

    SmartDashboard.putNumber("Set Target RPM", RPM);
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