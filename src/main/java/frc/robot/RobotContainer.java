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

import frc.robot.Constants;

public class RobotContainer {
    private final Controller driveController = new XboxController(Constants.DRIVE_CONTROLLER_ID);

    //Instantiate subsystems here

    //All updatable subsystems should be passed as parameters into the UpdateManager constructor
    private final UpdateManager updateManager = new UpdateManager();

    public RobotContainer() {
        updateManager.startLoop(5.0e-3);

        configureButtonBindings();
    }

    private void configureButtonBindings() {}
}
