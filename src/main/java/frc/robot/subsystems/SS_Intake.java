package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.RobotMap;

public class SS_Intake extends SubsystemBase {
    private DoubleSolenoid intakeArm;
    private CANSparkMax pickupMotor;
    private DigitalInput intakeSwitch;
    private boolean closed = true;
    
    public SS_Intake() { 
        intakeArm = new DoubleSolenoid(RobotMap.INTAKEARM_SOLENOID_FORWARD, RobotMap.INTAKEARM_SOLENOID_REVERSE);
        pickupMotor = new CANSparkMax(RobotMap.powerCellPickUpMotor, CANSparkMaxLowLevel.MotorType.kBrushless);
        intakeSwitch = new DigitalInput(RobotMap.INTAKE_SWITCH);

        pickupMotor.setIdleMode(IdleMode.kBrake);
    }

    public void retractIntakeArm(boolean extended) {
        if(extended){
          intakeArm.set(DoubleSolenoid.Value.kReverse);
          setClosed(true);
        }else{
          intakeArm.set(DoubleSolenoid.Value.kForward);
          setClosed(false);
        }
    }

    public void setArmPosition(boolean extended){
        if(extended){
            intakeArm.set(DoubleSolenoid.Value.kForward);
        }
        else{
            intakeArm.set(DoubleSolenoid.Value.kReverse);
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

    public DigitalInput getIntakeSwitch() {
        return intakeSwitch;
    }

    public boolean isPhysicalPresent() {
        return !intakeSwitch.get();
    } 
}