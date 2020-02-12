package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.commands.*;

public class OI {
    public XboxController driveController, manipController;
    Shoot shoot = new Shoot();
    DriveWithCurvature driveWithCurvature = new DriveWithCurvature();
    RunIntake runIntake = new RunIntake();
    RunConveyor runConveyor = new RunConveyor();

    JoystickButton bBtn;
    JoystickButton aBtn;
    JoystickButton lBumper;
    JoystickButton xBtn;
    public OI() {
        initControllers();

        bBtn = new JoystickButton(manipController, XboxController.Button.kB.value);
        bBtn.whileHeld(new ShootWithSensors.SpinUpShooters(2000));

        aBtn = new JoystickButton(manipController, XboxController.Button.kA.value);
        aBtn.toggleWhenPressed(new RunIntake());

        xBtn = new JoystickButton(manipController, XboxController.Button.kX.value);
        xBtn.toggleWhenPressed(new RunIntakeReverse());

        lBumper = new JoystickButton(manipController, XboxController.Button.kBumperLeft.value);
        lBumper.toggleWhenPressed(new RunConveyor());

//        Robot.drivetrain.setDefaultCommand(driveWithCurvature);
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