package frc.robot.util;

/**
 * Intake arm extend positions
 */

public enum IntakePosition{
    FULLY_RETRACTED, //POSITION 0 (for putting the intake arm within the frame perimeter)
    LONG_RETRACT, //POSITION 1 (for inserting power cells into the magazine)
    SHORT_RETRACT, //POSITION 2 (for storing power cells in the intake arm)
    FULLY_EXTENDED //POSITION 3 (for intaking power cells)
}
