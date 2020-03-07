package frc.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Drivetrain;

public class DriveWithCurvature extends CommandBase {

    private Drivetrain drivetrain;
    private boolean toggleSlowMode = false;

    public DriveWithCurvature() {
        drivetrain = Robot.drivetrain;
        addRequirements(drivetrain);
}

    @Override
    public void execute() {
        //initially halved
        double throttle = Robot.oi.driveController.getY(GenericHID.Hand.kLeft)*0.5;
        double rotation = Robot.oi.driveController.getX(GenericHID.Hand.kRight)*0.5;
        boolean isQuickTurn = Robot.oi.driveController.getBumper(GenericHID.Hand.kLeft);
        boolean decreaseSpeed = Robot.oi.driveController.getAButtonPressed();
        toggleSlowMode = decreaseSpeed ^ toggleSlowMode;
        Robot.drivetrain.drive(throttle, rotation, isQuickTurn, toggleSlowMode);
    }
}
