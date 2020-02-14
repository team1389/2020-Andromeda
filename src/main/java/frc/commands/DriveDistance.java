package frc.commands;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Drivetrain;

public class DriveDistance extends CommandBase {
    private double WHEEL_DIAMETER_CONSTANT = 10.3/(5*Math.PI);

    private double targetDistance;
    private Drivetrain drivetrain = new Drivetrain();

    private PIDController pid;

    private double error;
    private double goalPower;


    public void initialize() {
        pid = new PIDController(0.1, 0, 0);
        drivetrain = Robot.drivetrain;

        drivetrain.resetEncoders();
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

        //Limit max speed (only for testing, remove later)
        drivetrain.set(goalPower, goalPower);

        System.out.println(error);
    }
}