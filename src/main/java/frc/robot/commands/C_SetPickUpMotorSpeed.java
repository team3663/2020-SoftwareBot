package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.SS_Intake;

public class C_SetPickUpMotorSpeed extends CommandBase {
    private final SS_Intake m_intakeSubsystem;
    private final XboxController m_drivController;
    private double pickUpSpeed;

    public C_SetPickUpMotorSpeed(SS_Intake subsystem) {
        m_intakeSubsystem = subsystem;
        m_drivController = Robot.getRobotContainer().getDriveController();
        addRequirements(m_intakeSubsystem);
    }

    @Override
    public void execute() {
        if(m_drivController.getRawButtonPressed(OIConstants.L_BUMPER)) {
            pickUpSpeed = 0.5;
        }
        else if(m_drivController.getRawButtonPressed(OIConstants.R_BUMPER)) {
            pickUpSpeed = -0.5;
        }
        else if(m_drivController.getRawButtonPressed(OIConstants.BUTTON_START)) {
            pickUpSpeed = 0;
        }

        m_intakeSubsystem.setPickupMotorSpeed(pickUpSpeed);
    }
    
    @Override
    public boolean isFinished() {
        return false;
    }

    //@Override
    public void end() {
        m_intakeSubsystem.setPickupMotorSpeed(0.0);
    }

    //@Override
    public void interrupted() {
        end();
    }
}