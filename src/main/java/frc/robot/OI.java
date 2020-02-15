package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.commands.*;

public class OI {
    public XboxController driveController, manipController;
    DriveWithCurvature driveWithCurvature = new DriveWithCurvature();
    RunIntake runIntake = new RunIntake();
    RunConveyor runConveyor = new RunConveyor();

    private JoystickButton bBtn;
    private JoystickButton aBtn;
    private JoystickButton lBumper;
    private JoystickButton xBtn;
    private JoystickButton yBtn;
    private JoystickButton rBumper;

    public OI() {
        driveController = new XboxController(0);
        manipController = new XboxController(1);

        yBtn = new JoystickButton(manipController, XboxController.Button.kY.value);
        yBtn.whenPressed(new ShootWithSensors.SendBallToIndexer());

        rBumper = new JoystickButton(manipController, XboxController.Button.kBumperRight.value);
        rBumper.whenPressed(new ShootWithSensors.SendBallToShooter());

        bBtn = new JoystickButton(manipController, XboxController.Button.kB.value);
        bBtn.whileHeld(new ShootWithSensors(100));


        aBtn = new JoystickButton(manipController, XboxController.Button.kA.value);
        aBtn.toggleWhenPressed(new RunIntake());

        xBtn = new JoystickButton(manipController, XboxController.Button.kX.value);
        xBtn.toggleWhenPressed(new RunIntakeReverse());

        lBumper = new JoystickButton(manipController, XboxController.Button.kBumperLeft.value);
        lBumper.toggleWhenPressed(new RunConveyor());

//        Robot.drivetrain.setDefaultCommand(driveWithCurvature);
    }



}