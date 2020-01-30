package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Drivetrain;

//This commands turns the robot to a specified angle, measured in degrees
public class DriveDistance extends CommandBase {
    private double SOME_CONSTANT_INVOLVING_WHEEL_DIAMETER;

    private double targetDistance;
    private Drivetrain drivetrain = new Drivetrain();

    //We use fancy PID get on our level (To any other teams looking at our code)
    private double kP = 0.1;
    private double kI = 0.1;

    private double integral,  error, gain;

    public DriveDistance(double targetInches) {
        targetDistance = targetInches*SOME_CONSTANT_INVOLVING_WHEEL_DIAMETER;

        drivetrain = Robot.drivetrain;
        addRequirements(drivetrain);
    }

    public void PID() {
        error = targetDistance - drivetrain.leftAEncoder();
        integral += (error*.02); // Integral is increased by the error*time (which is I think  .02 seconds using normal IterativeRobot)
        gain = kP*error + kI*integral;
    }

    @Override
    public void execute() {
        PID();

        drivetrain.set(gain, gain);
    }
}
