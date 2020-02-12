package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.Constants.DriveConstants;
import frc.robot.util.IntakePosition;

public class SS_Intake extends SubsystemBase {
    private DoubleSolenoid shortSolenoid;
    private DoubleSolenoid longSolenoid;

    private CANSparkMax pickupMotor;
    
    public SS_Intake() { 
        shortSolenoid = new DoubleSolenoid(DriveConstants.SHORT_SOLENOID_FORWARD, DriveConstants.SHORT_SOLENOID_REVERSE);
        longSolenoid = new DoubleSolenoid(DriveConstants.LONG_SOLENOID_FORWARD, DriveConstants.LONG_SOLENOID_REVERSE);

        pickupMotor = new CANSparkMax(DriveConstants.powerCellPickUpMotor, CANSparkMaxLowLevel.MotorType.kBrushless);

        pickupMotor.setIdleMode(IdleMode.kBrake);
    }

    public void setArmPosition(IntakePosition position){
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

    public void setPickupMotorSpeed(double pickUpSpeed){
    	pickupMotor.set(pickUpSpeed);
    }

    public void setBrakeMode() {
        pickupMotor.setIdleMode(IdleMode.kBrake);
    }
}