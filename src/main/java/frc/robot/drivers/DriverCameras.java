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
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

//Code used from: http://docs.wpilib.org/en/latest/docs/software/vision-processing/introduction/using-multiple-cameras.html

public class DriverCameras {

    UsbCamera frontCamera;
    UsbCamera backCamera;
    UsbCamera topCamera;
    HashMap<CameraPosition, UsbCamera> cameras;
    NetworkTableEntry cameraSelection;

    public DriverCameras() {
        frontCamera = CameraServer.getInstance().startAutomaticCapture(0);
        backCamera = CameraServer.getInstance().startAutomaticCapture(1);
        topCamera = CameraServer.getInstance().startAutomaticCapture(2);

        cameras = new HashMap<CameraPosition, UsbCamera>();
        cameras.put(CameraPosition.FRONT, frontCamera);
        cameras.put(CameraPosition.BACK, backCamera);
        cameras.put(CameraPosition.TOP, topCamera);

        ShuffleboardTab cameraTab = Shuffleboard.getTab("Camera");
        cameraTab.add("VideoStream", frontCamera)
            .withWidget(BuiltInWidgets.kCameraStream)
            .withPosition(4, 0)
            .withSize(5, 5);

        cameraSelection = NetworkTableInstance.getDefault().getTable("").getEntry("CameraSelection");
    }

    public enum CameraPosition {
        FRONT,
        BACK,
        TOP
    }

    public void switchCameraFeed(CameraPosition camera) {
        cameraSelection.setString(cameras.get(camera).getName());
    }
}
