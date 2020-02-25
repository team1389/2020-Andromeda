package frc.commands;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Drivetrain;

public class DriveDistance extends CommandBase {
    private double WHEEL_DIAMETER_CONSTANT = 10.3/(5*Math.PI);

    private double targetDistance;
    private Drivetrain drivetrain;

    private CANPIDController leftPid;
    private CANPIDController rightPid;

    private double error;


    public void initialize() {
        drivetrain = Robot.drivetrain;

        leftPid = drivetrain.getLeftPidController();
        rightPid = drivetrain.getRightPidController();
    }

    public DriveDistance(double targetInches) {
        addRequirements(drivetrain);

        //WHEEL_DIAMETER_CONSTANT = (drivetrain.encoderCountsPerRevolution() * 3.49090909093)/(Math.PI*5); //counts per revolution * gear ratio / distance per revolution (pi * diameter)
        targetDistance = targetInches * WHEEL_DIAMETER_CONSTANT;
    }

    @Override
    public void execute() {
        error = targetDistance - drivetrain.leftLeaderEncoder();

        leftPid.setReference(targetDistance, ControlType.kVelocity);
        rightPid.setReference(targetDistance, ControlType.kVelocity);

        System.out.println(error);
    }
}