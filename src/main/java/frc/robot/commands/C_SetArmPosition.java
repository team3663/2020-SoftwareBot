package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SS_Intake;
import frc.robot.util.IntakePosition;

public class C_SetArmPosition extends CommandBase {
    private IntakePosition position;
    private final SS_Intake m_intakeSubsystem;
    private final Timer timer = new Timer();
    private final double DURATION = 10.0;
    private double currentTime;

    public C_SetArmPosition(SS_Intake subsystem, IntakePosition position) {
        m_intakeSubsystem = subsystem;
        this.position = position;
        addRequirements(m_intakeSubsystem);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();

        currentTime = timer.get();
        SmartDashboard.putNumber("Time Between Transitions", currentTime);
        m_intakeSubsystem.setArmPosition(position);
    }

    @Override
    public boolean isFinished() {
        if(m_intakeSubsystem.getReachedLimit() || currentTime > DURATION) {
            return true;
        }
        return false;
    }
}