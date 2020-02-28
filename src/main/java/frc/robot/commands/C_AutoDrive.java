/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import org.frcteam2910.common.control.PidConstants;
import org.frcteam2910.common.control.PidController;
import org.frcteam2910.common.math.RigidTransform2;
import org.frcteam2910.common.math.Vector2;
import org.frcteam2910.common.util.HolonomicDriveSignal;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SS_Drivebase;

public class C_AutoDrive extends CommandBase {

  private SS_Drivebase drivebase;
  private Vector2 targetTranslation;
  private double targetRotation;

  private double translationKp = 0;
  private double translationKi = 0;
  private double translationKd = 0;
  private PidConstants translationConstants = new PidConstants(translationKp, translationKi, translationKd);

  private double angleKp = 0;
  private double angleKi = 0;
  private double angleKd = 0;
  private PidConstants angleConstants = new PidConstants(angleKp, angleKi, angleKd);

  private PidController forwardController = new PidController(translationConstants);
  private PidController strafeController = new PidController(translationConstants);
  private double translationPercentTolerance = .015;

  private PidController rotationController = new PidController(angleConstants);
  private double rotationPercentTolerance = .01;

  private double lastTimeStamp;
  private double currentAngle;
  private RigidTransform2 currentPose;

  /**
   * @param drivebase the drivebase subsystem
   * @param targetTranslation inches for the robot to travel, negative inches are backwards
   * @param angle angle to travel at in radians
   */
  public C_AutoDrive(SS_Drivebase drivebase, Vector2 targetTranslation, double translationPercentOutput,
      double targetRotation, double rotationPercentOutput) {
    this.drivebase = drivebase;
    this.targetTranslation = targetTranslation;
    this.targetRotation = targetRotation;
    addRequirements(drivebase);

    forwardController.setSetpoint(targetTranslation.x);
    forwardController.setOutputRange(-translationPercentOutput, translationPercentOutput);
    strafeController.setSetpoint(targetTranslation.y);
    strafeController.setOutputRange(-translationPercentOutput, translationPercentOutput);
    
    rotationController.setSetpoint(targetRotation);
    rotationController.setInputRange(0, Math.PI * 2);
    rotationController.setContinuous(true);
    rotationController.setOutputRange(-rotationPercentOutput, rotationPercentOutput);
  }

  @Override
  public void initialize(){
    drivebase.resetPoseTranslation();
  }

  @Override
  public void execute() {
    double currentTime = Timer.getFPGATimestamp();
    double dt = currentTime - lastTimeStamp;
    lastTimeStamp = currentTime;
    currentPose = drivebase.getPose();
    currentAngle = currentPose.rotation.toRadians();

    double forward = forwardController.calculate(currentPose.translation.x, dt);
    double strafe = strafeController.calculate(currentPose.translation.y, dt);
    double rotation = rotationController.calculate(currentPose.rotation.toRadians(), dt);

    drivebase.drive(new Vector2(forward, strafe), rotation, true);
  }

  @Override
  public void end(boolean interrupted) {
    drivebase.drive(new HolonomicDriveSignal(new Vector2(0.0, 0.0), 0.0, false));
  }

  @Override
  public boolean isFinished() {
    return Math.abs(targetRotation - currentAngle) < targetRotation * rotationPercentTolerance &&
        Math.abs(targetTranslation.y - currentPose.translation.y) < targetTranslation.y * translationPercentTolerance &&
        Math.abs(targetTranslation.x - currentPose.translation.x) < targetTranslation.x * translationPercentTolerance;
  }
}
