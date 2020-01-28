package frc.robot;

import org.frcteam2910.common.robot.input.XboxController;
import frc.robot.commands.C_SetArmPosition;
import frc.robot.commands.C_SetPickUpMotorSpeed;
import frc.robot.commands.commandgroups.CG_Roomba;

public class OI {
    private XboxController driveController = new XboxController(0);
    //private Robot mRobot;

    public OI() {
        //mRobot = Robot;
    }

    public void registerControls() {
        driveController.getAButton().whenPressed(new C_SetArmPosition(true));
        driveController.getBButton().whenPressed(new C_SetArmPosition(false));
        driveController.getXButton().whileHeld(new C_SetPickUpMotorSpeed(0.5));
        driveController.getYButton().whileHeld(new C_SetPickUpMotorSpeed(-0.5));
        driveController.getRightBumperButton().whileHeld(new CG_Roomba());
    }
}