/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//Code from limelight example: http://docs.limelightvision.io/en/latest/getting_started.html#basic-programming

package frc.robot.drivers;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision {

  //led mode constants
  public static final int LED_PIPELINE = 0; //use the LED Mode set in the current pipeline
  public static final int LED_OFF = 1; //force off
  public static final int LED_BLINK = 2; //force blink
  public static final int LED_ON = 3; //force on
  public static final int LED_DEFAULT_MODE = LED_PIPELINE;

  //camera mode constants
  public static final int CAMERA_VISION = 0; //Vision processor
  public static final int CAMERA_DRIVE = 1; //Driver Camera (Increases exposure, disables vision processing)
  public static final int CAMERA_DEFAULT_MODE = CAMERA_VISION;

  //piplines (can be a numbers from 0-9)
  public static final int COARSE_PIPELINE = 0;
  public static final int FINE_PIPELINE = 1;
  public static final int DEFAULT_PIPELINE = COARSE_PIPELINE;

  //Distance constants
  public static final double CAMERA_ANGLE = 23;
  public static final double CAMERA_HEIGHT = 26; //in inches
  public static final double TARGET_HEIGHT = 92; //in inches

  //vision network table
  private NetworkTable visionTable;
  private NetworkTableEntry tx; //Horizontal Offset From Crosshair To Target (-27 degrees to 27 degrees)
  private NetworkTableEntry ty; //Vertical Offset From Crosshair To Target (-20.5 degrees to 20.5 degrees)
  private NetworkTableEntry tv; //Whether the limelight has any valid targets (0 or 1)
  private NetworkTableEntry ta; //Target Area (0% of image to 100% of image)
  private NetworkTableEntry ts; //Skew or rotation (-90 degrees to 0 degrees)
  private NetworkTableEntry camtran; //Results of a 3D position solution, 6 numbers: Translation (x,y,y) Rotation(pitch,yaw,roll)
  
  public Vision() {
    visionTable = NetworkTableInstance.getDefault().getTable("limelight-testing");
    tx = visionTable.getEntry("tx");
    ty = visionTable.getEntry("ty");
    tv = visionTable.getEntry("tv");
    ta = visionTable.getEntry("ta");
    ts = visionTable.getEntry("ts");
    camtran = visionTable.getEntry("camtran");
    setMode(CAMERA_DEFAULT_MODE, LED_DEFAULT_MODE, DEFAULT_PIPELINE);
  }

  public void updateTelemetry() {
    //read values 
    double x = tx.getDouble(-1);
    double y = ty.getDouble(-1);
    double area = ta.getDouble(-1);
    boolean target = tv.getDouble(-1) == 1;
    double skew = ts.getDouble(-1);

    //post to smart dashboard
    SmartDashboard.putNumber("LimelightX", x);
    SmartDashboard.putNumber("LimelightY", y);
    SmartDashboard.putNumber("LimelightArea", area);
    SmartDashboard.putBoolean("Valid Target", target);
    SmartDashboard.putNumber("Skew", skew);
    SmartDashboard.putNumber("Distance", getDistance());
  }

  public void setMode(int cameraMode, int ledMode, int pipeline) { 
    if(cameraMode > -1 && cameraMode < 2) {
      visionTable.getEntry("camMode").setNumber(cameraMode);
    }
    if(ledMode > -1 && ledMode < 4) {
      visionTable.getEntry("ledMode").setNumber(ledMode);
    }
    if(pipeline > -1 && pipeline < 10) {
      visionTable.getEntry("pipeline").setNumber(pipeline);
    }
  }

  //sets the pipeline mode
  public void setPipeline(int pipeline) {
    setMode(-1, -1, pipeline);
  }

  //sets the LED mode
  public void setLEDMode(int ledMode) {
    setMode(-1, ledMode, -1);
  }

  //sets the camera mode
  public void setCameraMode(int cameraMode) {
    setMode(cameraMode, -1, -1);
  }

  //returns horizontal offset from Crosshair to target (-27 degrees to 27 degrees)
  public double getXOffset() {
    return tx.getDouble(-1);
  }

  //vertical offset from crosshair to target (-20.5 degrees to 20.5 degrees)
  public double getYOffset() {
    return ty.getDouble(-1);
  }

  //returns the rotation (-90 degrees to 0 degrees)
  public double getSkew() {
    return ts.getDouble(-1);
  }

  //return if a valid target is in view of the camera
  public boolean getValidTarget() {
    return tv.getDouble(-1) == 1;
  }

  //get an estimated distance to the target
  public double getDistance() {
    double distance = 0;
    double angleOftarget = getYOffset();
    distance = (TARGET_HEIGHT - CAMERA_HEIGHT) / Math.tan(Math.toRadians(CAMERA_ANGLE + angleOftarget));
    SmartDashboard.putNumber("calculated distance", distance / 12);
    return distance / 12; //change distance to feet
  }

  /**  
   * returns rotation of vision target for a specific axis
  */
  public double get3DRotation(Axis axis) {
    //it only continues if it is in the fine adjustment pipeline which has the higher resolution
    if(visionTable.getEntry("pipeline").getDouble(-1) != FINE_PIPELINE) {
      return 0;
    }

    double[] defaultValues = {-1, -1, -1, -1, -1, -1};
    double[] positionData = camtran.getDoubleArray(defaultValues);
    //5 is the index of the yaw axis
    switch(axis) {
      case PITCH:
        return positionData[4];
      case YAW:
        return positionData[5];
      case ROLL:
        return positionData[6];
    }
    return -1;
  }

  public enum Axis {
    PITCH,
    ROLL,
    YAW
  }
}