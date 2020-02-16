package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SS_Roller;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class C_ButtonRoll extends CommandBase{

    private final SS_Roller ss_Roller;
    private double speed = 0.0;

    public C_ButtonRoll(SS_Roller subsystem, double speed) {
        ss_Roller = subsystem;
        this.speed = speed;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        ss_Roller.roll(speed);
        SmartDashboard.putNumber("Roller Speed", speed);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        ss_Roller.roll(0);
        SmartDashboard.putNumber("Roller Speed", speed);        
    }

}