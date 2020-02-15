package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SS_Intake;
import frc.robot.util.IntakePosition;

public class C_SetIntakeSpeed extends CommandBase {
    private final SS_Intake m_intakeSubsystem;
    private double intakeSpeed;
    private double speed;

    public C_SetIntakeSpeed(SS_Intake subsystem, double intakeSpeed) {
        m_intakeSubsystem = subsystem;
        this.intakeSpeed = intakeSpeed;
        addRequirements(m_intakeSubsystem);
    }

    @Override
    public void execute() {
        speed = 0.0;
        
        if(m_intakeSubsystem.getIntakePosition() == IntakePosition.FULLY_EXTENDED) {
            speed = intakeSpeed;
        }
        SmartDashboard.putNumber("Current Motor Speed", speed);
        m_intakeSubsystem.setPickupMotorSpeed(speed);
    }
    
    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        m_intakeSubsystem.setPickupMotorSpeed(0.0);
    }
}