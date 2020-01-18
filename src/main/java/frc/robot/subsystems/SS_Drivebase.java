/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.*;
import frc.robot.drivers.CPRSwerveModule;

/**
 * Add your docs here.
 */
public class SS_Drivebase extends Subsystem {

  private static final double TRACKWIDTH = 1.0;
  private static final double WHEELBASE = 1.0;
  
  public SS_Drivebase() {
    
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new C_Drive());
  }
}
