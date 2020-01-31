/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.frcteam2910.common.robot.UpdateManager;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.subsystems.SS_Drivebase;
import frc.robot.OI;

public class Robot extends TimedRobot {
  private static final SS_Drivebase ss_Drivebase = new SS_Drivebase();
  private static final OI oi = new OI();

  private UpdateManager updateManager = new UpdateManager(getDrivebase());

  public static ShuffleboardTab controllerInputTab;
  public static NetworkTableEntry controllerLeftXAxisEntry;
  public static NetworkTableEntry controllerLeftYAxisEntry;
  public static NetworkTableEntry controllerRightXAxisEntry;
 
  @Override
  public void robotInit() {
    LiveWindow.disableAllTelemetry();

    oi.bindButtons();
    initControllerTelemetry();

    updateManager.startLoop(5.0e-3);
  }

  public static SS_Drivebase getDrivebase() {
    return ss_Drivebase;
  }

  public static OI getOI() {
    return oi;
  }

  private void initControllerTelemetry() {
    controllerInputTab = Shuffleboard.getTab("Controller Input");
    controllerLeftXAxisEntry = controllerInputTab.add("Controller Left X Axis", 0.0).getEntry();
    controllerLeftYAxisEntry = controllerInputTab.add("Controller Left Y Axis", 0.0).getEntry();
    controllerRightXAxisEntry = controllerInputTab.add("Controller Right X Axis", 0.0).getEntry();
  }

  @Override
  public void robotPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {    
  }

  @Override
  public void autonomousPeriodic() {    
  }

  @Override
  public void teleopPeriodic() {
  }

  @Override
  public void testPeriodic() {
  }
}
