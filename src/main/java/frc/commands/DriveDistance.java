package frc.commands;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Drivetrain;

//This commands turns the robot to a specified angle, measured in degrees
public class DriveDistance extends CommandBase {
    private double WHEEL_DIAMETER_CONSTANT;

    private double targetDistance;
    private Drivetrain drivetrain = new Drivetrain();

    //We use fancy PID get on our level (To any other teams looking at our code) bruh they all do
    private PIDController pid;

    private double error;


    public void initialize() {
        pid = new PIDController(0.1, 0.1, 0);
    }
    public DriveDistance(double targetInches) {
        drivetrain = Robot.drivetrain;
        addRequirements(drivetrain);

        WHEEL_DIAMETER_CONSTANT = (drivetrain.encoderCountsPerRevolution() * 3.49090909093)/(Math.PI*5); //counts per revolution * gear ratio / distance per revolution (pi * diameter)
        targetDistance = targetInches*WHEEL_DIAMETER_CONSTANT;
    }

    @Override
    public void execute() {
        error = targetDistance - drivetrain.leftLeaderEncoder();

        drivetrain.set(pid.calculate(error, targetDistance), pid.calculate(error, targetDistance));
    }
}