package frc.robot;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.commands.*;

public class OI {
    public XboxController driveController, manipController;
    DriveWithCurvature driveWithCurvature = new DriveWithCurvature();

    private JoystickButton bBtn, aBtn, lBumper, yBtn, xBtn, rBumper, lTrigger;
    private JoystickButton driveYBtn;

    public OI() {
        driveController = new XboxController(0);
        manipController = new XboxController(1);

        //Intake Buttons
        aBtn = new JoystickButton(manipController, XboxController.Button.kA.value);
        aBtn.toggleWhenPressed(new RunIntakeAndSendBallToIndexer());

        bBtn = new JoystickButton(manipController, XboxController.Button.kB.value);
        bBtn.toggleWhenPressed(new Outtake());

        //Climber Commands
        rBumper = new JoystickButton(manipController, XboxController.Button.kBumperRight.value);
        rBumper.whenHeld(new WinchClimber());

        xBtn = new JoystickButton(manipController, XboxController.Button.kX.value);
        xBtn.whenPressed(new ExtendClimber());


        yBtn = new JoystickButton(manipController, XboxController.Button.kY.value);
        yBtn.whenPressed(new RetractClimber());



        //Shooter Commands
        lBumper = new JoystickButton(manipController, XboxController.Button.kBumperLeft.value);
        //3300-3500rpm is control panel shot
        lBumper.whenHeld(new DistanceShoot()); //4400
        //lBumper.whenHeld(new AdjustToTarget());
        //lBumper.whenHeld(new TestShooterSpeed());


        driveYBtn = new JoystickButton(driveController, XboxController.Button.kY.value);
        driveYBtn.whenHeld(new InstantCommand(() -> NetworkTableInstance.getDefault().getTable("limelight")
                .getEntry("ledMode").setNumber(3)));


        Robot.drivetrain.setDefaultCommand(driveWithCurvature);
        //Robot.conveyor.setDefaultCommand(new DescendConveyor());

    }


}