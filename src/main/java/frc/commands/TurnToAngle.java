package frc.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Drivetrain;

//This commands turns the robot to a specified angle, measured in degrees
public class TurnToAngle extends CommandBase {
    private double targetAngle;
    private Drivetrain drivetrain = new Drivetrain();

    //We use fancy PID get on our level (To any other teams looking at our code)
    private PIDController pid;

    private double error;

    public TurnToAngle(double targetDegrees, boolean isRelativeTurn) {
        targetAngle = targetDegrees;

        drivetrain = Robot.drivetrain;
        addRequirements(drivetrain);

        if(isRelativeTurn) {
            drivetrain.ahrs.reset();
        }
    }

    public void PID() {
    }

    @Override
    public void execute() {
        error = targetAngle - drivetrain.ahrs.getAngle();

        drivetrain.set(pid.calculate(error, targetAngle), -pid.calculate(error, targetAngle));
    }
}
