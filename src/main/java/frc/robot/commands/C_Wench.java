package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SS_Roller;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class C_Wench extends CommandBase {

    private final SS_Roller ss_Roller;
    private XboxController controller;
    private double rollerSpeed = 0.0;
    private double speed = 0.05;

    public C_Wench(SS_Roller subsystem, XboxController controller) {
        ss_Roller = subsystem;
        this.controller = controller;
        addRequirements(subsystem);
    }

    @Override
    public void execute() {
        // reset Roller speed
        rollerSpeed = 0.0;
        // using a and b button to trigger motors
        if(controller.getAButtonPressed()) {
            rollerSpeed = speed;
        } 
        if(controller.getBButtonPressed()) {
            rollerSpeed = -1 * speed;
        }
        ss_Roller.roll(rollerSpeed);
        SmartDashboard.putNumber("rollerSpeed", rollerSpeed);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
