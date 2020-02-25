package frc.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Drivetrain;

public class DriveWithCurvature extends CommandBase {
    private Drivetrain drivetrain = null;
    private XboxController controller = Robot.oi.driveController;
    public boolean climbing;

    private boolean toggleSlowMode = false;

    public DriveWithCurvature() {
        drivetrain = Robot.drivetrain;
        addRequirements(drivetrain);
}

    @Override
    public void execute() {
        climbing = Robot.climber.isClimbing;
        if (!climbing) {
            controller = Robot.oi.driveController;
        }
        else {
            controller = Robot.oi.manipController;
        }
        //initially halved
        double throttle = controller.getY(GenericHID.Hand.kLeft)/2;
        double rotation = controller.getX(GenericHID.Hand.kRight)/2;
        boolean isQuickTurn = controller.getBumper(GenericHID.Hand.kLeft);
        boolean decreaseSpeed = controller.getAButton();
        toggleSlowMode = decreaseSpeed ^ toggleSlowMode;
        Robot.drivetrain.drive(throttle, rotation, isQuickTurn, toggleSlowMode);
    }
}
