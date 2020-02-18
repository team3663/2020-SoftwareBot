package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.util.IntakePosition;

public class SS_Intake extends SubsystemBase {
    private DoubleSolenoid shortSolenoid;
    private DoubleSolenoid longSolenoid;

    private CANSparkMax pickupMotor;

    private DigitalInput pneumaticLimitSwitch;

    private IntakePosition currentPosition;
    
    public SS_Intake() { 
        shortSolenoid = new DoubleSolenoid(DriveConstants.intakeArmSolenoidModule, DriveConstants.intakeArmSolenoidPorts[0], 
                DriveConstants.intakeArmSolenoidPorts[1]);
        longSolenoid = new DoubleSolenoid(DriveConstants.intakeArmSolenoidModule, DriveConstants.intakeArmSolenoidPorts[2], 
                DriveConstants.intakeArmSolenoidPorts[3]);

        pickupMotor = new CANSparkMax(DriveConstants.powerCellPickUpMotor, CANSparkMaxLowLevel.MotorType.kBrushless);

        pneumaticLimitSwitch = new DigitalInput(IntakeConstants.SWITCH_ID);

        pickupMotor.setIdleMode(IdleMode.kBrake);
    }

    public void setArmPosition(IntakePosition position){
        SmartDashboard.putBoolean("getReachedSwitch", getReachedLimit());
        
        currentPosition = position;
        switch(position){
            case FULLY_RETRACTED:
                shortSolenoid.set(DoubleSolenoid.Value.kReverse);
                longSolenoid.set(DoubleSolenoid.Value.kReverse);
                break;

            case SHORT_RETRACT:
                shortSolenoid.set(DoubleSolenoid.Value.kReverse);
                longSolenoid.set(DoubleSolenoid.Value.kForward);
                break;
            
            case LONG_RETRACT:
                shortSolenoid.set(DoubleSolenoid.Value.kForward);
                longSolenoid.set(DoubleSolenoid.Value.kReverse);
                break;

            case FULLY_EXTENDED:
                shortSolenoid.set(DoubleSolenoid.Value.kForward);
                longSolenoid.set(DoubleSolenoid.Value.kForward);   
                break;
        }
    }

    public IntakePosition getIntakePosition() {
        return currentPosition;
    }

    public void setPickupMotorSpeed(double pickUpSpeed){
    	pickupMotor.set(pickUpSpeed);
    }

    public void setBrakeMode() {
        pickupMotor.setIdleMode(IdleMode.kBrake);
    }

    public boolean getReachedLimit() {
        return !pneumaticLimitSwitch.get();
    } 
}