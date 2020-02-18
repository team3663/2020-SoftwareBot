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
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.NetworkTableValue;
import edu.wpi.first.networktables.TableEntryListener;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

//Old code used from: http://docs.wpilib.org/en/latest/docs/software/vision-processing/introduction/using-multiple-cameras.html

public class DriverCameras {

    UsbCamera frontCamera;
    UsbCamera backCamera;
    UsbCamera topCamera;
    UsbCamera selectedCamera;
    HashMap<CameraPosition, UsbCamera> cameras;

    SelectorListener selectorListener; 
    SendableChooser cameraSelector;
    // NetworkTableEntry cameraSelection;

    public DriverCameras() {
        initCameras();
        initCameraSelector();
    }

    private void initCameras() {
        frontCamera = CameraServer.getInstance().startAutomaticCapture(0);
        backCamera = CameraServer.getInstance().startAutomaticCapture(1);
        topCamera = CameraServer.getInstance().startAutomaticCapture(2);
        selectedCamera = frontCamera;

        cameras = new HashMap<CameraPosition, UsbCamera>();
        cameras.put(CameraPosition.FRONT, frontCamera);
        cameras.put(CameraPosition.BACK, backCamera);
        cameras.put(CameraPosition.TOP, topCamera);

        ShuffleboardTab cameraTab = Shuffleboard.getTab("Camera");
        cameraTab.add("Video Stream", selectedCamera)
            .withWidget(BuiltInWidgets.kCameraStream)
            .withPosition(3, 0)
            .withSize(5, 5);
        // cameraSelection = NetworkTableInstance.getDefault().getTable("").getEntry("CameraSelection");
    }

    private void initCameraSelector() {
        cameraSelector.setDefaultOption("Front", CameraPosition.FRONT);
        cameraSelector.addOption("Back", CameraPosition.BACK);
        cameraSelector.addOption("Top", CameraPosition.TOP);

        ShuffleboardTab cameraTab = Shuffleboard.getTab("Camera");
        String selectorName = "SelectedCamera";
        cameraTab.add(selectorName, cameraSelector)
            .withWidget(BuiltInWidgets.kComboBoxChooser)
            .withPosition(8, 0)
            .withSize(2, 1);

        selectorListener = new SelectorListener(this);
        //Add the entry listener to the. Code from: https://github.com/wpilibsuite/allwpilib/issues/843
        NetworkTableInstance.getDefault().getTable("SmartDashboard").getSubTable(selectorName)
            .addEntryListener("selected", selectorListener, EntryListenerFlags.kUpdate);
    }

    public enum CameraPosition {
        FRONT,
        BACK,
        TOP
    }

    public void switchCameraFeed() {
        switchCameraFeed((CameraPosition)cameraSelector.getSelected());
    }

    public void switchCameraFeed(CameraPosition camera) {
        selectedCamera = cameras.get(camera);
        // cameraSelection.setString(cameras.get(camera).getName());
    }

    private class SelectorListener implements TableEntryListener {
        DriverCameras cameras;

        private SelectorListener(DriverCameras cameras) {
            this.cameras = cameras;
        }

        @Override
        public void valueChanged(NetworkTable table, String key, NetworkTableEntry entry, NetworkTableValue value, int flags) {
            cameras.switchCameraFeed();
        }
    }
}
