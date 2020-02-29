package frc.robot;

import org.frcteam2910.common.math.Rotation2;
import org.frcteam2910.common.math.Vector2;
import org.frcteam2910.common.robot.UpdateManager;
import org.frcteam2910.common.robot.input.Controller;
import org.frcteam2910.common.robot.input.XboxController;
import org.frcteam2910.common.robot.input.DPadButton.Direction;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.commands.C_Drive;
import frc.robot.commands.C_AutoDrive;
import frc.robot.subsystems.SS_Drivebase;
import frc.robot.util.AutonomousBuilder;

public class RobotContainer {
    private final Controller driveController = new XboxController(Constants.DRIVE_CONTROLLER_ID);

    // Instantiate subsystems here
    private final SS_Drivebase drivebase = new SS_Drivebase();

    // All updatable subsystems should be passed as parameters into the
    // UpdateManager constructor
    private final UpdateManager updateManager = new UpdateManager(drivebase);

    private final AutonomousBuilder autonomousBuilder = new AutonomousBuilder(drivebase);

    public RobotContainer() {
        
        driveController.getRightXAxis().setScale(.3);

        CommandScheduler.getInstance().setDefaultCommand(drivebase,
                new C_Drive(drivebase, 
                        () -> driveController.getLeftYAxis().get(true),
                        () -> driveController.getLeftXAxis().get(true),
                        () -> driveController.getRightXAxis().get(true)));        

        updateManager.startLoop(5.0e-3);

        configureButtonBindings();
    }

    private void configureButtonBindings() {
        driveController.getBackButton().whenPressed(new InstantCommand(() -> drivebase.resetGyroAngle(Rotation2.ZERO), drivebase));
        driveController.getAButton().whenPressed(new InstantCommand(() -> drivebase.resetPose()));
        driveController.getYButton().whenPressed(new InstantCommand(() -> drivebase.getModules()[0].zeroDriveEncoder(), drivebase));
        driveController.getStartButton().whenPressed(getAutonomousCommand());
    }

    public SequentialCommandGroup getAutonomousCommand() {
        return new SequentialCommandGroup(
            new InstantCommand(() -> drivebase.resetGyroAngle(Rotation2.ZERO), drivebase),
            new C_AutoDrive(drivebase, new Vector2(-80.0, 66.0), .7, 0.0, 0.0),
            new C_AutoDrive(drivebase, new Vector2(-100.0, 0.0), .5, 0.0, 0.0)
        );
        //return autonomousBuilder.buildAutoRoutine();
    }
}
