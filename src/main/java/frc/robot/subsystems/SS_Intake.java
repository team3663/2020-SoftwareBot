package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.RobotMap;
import frc.robot.util.IntakePosition;

public class SS_Intake extends SubsystemBase {
    private DoubleSolenoid frontSolenoid;
    private DoubleSolenoid backSolenoid;

    private CANSparkMax pickupMotor;
    private boolean closed = true;
    
    public SS_Intake() { 
        frontSolenoid = new DoubleSolenoid(RobotMap.FRONT_SOLENOID_FORWARD, RobotMap.FRONT_SOLENOID_REVERSE);
        backSolenoid = new DoubleSolenoid(RobotMap.BACK_SOLENOID_FORWARD, RobotMap.BACK_SOLENOID_REVERSE);

        pickupMotor = new CANSparkMax(RobotMap.powerCellPickUpMotor, CANSparkMaxLowLevel.MotorType.kBrushless);

        pickupMotor.setIdleMode(IdleMode.kBrake);
    }

    public void retractIntakeArm(boolean extended) {
        if(extended){
          frontSolenoid.set(DoubleSolenoid.Value.kReverse);
          backSolenoid.set(DoubleSolenoid.Value.kReverse);
          setClosed(true);
        }
    }

    public void setArmPosition(IntakePosition position){
        switch(position){
            case EXTENDED:
                frontSolenoid.set(DoubleSolenoid.Value.kForward);
                backSolenoid.set(DoubleSolenoid.Value.kForward);   
             
            case RETRACTED:
                frontSolenoid.set(DoubleSolenoid.Value.kReverse);
                backSolenoid.set(DoubleSolenoid.Value.kReverse);
            
            case HALF_RETRACT:
                frontSolenoid.set(DoubleSolenoid.Value.kReverse);
                backSolenoid.set(DoubleSolenoid.Value.kForward);
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