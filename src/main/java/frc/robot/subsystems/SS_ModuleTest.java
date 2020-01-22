/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
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
/**
 * Add your docs here.
 */
public class SS_ModuleTest extends Subsystem {
  private static final SS_ModuleTest instance;
  private final CPRSwerveModule module;
  private final NavX navx= new NavX(Port.kUSB, RobotMap.NAVX_UPDATE_RATE);

  static {
    instance = new SS_ModuleTest();
  }

  public static SS_ModuleTest getInstance() {
    return instance;
  }

  private NetworkTableEntry poseXEntry;
  private NetworkTableEntry poseYEntry;
  private NetworkTableEntry poseAngleEntry;

  private NetworkTableEntry moduleAngleEntry;

  public SS_ModuleTest() {
    module = new CPRSwerveModule(new Vector2(0, 1), 0, new CANSparkMax(RobotMap.TEST_ANGLE_MOTOR, MotorType.kBrushless), new CANSparkMax(RobotMap.TEST_DRIVE_MOTOR, MotorType.kBrushless));
    ShuffleboardTab tab = Shuffleboard.getTab("Drivetrain");


    poseXEntry = tab.add("Pose X", 0.0)
            .withPosition(0, 0)
            .withSize(1, 1)
            .getEntry();
    poseYEntry = tab.add("Pose Y", 0.0)
            .withPosition(0, 1)
            .withSize(1, 1)
            .getEntry();
    poseAngleEntry = tab.add("Pose Angle", 0.0)
            .withPosition(0, 2)
            .withSize(1, 1)
            .getEntry();
    ShuffleboardLayout testModulesContainer = tab.getLayout("Test Module", BuiltInLayouts.kList)
            .withPosition(1, 0)
            .withSize(2, 3);
    moduleAngleEntry = testModulesContainer.add("Angle", 0.0).getEntry();
  }
  /**
   * @param angle IN RADIANS
   */
  public void setAngle(double angle) {
    module.setTargetAngle(angle);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new C_ModuleTest());
  }
}
