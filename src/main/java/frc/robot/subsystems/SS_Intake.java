package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.RobotMap;
import frc.robot.util.IntakePosition;

public class SS_Intake extends SubsystemBase {
    private DoubleSolenoid shortSolenoid;
    private DoubleSolenoid longSolenoid;

    private CANSparkMax pickupMotor;
    private boolean closed = true;
    
    public SS_Intake() { 
        shortSolenoid = new DoubleSolenoid(RobotMap.SHORT_SOLENOID_FORWARD, RobotMap.SHORT_SOLENOID_REVERSE);
        longSolenoid = new DoubleSolenoid(RobotMap.LONG_SOLENOID_FORWARD, RobotMap.LONG_SOLENOID_REVERSE);

        pickupMotor = new CANSparkMax(RobotMap.powerCellPickUpMotor, CANSparkMaxLowLevel.MotorType.kBrushless);

        pickupMotor.setIdleMode(IdleMode.kBrake);
    }

    public void retractIntakeArm(boolean extended) {
        if(extended){
          shortSolenoid.set(DoubleSolenoid.Value.kReverse);
          longSolenoid.set(DoubleSolenoid.Value.kReverse);
          setClosed(true);
        }
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

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public boolean getClosed() {
        return closed;
    }

    public void setPickupMotorSpeed(double pickUpSpeed){
    	pickupMotor.set(pickUpSpeed);
    }

    public void setBrakeMode() {
        pickupMotor.setIdleMode(IdleMode.kBrake);
    }
}