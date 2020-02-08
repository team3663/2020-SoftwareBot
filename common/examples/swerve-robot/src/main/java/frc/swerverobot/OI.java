package frc.swerverobot;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.swerverobot.subsystems.DrivetrainSubsystem;
import org.frcteam2910.common.math.Rotation2;
import org.frcteam2910.common.robot.input.Axis;
import org.frcteam2910.common.robot.input.Controller;
import org.frcteam2910.common.robot.input.XboxController;

public class OI {
   
    public static final int GAMEPAD_1 = 0;
    private Controller controller = new XboxController(GAMEPAD_1);
    public static final int R_STICK_Y_AXIS = 1;
    
    public OI() {
        controller.getLeftXAxis().setInverted(true);
        controller.getRightXAxis().setInverted(true);

        controller.getRightXAxis().setScale(0.45);
    }

    public void bindButtons() {
        getResetGyroButton().whenPressed(new InstantCommand(() ->
                DrivetrainSubsystem.getInstance().resetGyroAngle(Rotation2.ZERO)
        ));
    }

    public Axis getDriveForwardAxis() {
        return controller.getLeftYAxis();
    }

    public Axis getDriveStrafeAxis() {
        return controller.getLeftXAxis();
    }

    public Axis getDriveRotationAxis() {
        return controller.getRightXAxis();
    }

    public Button getResetGyroButton() {
        return controller.getBackButton();
    }
}
