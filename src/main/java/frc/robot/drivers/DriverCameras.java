/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.drivers;

import java.util.HashMap;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

//Code used from: http://docs.wpilib.org/en/latest/docs/software/vision-processing/introduction/using-multiple-cameras.html

public class DriverCameras {

    UsbCamera frontCamera;
    UsbCamera backCamera;
    UsbCamera topCamera;
    HashMap<Camera, UsbCamera> cameras;
    NetworkTableEntry cameraSelection;

    public DriverCameras() {
        frontCamera = CameraServer.getInstance().startAutomaticCapture(0);
        backCamera = CameraServer.getInstance().startAutomaticCapture(1);
        topCamera = CameraServer.getInstance().startAutomaticCapture(2);

        cameras = new HashMap<Camera, UsbCamera>();
        cameras.put(Camera.FRONT, frontCamera);
        cameras.put(Camera.BACK, backCamera);
        cameras.put(Camera.TOP, topCamera);

        cameraSelection = NetworkTableInstance.getDefault().getTable("").getEntry("CameraSelection");
    }

    public enum Camera {
        FRONT,
        BACK,
        TOP
    }

    public void switchCameraFeed(Camera camera) {
        cameraSelection.setString(cameras.get(camera).getName());
    }
}
