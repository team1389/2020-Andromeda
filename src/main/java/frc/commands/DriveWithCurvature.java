package frc.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Drivetrain;

public class DriveWithCurvature extends CommandBase {
    private Drivetrain drivetrain = null;

    public DriveWithCurvature() {
        drivetrain = Robot.drivetrain;
    addRequirements(drivetrain);
}

    @Override
    public void execute() {
        //initially halved
        double throttle = Robot.oi.driveController.getY(GenericHID.Hand.kLeft)/3;
        double rotation = Robot.oi.driveController.getX(GenericHID.Hand.kRight)/3;
        boolean isQuickTurn = Robot.oi.driveController.getBumper(GenericHID.Hand.kLeft);
        System.out.println("Jebediah is having fun driving around!");
        Robot.drivetrain.drive(throttle, rotation, isQuickTurn);
    }
}
