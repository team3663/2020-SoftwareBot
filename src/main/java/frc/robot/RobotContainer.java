/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.frcteam2910.common.robot.UpdateManager;
import org.frcteam2910.common.robot.input.Controller;
import org.frcteam2910.common.robot.input.XboxController;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.commands.C_Shoot;
import frc.robot.drivers.Vision;
import frc.robot.subsystems.SS_Shooter;

public class RobotContainer {
    private final Controller driveController = new XboxController(Constants.DRIVE_CONTROLLER_ID);

    //instantiate drivers
    private final Vision vision = new Vision();
    
    //Instantiate subsystems here
    private final SS_Shooter shooter = new SS_Shooter();

    //instantiate commands
    private final C_Shoot shoot = new C_Shoot(vision, shooter);

    //All updatable subsystems should be passed as parameters into the UpdateManager constructor
    private final UpdateManager updateManager = new UpdateManager();

    public RobotContainer() {
        updateManager.startLoop(5.0e-3);

        configureButtonBindings();
    }

    private void configureButtonBindings() {}

    public Command getAutonomousCommand() {
        return shoot;
    }

    public Command getTeleopCommand() {
        return shoot;
    }
}
