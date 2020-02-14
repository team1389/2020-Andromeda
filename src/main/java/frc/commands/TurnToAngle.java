package frc.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Drivetrain;

//This commands turns the robot to a specified angle, measured in degrees
public class TurnToAngle extends CommandBase {
    private double targetAngle;
    private Drivetrain drivetrain = new Drivetrain();

    //We use fancy PID get on our level (To any other teams looking at our code) product integral and derivitave ladies love him
    private PIDController pid;

    private double error;
    private double goalPower;

    public TurnToAngle(double targetDegrees, boolean isRelativeTurn) {
        targetAngle = targetDegrees;

        drivetrain = Robot.drivetrain;
        addRequirements(drivetrain);

        if(isRelativeTurn) {
            drivetrain.ahrs.reset();
        }
    }

    @Override
    public void execute() {
        error = targetAngle - drivetrain.ahrs.getAngle();

        goalPower = pid.calculate(targetAngle, drivetrain.ahrs.getAngle());

        //Limit max speed (only for testing, remove later)
        goalPower = Math.max(-0.2, Math.min(0.2, goalPower));

        drivetrain.set(goalPower, -goalPower);
    }
}
