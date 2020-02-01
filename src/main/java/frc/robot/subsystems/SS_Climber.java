package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.commands.C_Roll;


public class SS_Climber extends SubsystemBase {
    private double speed = 0;
    
    public TalonSRX rollM = new TalonSRX(RobotMap.rollMotor);

    public void setRoll(double speed) {
        this.speed = speed;
        rollM.set(ControlMode.PercentOutput, speed);
    }

    //@Override
    public void initDefaultCommand() {
        setDefaultCommand(new C_Roll(speed));
    }

    public void periodic(){
        
    }

}