package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
//import frc.robot.commands.C_Roll;


public class SS_Climber extends SubsystemBase {
    public TalonSRX rollM = new TalonSRX(RobotMap.rollMotor);

    public void setRollRight(double speed) {
        speed = 0.5;
        rollM.set(ControlMode.PercentOutput, speed);
    }

    public void setRollLeft(double speed) {
        speed = -0.5;
        rollM.set(ControlMode.PercentOutput, speed);
    }

    //@Override
    //public void initDefaultCommand() {
        //setDefaultCommand(new C_Roll());
    //}

    //@Override
    public void periodic(){
        
    }

}