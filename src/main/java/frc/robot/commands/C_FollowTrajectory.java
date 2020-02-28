/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import java.util.Optional;

import org.frcteam2910.common.control.HolonomicMotionProfiledTrajectoryFollower;
import org.frcteam2910.common.control.PidConstants;
import org.frcteam2910.common.control.Trajectory;
import org.frcteam2910.common.math.Vector2;
import org.frcteam2910.common.util.DrivetrainFeedforwardConstants;
import org.frcteam2910.common.util.HolonomicDriveSignal;
import org.frcteam2910.common.util.HolonomicFeedforward;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.SS_Drivebase;

public class C_FollowTrajectory extends CommandBase {

  private PidConstants trajectoryTranslationConstants = new PidConstants(0, 0, 0);
  private PidConstants trajectoryRotationConstants = new PidConstants(0, 0, 0);

  private SS_Drivebase drivebase;
  private Trajectory trajectory;
  private Optional<HolonomicDriveSignal> driveSignal;
  private HolonomicMotionProfiledTrajectoryFollower trajectoryFollower = 
              new HolonomicMotionProfiledTrajectoryFollower(trajectoryTranslationConstants, trajectoryRotationConstants, 
              new HolonomicFeedforward(new DrivetrainFeedforwardConstants(0, 0, 0)));
              
  public C_FollowTrajectory(Trajectory trajectory, SS_Drivebase drivebase) {
    this.drivebase = drivebase;
    this.trajectory = trajectory;
    addRequirements(drivebase);
  }

  @Override
  public void initialize() {
    trajectoryFollower.follow(trajectory);
  }

  @Override
  public void execute() {
    driveSignal = trajectoryFollower.update(drivebase.getPose(), drivebase.getKinematicVelocity().getTranslationalVelocity(),
                drivebase.getGyro().getRate(), drivebase.getCurrentTime(), drivebase.getDeltaTime());
    drivebase.drive(driveSignal.orElse(new HolonomicDriveSignal(Vector2.ZERO, 0.0, false)));
  }

  @Override
  public void end(boolean interrupted) {
    if (interrupted) {
      trajectoryFollower.cancel();
    }
    drivebase.drive(new HolonomicDriveSignal(Vector2.ZERO, 0.0, false));
  }

  @Override
  public boolean isFinished() {
    return trajectoryFollower.getCurrentTrajectory().isEmpty();
  }
}
