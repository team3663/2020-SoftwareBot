/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import javax.annotation.concurrent.GuardedBy;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.shuffleboard.*;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SerialPort.Port;
import frc.robot.commands.*;
import frc.robot.drivers.*;
import frc.robot.RobotMap;

import org.frcteam2910.common.math.*;
import org.frcteam2910.common.util.HolonomicDriveSignal;
import org.frcteam2910.common.drivers.*;
import org.frcteam2910.common.kinematics.ChassisVelocity;
import org.frcteam2910.common.kinematics.SwerveKinematics;
import org.frcteam2910.common.kinematics.SwerveOdometry;
import org.frcteam2910.common.robot.UpdateManager;

/**
 * Add your docs here.
 */
public class SS_Drivebase extends Subsystem implements UpdateManager.Updatable{

  private static final SS_Drivebase instance;

  private final CPRSwerveModule frontLeftModule = new CPRSwerveModule(new Vector2(RobotMap.TRACKWIDTH / 2.0, -RobotMap.WHEELBASE / 2.0), 
  RobotMap.FRONT_LEFT_MODULE_OFFSET, new TalonSRX(RobotMap.FRONT_LEFT_ANGLE_MOTOR), 
  new CANSparkMax(RobotMap.FRONT_LEFT_DRIVE_MOTOR, MotorType.kBrushless));

  private final CPRSwerveModule frontRightModule = new CPRSwerveModule(new Vector2(RobotMap.TRACKWIDTH / 2.0, RobotMap.WHEELBASE / 2.0), 
  RobotMap.FRONT_RIGHT_MODULE_OFFSET, new TalonSRX(RobotMap.FRONT_RIGHT_ANGLE_MOTOR), 
  new CANSparkMax(RobotMap.FRONT_RIGHT_DRIVE_MOTOR, MotorType.kBrushless));

  private final CPRSwerveModule backLeftModule = new CPRSwerveModule(new Vector2(-RobotMap.TRACKWIDTH / 2.0, -RobotMap.WHEELBASE / 2.0), 
  RobotMap.BACK_LEFT_MODULE_OFFSET, new TalonSRX(RobotMap.BACK_LEFT_ANGLE_MOTOR), 
  new CANSparkMax(RobotMap.BACK_LEFT_DRIVE_MOTOR, MotorType.kBrushless));

  private final CPRSwerveModule backRightModule = new CPRSwerveModule(new Vector2(-RobotMap.TRACKWIDTH / 2.0, RobotMap.WHEELBASE / 2.0), 
  RobotMap.BACK_RIGHT_MODULE_OFFSET, new TalonSRX(RobotMap.BACK_RIGHT_ANGLE_MOTOR), 
  new CANSparkMax(RobotMap.BACK_RIGHT_DRIVE_MOTOR, MotorType.kBrushless));

  private final SwerveModule[] modules = {frontLeftModule, frontRightModule, backLeftModule, backRightModule};

  private final SwerveKinematics kinematics = new SwerveKinematics(
            new Vector2(RobotMap.TRACKWIDTH / 2.0, RobotMap.WHEELBASE / 2.0), // Front Left
            new Vector2(RobotMap.TRACKWIDTH / 2.0, -RobotMap.WHEELBASE / 2.0), // Front Right
            new Vector2(-RobotMap.TRACKWIDTH / 2.0, RobotMap.WHEELBASE / 2.0), // Back Left
            new Vector2(-RobotMap.TRACKWIDTH / 2.0, -RobotMap.WHEELBASE / 2.0) // Back Right
    );
  
  private final SwerveOdometry odometry = new SwerveOdometry(kinematics, RigidTransform2.ZERO);

  private final Object sensorLock = new Object();
  @GuardedBy("sensorLock")
  private final NavX navX = new NavX(Port.kUSB, RobotMap.NAVX_UPDATE_RATE);

  private final Object kinematicsLock = new Object();
  @GuardedBy("kinematicsLock")
  private RigidTransform2 pose = RigidTransform2.ZERO;

  private final Object stateLock = new Object();
  @GuardedBy("stateLock")
  private HolonomicDriveSignal driveSignal = null;

  private NetworkTableEntry poseXEntry;
  private NetworkTableEntry poseYEntry;
  private NetworkTableEntry poseAngleEntry;

  private NetworkTableEntry[] moduleAngleEntries = new NetworkTableEntry[modules.length];

  static {
    instance = new SS_Drivebase();
}
    
  public SS_Drivebase() {
    synchronized (sensorLock) {
      navX.setInverted(true);
    }

  ShuffleboardTab tab = Shuffleboard.getTab("Drivetrain");
  poseXEntry = tab.add("Pose X", 0.0)
          .withPosition(0, 0)
          .withSize(1, 1)
          .getEntry();
  poseYEntry = tab.add("Pose Y", 0.0)
          .withPosition(0, 1)
          .withSize(1, 1)
          .getEntry();
  poseAngleEntry = tab.add("Pose Angle", 0.0)
          .withPosition(0, 2)
          .withSize(1, 1)
          .getEntry();

  ShuffleboardLayout frontLeftModuleContainer = tab.getLayout("Front Left Module", BuiltInLayouts.kList)
          .withPosition(1, 0)
          .withSize(2, 3);
  moduleAngleEntries[0] = frontLeftModuleContainer.add("Angle", 0.0).getEntry();

  ShuffleboardLayout frontRightModuleContainer = tab.getLayout("Front Right Module", BuiltInLayouts.kList)
          .withPosition(3, 0)
          .withSize(2, 3);
  moduleAngleEntries[1] = frontRightModuleContainer.add("Angle", 0.0).getEntry();

  ShuffleboardLayout backLeftModuleContainer = tab.getLayout("Back Left Module", BuiltInLayouts.kList)
          .withPosition(5, 0)
          .withSize(2, 3);
  moduleAngleEntries[2] = backLeftModuleContainer.add("Angle", 0.0).getEntry();

  ShuffleboardLayout backRightModuleContainer = tab.getLayout("Back Right Module", BuiltInLayouts.kList)
          .withPosition(7, 0)
          .withSize(2, 3);
  moduleAngleEntries[3] = backRightModuleContainer.add("Angle", 0.0).getEntry();
  }

  public static SS_Drivebase getInstance() {
    return instance;
  }

  public RigidTransform2 getPose() {
    synchronized (kinematicsLock) {
        return pose;
    }
}

public void drive(Vector2 translationalVelocity, double rotationalVelocity, boolean fieldOriented) {
    synchronized (stateLock) {
        driveSignal = new HolonomicDriveSignal(translationalVelocity, rotationalVelocity, fieldOriented);
    }
}

public void resetGyroAngle(Rotation2 angle) {
    synchronized (sensorLock) {
        navX.setAdjustmentAngle(
                navX.getUnadjustedAngle().rotateBy(angle.inverse())
        );
    }
}

@Override
    public void update(double timestamp, double dt) {
        updateOdometry(dt);

        HolonomicDriveSignal driveSignal;
        synchronized (stateLock) {
            driveSignal = this.driveSignal;
        }

        updateModules(driveSignal, dt);
    }

    private void updateOdometry(double dt) {
        Vector2[] moduleVelocities = new Vector2[modules.length];
        for (int i = 0; i < modules.length; i++) {
            var module = modules[i];
            module.updateSensors();

            moduleVelocities[i] = Vector2.fromAngle(Rotation2.fromRadians(module.getCurrentAngle())).scale(module.getCurrentVelocity());
        }

        Rotation2 angle;
        synchronized (sensorLock) {
            angle = navX.getAngle();
        }

        RigidTransform2 pose = odometry.update(angle, dt, moduleVelocities);

        synchronized (kinematicsLock) {
            this.pose = pose;
        }
    }

    private void updateModules(HolonomicDriveSignal signal, double dt) {
      ChassisVelocity velocity;
      if (signal == null) {
          velocity = new ChassisVelocity(Vector2.ZERO, 0.0);
      } else if (signal.isFieldOriented()) {
          velocity = new ChassisVelocity(
                  signal.getTranslation().rotateBy(getPose().rotation.inverse()),
                  signal.getRotation()
          );
      } else {
          velocity = new ChassisVelocity(signal.getTranslation(), signal.getRotation());
      }

      Vector2[] moduleOutputs = kinematics.toModuleVelocities(velocity);
      SwerveKinematics.normalizeModuleVelocities(moduleOutputs, 1.0);

      for (int i = 0; i < modules.length; i++) {
          var module = modules[i];
          module.setTargetVelocity(moduleOutputs[i]);
          module.updateState(dt);
      }
  }



  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new C_Drive());
  }

  @Override
    public void periodic() {
        var pose = getPose();
        poseXEntry.setDouble(pose.translation.x);
        poseYEntry.setDouble(pose.translation.y);
        poseAngleEntry.setDouble(pose.rotation.toDegrees());

        for (int i = 0; i < modules.length; i++) {
            var module = modules[i];
            moduleAngleEntries[i].setDouble(Math.toDegrees(module.getCurrentAngle()));
        }
    }
}
