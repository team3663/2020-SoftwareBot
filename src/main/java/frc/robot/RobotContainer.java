/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

<<<<<<< Updated upstream
import org.frcteam2910.common.math.Rotation2;
import org.frcteam2910.common.robot.UpdateManager;
import org.frcteam2910.common.robot.input.Controller;
import org.frcteam2910.common.robot.input.XboxController;

import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants;
import frc.robot.commands.C_Drive;
import frc.robot.subsystems.SS_Drivebase;
=======
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.SS_Roller;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.C_Gondola;
import frc.robot.commands.C_Wench;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
>>>>>>> Stashed changes

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    
    // The robot's subsystems and commands are defined here...
    // private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
    
    private SS_Roller ss_Roller = new SS_Roller();
    private XboxController driverController = new XboxController(0);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        // CommandScheduler.getInstance().setDefaultCommand(ss_DriveTrain, new
        // C_Drive(ss_DriveTrain, driverController));
        CommandScheduler.getInstance().setDefaultCommand(ss_Roller, new C_Gondola(ss_Roller, driverController));
        // Configure the button bindings
    configureButtonBindings();
  }

<<<<<<< Updated upstream
    // Instantiate subsystems here
    private final SS_Drivebase drivebase = new SS_Drivebase();

    // All updatable subsystems should be passed as parameters into the
    // UpdateManager constructor
    private final UpdateManager updateManager = new UpdateManager(drivebase);

    public RobotContainer() {
        
        driveController.getRightXAxis().setScale(.3);

        CommandScheduler.getInstance().setDefaultCommand(drivebase,
                new C_Drive(drivebase, () -> driveController.getLeftYAxis().get(true),
                        () -> driveController.getLeftXAxis().get(true),
                        () -> driveController.getRightXAxis().get(true)));
        
        /*
        CommandScheduler.getInstance().setDefaultCommand(drivebase,
                new C_Drive(drivebase, () -> driveController.getLeftYAxis().get(true),
                        () -> 0.0,
                        () -> driveController.getRightXAxis().get(true)));
        */
        updateManager.startLoop(5.0e-3);
=======
  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // button  -> roll rih

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
>>>>>>> Stashed changes


<<<<<<< Updated upstream
    private void configureButtonBindings() {
        driveController.getBackButton().whenPressed(new InstantCommand(() -> drivebase.resetGyroAngle(Rotation2.ZERO), drivebase));
    }
=======
  //public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    //return m_autoCommand;
  //}
>>>>>>> Stashed changes
}
