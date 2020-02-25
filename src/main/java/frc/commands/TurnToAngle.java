package frc.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Drivetrain;
import frc.utils.SizeLimitedQueue;

//This commands turns the robot to a specified angle, measured in degrees
public class TurnToAngle extends CommandBase {

    private double targetAngle;
    private Drivetrain drivetrain;
    private PIDController pid;
    private double goalPower;

    private SizeLimitedQueue recentErrors = new SizeLimitedQueue(7);

    public TurnToAngle(double targetDegrees, boolean isRelativeTurn) {
        pid = new PIDController(0.1, 0, 0);

        targetAngle = targetDegrees;

        drivetrain = Robot.drivetrain;
        addRequirements(drivetrain);

        if(isRelativeTurn) {
            targetAngle = Robot.drivetrain.getAngle() + targetDegrees;
        }
    }

    @Override
    public void execute() {
        goalPower = pid.calculate(targetAngle, drivetrain.getAngle());

        //Limit max speed (only for testing, remove later)
        goalPower = Math.max(-0.2, Math.min(0.2, goalPower));

        drivetrain.set(-goalPower, goalPower);
    }

    @Override
    public boolean isFinished() {
        recentErrors.addElement(drivetrain.getAngle()-targetAngle);

        SmartDashboard.putNumber("average error", recentErrors.getAverage());
        return 1 >= Math.abs(recentErrors.getAverage());
    }
}
