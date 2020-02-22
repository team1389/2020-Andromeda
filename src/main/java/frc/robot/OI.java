package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.commands.*;

import java.time.Instant;

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
    private JoystickButton lStick;

    public OI() {
        driveController = new XboxController(0);
        manipController = new XboxController(1);

        bBtn = new JoystickButton(manipController, XboxController.Button.kB.value);
        bBtn.whenHeld(new ShootWithSensors(ShootWithSensors.ShootType.Speed,200, 0));

        yBtn = new JoystickButton(manipController, XboxController.Button.kY.value);
        yBtn.whenPressed(new InstantCommand(() -> Robot.indexer.runIndexer(1)));

        rBumper = new JoystickButton(manipController, XboxController.Button.kBumperRight.value);
        rBumper.whenPressed
                (new InstantCommand(() -> Robot.intake.retractIntake()));

        aBtn = new JoystickButton(manipController, XboxController.Button.kA.value);
        aBtn.toggleWhenPressed(new RunIntake());

        xBtn = new JoystickButton(manipController, XboxController.Button.kX.value);
        xBtn.toggleWhenPressed(new InstantCommand(() -> Robot.climber.extend()));

        lBumper = new JoystickButton(manipController, XboxController.Button.kBumperLeft.value);
        lBumper.toggleWhenPressed(new RunConveyor());


        Robot.drivetrain.setDefaultCommand(driveWithCurvature);
    }



}