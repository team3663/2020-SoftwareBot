package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SS_Intake;
import frc.robot.util.IntakePosition;

public class C_IntakeOut extends CommandBase {
    private final SS_Intake m_intakeSubsystem;
    private final double DEFAULT_SPEED = -0.5;
    private double outakeSpeed;

    public C_IntakeOut(SS_Intake subsystem) {
        m_intakeSubsystem = subsystem;
        addRequirements(m_intakeSubsystem);
    }

    @Override
    public void execute() {
        outakeSpeed = 0.0;

        if(m_intakeSubsystem.getIntakePosition() == IntakePosition.FULLY_EXTENDED) {
            outakeSpeed = DEFAULT_SPEED;
        }

        m_intakeSubsystem.setPickupMotorSpeed(outakeSpeed);
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