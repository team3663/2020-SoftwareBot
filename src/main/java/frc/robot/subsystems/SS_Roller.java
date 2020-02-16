/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class SS_Roller extends SubsystemBase {
  /**
   * Creates a new SS_Roller.
   */
  private static final CANSparkMax rollMotor = new CANSparkMax(DriveConstants.SPARK_MAX_ID, MotorType.kBrushless);

  public SS_Roller() {

  }
  public void roll(double rollSpeed){
    rollMotor.set(rollSpeed);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
