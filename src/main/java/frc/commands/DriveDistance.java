package frc.command;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Drivetrain;

//This commands turns the robot to a specified angle, measured in degrees
public class DriveDistance extends CommandBase {
    private double WHEEL_DIAMETER_CONSTANT = 10.3/(5*Math.PI);

    private double targetDistance;
    private Drivetrain drivetrain = new Drivetrain();

    //We use fancy PID get on our level (To any other teams looking at our code) bruh they all do
    private PIDController pid;

    private double error;
    private double goalPower;


    public void initialize() {
        pid = new PIDController(0.1, 0, 0);
        drivetrain = Robot.drivetrain;

        drivetrain.resetEncoders();
        System.out.println("init");
    }

    public DriveDistance(double targetInches) {
        addRequirements(drivetrain);

        //WHEEL_DIAMETER_CONSTANT = (drivetrain.encoderCountsPerRevolution() * 3.49090909093)/(Math.PI*5); //counts per revolution * gear ratio / distance per revolution (pi * diameter)
        targetDistance = targetInches*WHEEL_DIAMETER_CONSTANT;
    }

    @Override
    public void execute() {
        error = targetDistance - drivetrain.leftLeaderEncoder();
        goalPower = pid.calculate(drivetrain.leftLeaderEncoder(), targetDistance);

        goalPower = Math.max(-0.2, Math.min(0.2, goalPower));

        //Limit max speed (only for testing, remove late)
        drivetrain.set(goalPower, goalPower);

        System.out.println(error);
    }
}