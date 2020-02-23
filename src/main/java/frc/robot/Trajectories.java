/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import org.frcteam2910.common.control.*;
import org.frcteam2910.common.math.*;

/**
 * Contains autonomous path and trajectory generation
 */
public class Trajectories {

    private static final double DEFAULT_SAMPLE_DISTANCE = 5.0e-1;

    //Test Autonomous Trajectory Creation
    /*
    static Path testAutoPath = new SplinePathBuilder(Vector2.ZERO, Rotation2.ZERO, Rotation2.ZERO)
                .bezier(new Vector2(10.0, 10.0), new Vector2(30.0, 20.0), new Vector2(50.0, 25.0))
                .build();
    */

    static Path testAutoPath = new SimplePathBuilder(Vector2.ZERO, Rotation2.ZERO)
                .arcTo(new Vector2(60.0, 60.0), new Vector2(25.0, 45.0)).build();

    
    static TrajectoryConstraint[] testAutoConstraints = {
        new MaxAccelerationConstraint(10.0),
        new MaxVelocityConstraint(6.0)
    };
    
    public static Trajectory testAutoTrajectory = new Trajectory(testAutoPath, testAutoConstraints, DEFAULT_SAMPLE_DISTANCE);

    //Basic drive forward trajectory
    static Path driveForwardAutoPath = new SimplePathBuilder(Vector2.ZERO, Rotation2.ZERO).lineTo(new Vector2(0, 6.0)).build();
    static TrajectoryConstraint[] driveForwardAutoConstraints = {new MaxVelocityConstraint(1.0), new MaxAccelerationConstraint(12.0)};
    
    public static Trajectory driveForwardTrajectory = new Trajectory(driveForwardAutoPath, driveForwardAutoConstraints, DEFAULT_SAMPLE_DISTANCE);

    /**
     * 
     * @param inches inches to drive forwards, negative inches will drive backwards
     * @return A drive straight trajectory
     */
    public static Trajectory driveStraightTrajectoryBuilder(double inches) {
        return new Trajectory(new SimplePathBuilder(Vector2.ZERO, Rotation2.ZERO).lineTo(new Vector2(0, inches)).build(),
                    driveForwardAutoConstraints, DEFAULT_SAMPLE_DISTANCE);
    }
}
