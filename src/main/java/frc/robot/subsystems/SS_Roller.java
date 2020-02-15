package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class SS_Roller extends SubsystemBase{
    // for SparkMax motor controllers
    private static final CANSparkMax rollMotor = new CANSparkMax(DriveConstants.GONDOLA_SPARK_MAX_ID, MotorType.kBrushless);
    private static final CANSparkMax wenchMotor = new CANSparkMax(DriveConstants.WENCH_SPARK_MAX_ID, MotorType.kBrushless);

    public SS_Roller() {
    }

    public void roll(double rollSpeed) {
        // for SparkMax motor controllers
        rollMotor.set(rollSpeed);
    }

    public void wench(double rollSpeed) {
        // for SparkMax motor controllers
        wenchMotor.set(rollSpeed);
    }
    
}
