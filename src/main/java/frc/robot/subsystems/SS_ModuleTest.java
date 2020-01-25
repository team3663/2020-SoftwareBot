/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.frcteam2910.common.math.Vector2;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.*;
import frc.robot.drivers.CPRSwerveModule;
import frc.robot.drivers.NavX;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import org.frcteam2910.common.robot.UpdateManager;
/**
 * Add your docs here.
 */
public class SS_ModuleTest extends Subsystem implements UpdateManager.Updatable{
  //private static final SS_ModuleTest instance;
  private final CPRSwerveModule module;
  //private final NavX navx= new NavX(Port.kUSB, RobotMap.NAVX_UPDATE_RATE);

  /*
  static {
    instance = new SS_ModuleTest();
  }

  public static SS_ModuleTest getInstance() {
    return instance;
  }*/

  private NetworkTableEntry moduleEncoderVoltage;
  private NetworkTableEntry moduleAbsoluteAngleEntry;

  private NetworkTableEntry moduleIntegratedAngleEntry;

  public SS_ModuleTest() {
    module = new CPRSwerveModule(new Vector2(0, 1), 0, new CANSparkMax(RobotMap.TEST_ANGLE_MOTOR, MotorType.kBrushless), new CANSparkMax(RobotMap.TEST_DRIVE_MOTOR, MotorType.kBrushless));
    ShuffleboardTab tab = Shuffleboard.getTab("Drivetrain");

    ShuffleboardLayout testModulesContainer = tab.getLayout("Test Module", BuiltInLayouts.kList)
            .withPosition(1, 0)
            .withSize(2, 3);
    moduleAbsoluteAngleEntry = testModulesContainer.add("Absolute Encoder Angle", 0.0).getEntry();
    moduleEncoderVoltage = testModulesContainer.add("Voltage", 0.0).getEntry();
    moduleIntegratedAngleEntry = testModulesContainer.add("Integrated Encoder Angle", 0.0).getEntry();
  }

  public void update(double timestamp, double dt) {
    updateModule(dt);
  }

  public void updateModule(double dt) {
    module.updateState(dt);
  }

  /**
   * @param angle IN RADIANS
   */
  public void setAngle(double angle) {
    module.setTargetAngle(angle);
  }

  @Override
  public void periodic() {
    super.periodic();
    moduleAbsoluteAngleEntry.setDouble(Math.toDegrees(module.readAngle()));
    moduleEncoderVoltage.setDouble(module.getEncoderVoltage());
    moduleIntegratedAngleEntry.setDouble(module.getIntegratedEncoderAngle());
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new C_ModuleTest());
  }
}
