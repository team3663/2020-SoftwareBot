/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import frc.robot.Constants.DriveConstants;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Add your docs here.
 */
public class SS_ControlPanel extends SubsystemBase {

  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private static final TalonSRX leftMotor = new TalonSRX(DriveConstants.L_TALON_SRX_ID);
  private static final TalonSRX rightMotor = new TalonSRX(DriveConstants.R_TALON_SRX_ID);


  public void setSpeed(double speedLeft, double speedRight) {
    leftMotor.set(ControlMode.PercentOutput, speedLeft);
    rightMotor.set(ControlMode.PercentOutput, speedRight);


  }
  public void tankDrive(double speedLeft, double speedRight){
    //leftMoter.set()
  }
  
}