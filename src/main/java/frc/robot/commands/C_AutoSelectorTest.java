/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.util.AutoSelector;

//A simple class to move the motor 
public class C_AutoSelectorTest extends CommandBase {

  private final double TEST_SPEED = 0.05;
  private CANSparkMax testMotor;
  private AutoSelector selector;

  public C_AutoSelectorTest(AutoSelector selector) {
    testMotor = new CANSparkMax(20, MotorType.kBrushless);
    this.selector = selector;
  }

  @Override
  public void initialize() {}

  @Override
  public void execute() {
    if(!selector.getShootFromLine()) {
      testMotor.set(0);
    } else {
      switch(selector.getStartingPosition()) {
        case LEFT:
          testMotor.set(-TEST_SPEED);
          break;
        case RIGHT:
          testMotor.set(TEST_SPEED);
          break;
        case MIDDLE:
          testMotor.set(0);
          break;
      }
    }
  }

  @Override
  public void end(boolean interrupted) {
    testMotor.set(0);
  }

  @Override
  public boolean isFinished() {
    return false;
  }
}
