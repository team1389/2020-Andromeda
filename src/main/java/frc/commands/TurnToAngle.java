package frc.commands;

import edu.wpi.first.wpilibj.Timer;
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
    private double kP = 0.012;
    private double kI = 0.001;
    private double kD = 0;
    private boolean isRelativeTurn;
    private Timer timer = new Timer();

    private SizeLimitedQueue recentErrors = new SizeLimitedQueue(7);

    public TurnToAngle(double targetDegrees, boolean isRelativeTurn) {
        pid = new PIDController(kP, kI, kD);
        this.isRelativeTurn = isRelativeTurn;
        pid.enableContinuousInput(0, 360);
        targetAngle = targetDegrees;
        SmartDashboard.putNumber("kP", kP);
        SmartDashboard.putNumber("kI", kI);
        SmartDashboard.putNumber("kD", kD);


        drivetrain = Robot.drivetrain;
        addRequirements(drivetrain);


    }

    @Override
    public void initialize() {
        if (isRelativeTurn) {
            targetAngle = Robot.drivetrain.getAngle() + targetAngle;
        }
        pid.setPID(SmartDashboard.getNumber("kP", 0), SmartDashboard.getNumber("kI", 0),
                SmartDashboard.getNumber("kD", 0));

        recentErrors = new SizeLimitedQueue(7);
        timer.reset();
        timer.start();

    }

    @Override
    public void execute() {
        pid.setPID(SmartDashboard.getNumber("kP", 0),
                SmartDashboard.getNumber("kI", 0),
                SmartDashboard.getNumber("kD", 0));
        goalPower = pid.calculate(drivetrain.getAngle(), targetAngle);

        //Limit max speed (only for testing, remove later)

        SmartDashboard.putNumber("set power", goalPower);
        SmartDashboard.putNumber("turn error", pid.getPositionError());
        drivetrain.set(goalPower, -goalPower);
        System.out.println("running");
    }

    @Override
    public boolean isFinished() {
        recentErrors.addElement(pid.getPositionError());

        SmartDashboard.putNumber("average error", recentErrors.getAverage());
        return 1.4 >= Math.abs(recentErrors.getAverage()) || timer.get()>1;
    }
}
