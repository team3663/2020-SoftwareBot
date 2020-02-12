package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SS_Intake;
import frc.robot.util.ElapsedTime;
import frc.robot.util.IntakePosition;

public class C_SetArmPosition extends CommandBase {
    private IntakePosition position;
    private final SS_Intake m_intakeSubsystem;
    private final ElapsedTime elapsedTime = new ElapsedTime();

    public C_SetArmPosition(SS_Intake subsystem, IntakePosition position) {
        m_intakeSubsystem = subsystem;
        this.position = position;
        addRequirements(m_intakeSubsystem);
    }

    @Override
    public void initialize() {
        elapsedTime.reset();
    }

    @Override
    public void execute() {
        m_intakeSubsystem.setArmPosition(position);
    }
    
    @Override
    public boolean isFinished() {
        if(elapsedTime.getElapsedMillis() >= 100) {
            return true;
        }
        return false;
    }
}