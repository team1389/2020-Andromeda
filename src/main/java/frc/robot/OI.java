package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.commands.DriveWithCurvature;
import frc.commands.RunConveyor;
import frc.commands.RunIntake;
import frc.commands.Shoot;

public class OI {
    public XboxController driveController, manipController;
    Shoot shoot = new Shoot();
    DriveWithCurvature driveWithCurvature = new DriveWithCurvature();
    RunIntake runIntake = new RunIntake();
    RunConveyor runConveyor = new RunConveyor();

    JoystickButton bBtn;

    public OI() {
        initControllers();

        bBtn = new JoystickButton(manipController, XboxController.Button.kB.value);
        bBtn.whenReleased(new Shoot());

        Robot.drivetrain.setDefaultCommand(driveWithCurvature);
        Robot.intake.setDefaultCommand(runIntake);
        Robot.conveyor.setDefaultCommand(runConveyor);
    }

    /**
     * Initialize JoystickButtons and Controllers
     */
    private void initControllers() {
        driveController = new XboxController(0);
        manipController = new XboxController(1);
    }

    public double driveLeftY() {
        return -driveController.getY(GenericHID.Hand.kLeft);
    }

    public double driveRightX() {
        return driveController.getX(GenericHID.Hand.kRight);
    }

    public double manipRightTrigger() {
        return manipController.getTriggerAxis(GenericHID.Hand.kRight);
    }

    public double mainpLeftTrigger() {
        return manipController.getTriggerAxis(GenericHID.Hand.kLeft);
    }


    public boolean driveLeftBumper() {
        return driveController.getBumper(GenericHID.Hand.kLeft);
    }

    public boolean manipAButton() {
        return manipController.getAButtonPressed();
    }

    public boolean manipStartButton() {
        return manipController.getStartButtonPressed();
    }

    public boolean manipBackButton() {
        return manipController.getBackButtonPressed();
    }


    public double manipLeftTrigger() {
        return manipController.getTriggerAxis(GenericHID.Hand.kLeft);
    }
}