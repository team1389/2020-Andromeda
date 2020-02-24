package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.commands.*;

public class OI {
    public XboxController driveController, manipController;
    DriveWithCurvature driveWithCurvature = new DriveWithCurvature();
    RunIntake runIntake = new RunIntake();
    RunConveyor runConveyor = new RunConveyor();

    public JoystickButton bBtn,aBtn,lBumper,xBtn,yBtn,rBumper,lStick, rTrigger;

    public OI() {
        driveController = new XboxController(0);
        manipController = new XboxController(1);

        //Intake Buttons
        aBtn = new JoystickButton(manipController, XboxController.Button.kA.value);
        aBtn.toggleWhenPressed(new RunIntake());

        bBtn = new JoystickButton(manipController, XboxController.Button.kB.value);
        bBtn.toggleWhenPressed(new Outtake());

        //Climber Commands
        rBumper = new JoystickButton(manipController, XboxController.Button.kBumperRight.value);
        rBumper.whenHeld
                (new WinchClimber());

        xBtn = new JoystickButton(manipController, XboxController.Button.kX.value);
        xBtn.toggleWhenPressed(new ToggleClimber());

        //Shooter Commands
        lBumper = new JoystickButton(manipController, XboxController.Button.kBumperLeft.value);
        lBumper.whenHeld(new ShootWithSensors(ShootWithSensors.ShootType.Speed, 5000,0));

        yBtn = new JoystickButton(manipController, XboxController.Button.kY.value);
        yBtn.whenHeld(new AdjustToTarget());




        Robot.drivetrain.setDefaultCommand(driveWithCurvature);

    }



}