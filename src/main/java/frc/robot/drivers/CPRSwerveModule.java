package frc.robot.drivers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANAnalog;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANAnalog.AnalogMode;
import com.revrobotics.CANSparkMax.IdleMode;

import org.frcteam2910.common.math.Vector2;
import org.frcteam2910.common.drivers.SwerveModule;

import static org.frcteam2910.common.robot.Constants.CAN_TIMEOUT_MS;

/**
 * Driver for the 2017 revision of the 2910 swerve module.
 * <p>
 * This implementation matches what hardware was used on both the robot we made during the 2017 off-season and what was
 * used on the 2018 competition robot. It assumes that two Talon SRXs are used to control each module over CAN and that
 * the drive Talon SRX is connected to a CIMcoder and the angle Talon SRX is connected to an analog encoder.
 * <p>
 * The drive distance units default to inches. This can be changed using
 * {@link CPRSwerveModule#setDriveTicksPerUnit(double)}
 */
public final class CPRSwerveModule extends SwerveModule {
    /**
     * How many angle encoder ticks occur for one radian travel.
     * <p>
     * The angle encoder travels 1 to 1 with the module so one rotation of the angle encoder is one rotation of the
     * module. A Talon SRX gives us 1024 ticks per rotation for an analog encoder so we can divide that by 2&pi; to get
     * the ticks per radian.
     */
    private static final double ANGLE_TICKS_PER_RADIAN = (1024.0 / (2.0 * Math.PI));

    /**
     * The default amount of drive encoder ticks for one unit of travel.
     * <p>
     * This value was taken from our 2018 robot.
     */
    private static final double DEFAULT_DRIVE_TICKS_PER_UNIT = 36.65; //TODO: find drive ticks per inch with a NEO drive motor

    private final double offsetAngle;

    private final CANSparkMax angleMotor;
    private final CANSparkMax driveMotor;

    private final CANAnalog angleEncoder;

    /**
     * The amount of drive encoder ticks that occur for one unit of travel.
     *
     * @see #DEFAULT_DRIVE_TICKS_PER_UNIT
     */
    private volatile double driveTicksPerUnit = DEFAULT_DRIVE_TICKS_PER_UNIT;

    /**
     * @param modulePosition the module's offset from the center of the robot
     * @param offsetAngle    how much to offset the angle encoder by in radians
     * @param angleMotor     the motor controller that controls the angle motor
     * @param driveMotor     the motor controller that controls the drive motor
     */
    public CPRSwerveModule(Vector2 modulePosition, double offsetAngle, CANSparkMax angleMotor, CANSparkMax driveMotor) {
        super(modulePosition);
        this.offsetAngle = offsetAngle;
        this.angleMotor = angleMotor;
        this.driveMotor = driveMotor;


        //Config angle motor TODO: Tune PID        
        this.angleMotor.getPIDController().setP(0);//was 30 originally
        this.angleMotor.getPIDController().setI(0);//was .001
        this.angleMotor.getPIDController().setD(0);//was 300
        this.angleMotor.getPIDController().setFF(0);//was 0
        this.angleMotor.setIdleMode(IdleMode.kBrake);

        // Configure drive motor
        this.driveMotor.getPIDController().setP(15);
        this.driveMotor.getPIDController().setI(.01);
        this.driveMotor.getPIDController().setD(.1);
        this.driveMotor.getPIDController().setFF(.2);

        this.driveMotor.setIdleMode(IdleMode.kBrake);

        // Setup current limiting
        this.driveMotor.setSmartCurrentLimit(25);

        //Configure absolute encoder
        this.angleEncoder = new CANAnalog(this.angleMotor, AnalogMode.kAbsolute);
        this.angleEncoder.setInverted(false);
    }

    /**
     * Sets the amount of drive ticks per inch.
     * <p>
     * The amount of ticks per inch can be calculated by driving the robot some distance forwards (10 feet is usually
     * good) and then dividing the average module ticks by that distance.
     * <p>
     * The default value uses inches and should only be used for testing.
     *
     * @param driveTicksPerUnit the amount of drive ticks that occur per unit of travel
     */
    public void setDriveTicksPerUnit(double driveTicksPerUnit) {
        this.driveTicksPerUnit = driveTicksPerUnit;
    }

    @Override
    protected double readAngle() {
        

        double angle = angleEncoder.getPosition() / ANGLE_TICKS_PER_RADIAN + offsetAngle;
        angle %= 2.0 * Math.PI;
        if (angle < 0.0) {
            angle += 2.0 * Math.PI;
        }

        return angle;
    }

    @Override
    public double readDistance() {
        return driveMotor.getEncoder().getPosition() / driveTicksPerUnit;
    }
    /**
     * @param angle IN RADIANS
     */
    @Override
	public void setTargetAngle(double angle) {
        angleMotor.getPIDController().setReference(angle * ANGLE_TICKS_PER_RADIAN, ControlType.kPosition);
    }

    @Override
    public void setDriveOutput(double output) {
        driveMotor.set(output);
    }
}
