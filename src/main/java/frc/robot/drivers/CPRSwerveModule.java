package frc.robot.drivers;

import com.revrobotics.CANAnalog;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANAnalog.AnalogMode;
import com.revrobotics.CANSparkMax.IdleMode;

import org.frcteam2910.common.math.Vector2;

import edu.wpi.first.wpilibj.controller.PIDController;
import frc.robot.Constants;

import org.frcteam2910.common.drivers.SwerveModule;

import static org.frcteam2910.common.robot.Constants.CAN_TIMEOUT_MS;

/**
 * Driver for Team 3663's 2020 revision of the 2910 Mk1 swerve module.
 * <p>
 * This implementation assumes that two Spark MAXs are used to control each module over CAN and that
 * the angle Spark MAX is connected to an analog encoder.
 * <p>
 * The drive distance units default to inches. This can be changed using
 * {@link CPRSwerveModule#setDriveTicksPerUnit(double)}
 */
public final class CPRSwerveModule extends SwerveModule {
    
    private static final double ANGLE_VERSA_GEAR_RATIO = 21.0; // MOTOR REVOLUTIONS TO MODULE REVOLUTIONS
    private static final double ANGLE_TOTAL_GEAR_RATIO = 63.0;

    private static final double DRIVE_GEAR_RATIO = 10.67; //2020 Final Robot 8.75, 2020 Software Bot ~10.67

    private static final double DEFAULT_DRIVE_REVOLUTIONS_PER_UNIT = .076; //in inches; theoretically .0796, accounted for wheel wear

    private final double offsetAngle;

    private final CANSparkMax angleMotor;
    private final CANSparkMax driveMotor;

    private final CANAnalog angleAbsoluteEncoder;
    private final CANEncoder angleMotorEncoder;
    private final PIDController anglePIDController;
    private final CANEncoder driveMotorEncoder;
    private final CANPIDController drivePIDController;

    private final double ANGLE_P = .5;
    private final double ANGLE_I = 0.0;
    private final double ANGLE_D = 0.0;

    private double driveEncoderPositionOffset = 0.0;



    /**
     * The amount of drive encoder ticks that occur for one unit of travel.
     *
     * @see #DEFAULT_DRIVE_REVOLUTIONS_PER_UNIT
     */
    private volatile double driveRevolutionsPerUnit = DEFAULT_DRIVE_REVOLUTIONS_PER_UNIT;

    /**
     * @param modulePosition the module's offset from the center of the robot
     * @param offsetAngle    how much to offset the angle encoder by in radians
     * @param angleMotor     the motor controller that controls the angle motor
     * @param driveMotor     the motor controller that controls the drive motor
     */
    public CPRSwerveModule(final Vector2 modulePosition, final double offsetAngle, final CANSparkMax angleMotor, final CANSparkMax driveMotor) {
        super(modulePosition);
        this.offsetAngle = offsetAngle;
        this.angleMotor = angleMotor;
        this.driveMotor = driveMotor;

        //Configure absolute encoder
        this.angleAbsoluteEncoder = new CANAnalog(this.angleMotor, AnalogMode.kAbsolute);
        this.angleAbsoluteEncoder.setInverted(false);

        //Configure angle motor encoder
        angleMotorEncoder = angleMotor.getEncoder();
        angleMotorEncoder.setPositionConversionFactor(2 * Math.PI / ANGLE_TOTAL_GEAR_RATIO);
        angleMotorEncoder.setPosition(readAngle());

        //Configure PID controllers
        anglePIDController = new PIDController(ANGLE_P, ANGLE_I, ANGLE_D);
        anglePIDController.enableContinuousInput(0, 2 * Math.PI);

        drivePIDController = this.driveMotor.getPIDController();
        drivePIDController.setOutputRange(-1.0, 1.0);


        //Config angle motor
        this.angleMotor.setIdleMode(IdleMode.kBrake);
        // Configure drive motor
        drivePIDController.setP(15);
        drivePIDController.setI(.01);
        drivePIDController.setD(.1);
        drivePIDController.setFF(.2);

        this.driveMotor.setIdleMode(IdleMode.kBrake);

        this.driveMotorEncoder = driveMotor.getEncoder();
        this.driveMotorEncoder.setVelocityConversionFactor(1.0);
        this.driveMotorEncoder.setPositionConversionFactor(1.0);

        // Setup current limiting
        this.driveMotor.setSmartCurrentLimit(Constants.DRIVE_MOTOR_AMP_LIMIT);
    }

    /**
     * Sets the amount of drive ticks per inch.
     * <p>
     * The amount of ticks per inch can be calculated by driving the robot some distance forwards (10 feet is usually
     * good) and then dividing the average module ticks by that distance.
     * <p>
     * The default value uses inches and should only be used for testing.
     *
     * @param driveRevolutionsPerUnit the amount of drive ticks that occur per unit of travel
     */
    public void setDriveTicksPerUnit(double driveRevolutionsPerUnit) {
        this.driveRevolutionsPerUnit = driveRevolutionsPerUnit;
    }

    @Override
    public double readAngle() {
        double voltage = angleAbsoluteEncoder.getVoltage() - .03; //account for dead encoder spot
        double angle = (voltage / 3.22) * 2.0 * Math.PI + offsetAngle; 
        angle %= 2.0 * Math.PI;
        if (angle < 0.0) {
            angle += 2.0 * Math.PI;
        }

        return angle;
    }

    public double getEncoderVoltage() {
        return angleAbsoluteEncoder.getPosition();
    }

    public double getIntegratedEncoderAngle() {
        return Math.toDegrees(angleMotorEncoder.getPosition());
    }

    public void resetIntegratedEncoder() {
        angleMotorEncoder.setPosition(readAngle());
    }

    public double getDriveRevs() {
        return driveMotor.getEncoder().getPosition() - driveEncoderPositionOffset;
    }

    public void zeroDriveEncoder() {
        driveEncoderPositionOffset = driveMotorEncoder.getPosition();
    }

    /**
     * @return units per second of the drive motor
     */
    @Override
    public double getCurrentVelocity() {
        return driveMotorEncoder.getVelocity() / 60.0 / DRIVE_GEAR_RATIO / driveRevolutionsPerUnit;
    }

    /**
     * @return the current RPMs of the drive motor, not the wheel
     */
    public double getDriveMotorRpms() {
        return driveMotorEncoder.getVelocity();
    }

    public CANEncoder getDriveEncoder() {
        return driveMotorEncoder;
    }

    @Override
    public double getDriveCurrent() {
        return driveMotor.getOutputCurrent();
    }

    @Override
    public double readDistance() {
        return driveMotorEncoder.getPosition() / driveRevolutionsPerUnit / DRIVE_GEAR_RATIO;
    }
    /**
     * @param angle IN RADIANS
     */
    @Override
	public void setTargetAngle(double angle) {
        //System.out.println(anglePIDController.getPositionError());
        angleMotor.set(anglePIDController.calculate(readAngle(), angle));
    }

    @Override
    public void setDriveOutput(double output) {
        driveMotor.set(output);
    }
}
