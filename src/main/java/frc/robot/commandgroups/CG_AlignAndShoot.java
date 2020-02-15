/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commandgroups;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.C_Shoot;
import frc.robot.commands.C_Track;
import frc.robot.drivers.Vision;
import frc.robot.subsystems.SS_Drivebase;
import frc.robot.subsystems.SS_Shooter;
import frc.robot.subsystems.SS_Feeder;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class CG_AlignAndShoot extends ParallelCommandGroup {



  public CG_AlignAndShoot(Vision vision, SS_Shooter shooter, SS_Drivebase drivebase, SS_Feeder feeder) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());super();
    addCommands(new C_Shoot(vision,shooter,feeder),new C_Track(vision, drivebase, () -> 0.0, () -> 0.0));

    
  }

  @Override
  public void execute() {

  }
}


