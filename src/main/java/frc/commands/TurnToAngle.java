package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Drivetrain;

//This commands turns the robot to a specified angle, measured in degrees
public class TurnToAngle extends CommandBase {
    private double targetAngle;
    private Drivetrain drivetrain = new Drivetrain();

    //We use fancy PID get on our level (To any other teams looking at our code)
    private double kP = 0.1;
    private double kI = 0.1;

    private double integral,  error, gain;

    public TurnToAngle(double targetDegrees, boolean isRelativeTurn) {
        targetAngle = targetDegrees;

        drivetrain = Robot.drivetrain;
        addRequirements(drivetrain);

        if(isRelativeTurn) {
            drivetrain.ahrs.reset();
        }
    }

    public void PID() {
        error = targetAngle - drivetrain.ahrs.getAngle();
        integral += (error*.02); // Integral is increased by the error*time (which is I think  .02 seconds using normal IterativeRobot)
        gain = kP*error + kI*integral;
    }

    @Override
    public void execute() {
        PID();

        drivetrain.set(gain, -gain);
    }
}
