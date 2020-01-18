/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SS_NavX extends SubsystemBase {
  /**
   * Creates a new SS_NavX.
   */

  private AHRS navx;
   
  public SS_NavX() {
    navx = new AHRS(Port.kUSB);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run 
  }

  public double getAngle(){
    return navx.getAngle();
  }

  public double getAngle(Axis axis){
      switch(axis){
          case PITCH:
            return navx.getPitch();
          case ROLL:
            return navx.getRoll();
          case YAW:
            return navx.getYaw();
          default:
            return 0.0;
      }
  }

  public void updateTelemetry(){
    SmartDashboard.putNumber("yaw Rotation", getAngle());
    SmartDashboard.putNumber("pitch Rotation", getAngle(Axis.PITCH));
    SmartDashboard.putNumber("roll Rotation", getAngle(Axis.ROLL));

  }

  public void reset(){
    navx.reset();
  }
  public enum Axis{
    PITCH,
    ROLL,
    YAW
  }


}
