/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.util;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

public class AutoSelector {

    private SendableChooser startingPositionSelector;
    private SendableChooser movementSelector;
    private NetworkTableEntry shootDelayEntry;
    private NetworkTableEntry movementDelayEntry;
    
    public AutoSelector() {
        initStartingPositionSelector();
        initMovementSelector();

        ShuffleboardTab autoTab = Shuffleboard.getTab("Auto Selector");

        autoTab.add("Starting Position", startingPositionSelector)
            .withWidget(BuiltInWidgets.kComboBoxChooser)
            .withPosition(0, 0)
            .withSize(2, 0);
        shootDelayEntry = autoTab.add("Shoot Delay", 0)
            .withWidget(BuiltInWidgets.kTextView)
            .withPosition(0, 1)
            .withSize(2, 1)
            .getEntry();
        movementDelayEntry = autoTab.add("Movement Delay", 0)
            .withWidget(BuiltInWidgets.kTextView)
            .withPosition(0, 2)
            .withSize(2, 1)
            .getEntry();
        autoTab.add("Movement Strategy", movementSelector)
            .withWidget(BuiltInWidgets.kComboBoxChooser)
            .withPosition(0, 3)
            .withSize(1, 2);
    }

    private void initStartingPositionSelector() {
        startingPositionSelector = new SendableChooser<StartingPosition>();
        //Add options to the starting position selector
        startingPositionSelector.setDefaultOption("Middle", StartingPosition.MIDDLE);
        startingPositionSelector.addOption("Left", StartingPosition.LEFT);
        startingPositionSelector.addOption("Right", StartingPosition.RIGHT);
    }

    private void initMovementSelector() {
        movementSelector.setDefaultOption("Backward", MovementStrategy.BACKWARD);
        movementSelector.addOption("Forward", MovementStrategy.FORWARD);
        movementSelector.addOption("Trench", MovementStrategy.TRENCH);
        movementSelector.addOption("None", MovementStrategy.NONE);
    }

    /**
     * @return true if the robot should shoot from the line
     */
    public boolean getShootFromLine() {
        //return shootFromLineEntry.getBoolean(false);
        return false;
    }

    public StartingPosition getStartingPosition() {
        return (StartingPosition)startingPositionSelector.getSelected();
    }

    public enum StartingPosition {
        LEFT,
        MIDDLE,
        RIGHT
    }

    public enum MovementStrategy {
        FORWARD,
        BACKWARD,
        TRENCH,
        NONE
    }
}
