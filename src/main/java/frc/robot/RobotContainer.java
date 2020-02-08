/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.frcteam2910.common.math.Rotation2;
import org.frcteam2910.common.robot.UpdateManager;
import org.frcteam2910.common.robot.input.Controller;
import org.frcteam2910.common.robot.input.XboxController;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.commands.C_Shoot;
import frc.robot.drivers.Vision;
import frc.robot.subsystems.SS_Shooter;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.Constants;
import frc.robot.commands.C_Drive;
import frc.robot.subsystems.SS_Drivebase;

public class RobotContainer {
    private final Controller driveController = new XboxController(Constants.DRIVE_CONTROLLER_ID);

    //instantiate drivers
    private final Vision vision = new Vision();
    
    //Instantiate subsystems here
    private final SS_Shooter shooter = new SS_Shooter();

    //instantiate commands
    private final C_Shoot shoot = new C_Shoot(vision, shooter);
    
    
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

        configureButtonBindings();
    }

    private void configureButtonBindings() {
        driveController.getBackButton().whenPressed(new InstantCommand(() -> drivebase.resetGyroAngle(Rotation2.ZERO), drivebase));

    }

    public Command getAutonomousCommand() {
        return shoot;
    }

    public Command getTeleopCommand() {
        return shoot;
    }
}
