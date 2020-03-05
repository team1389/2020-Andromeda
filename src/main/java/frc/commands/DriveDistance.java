package frc.commands;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Drivetrain;
import frc.utils.SizeLimitedQueue;

/**
 * NOTE: We're doing error calculations with Rotations and encoder ticks
 */
public class DriveDistance extends CommandBase {
    private double ENCODER_COUNTS_PER_INCH = 10.3 / (5 * Math.PI);

    private double targetDistanceInEncoderCounts;
    private Drivetrain drivetrain;

    private PIDController leftPid;
    private PIDController rightPid;
    private PIDController turnPid;
    private double leftKP = 0.07;
    private double leftKI = 0;
    private double leftKD = 0;

    private double rightKP = 0.07;
    private double rightKI = 0;
    private double rightKD = 0;

    private double gyroP = 0.005;
    private double gyroI = 0;
    private double gyroD = 0;

    double percentCap = 0.375;
    double tolerance = 0.4;


    private SizeLimitedQueue recentLeftErrors;
    private SizeLimitedQueue recentRightErrors;



    public DriveDistance(double targetInches) {
        drivetrain = Robot.drivetrain;
        addRequirements(drivetrain);

        targetDistanceInEncoderCounts = targetInches * ENCODER_COUNTS_PER_INCH;

        SmartDashboard.putNumber("left P", leftKP);
        SmartDashboard.putNumber("left I", leftKI);
        SmartDashboard.putNumber("left D", leftKD);

        SmartDashboard.putNumber("right P", rightKP);
        SmartDashboard.putNumber("right I", rightKI);
        SmartDashboard.putNumber("right D", rightKD);

    }

    public void initialize() {
        drivetrain = Robot.drivetrain;

        recentLeftErrors = new SizeLimitedQueue(7);
        recentRightErrors = new SizeLimitedQueue(7);
        leftPid = new PIDController(leftKP, leftKI, leftKD);
        drivetrain.leftLeaderEncoder.setPosition(0);

        rightPid = new PIDController(rightKP, rightKI, rightKD);
        drivetrain.rightLeaderEncoder.setPosition(0);

        turnPid = new PIDController(gyroP, gyroI, gyroD);

    }

    @Override
    public void execute() {


        double leftPower = leftPid.calculate(drivetrain.getLeftPosition() , -targetDistanceInEncoderCounts);
        double rightPower = rightPid.calculate(-drivetrain.getRightPosition(), -targetDistanceInEncoderCounts);

        double maxPower = Math.max(Math.abs(leftPower), Math.abs(rightPower));
        double scaleFactor = percentCap/maxPower;

        leftPower *= scaleFactor;
        rightPower *=scaleFactor;
        double turnPower = turnPid.calculate(drivetrain.getAngle(), 0);
        leftPower += turnPower;
        rightPower += turnPower;

        Robot.drivetrain.set(leftPower, rightPower);
        SmartDashboard.putNumber("drive left error", leftPid.getPositionError());
        SmartDashboard.putNumber("drive right error", rightPid.getPositionError());
    }

    @Override
    public boolean isFinished() {
        recentLeftErrors.addElement(leftPid.getPositionError());
        recentRightErrors.addElement(rightPid.getPositionError());
        return Math.abs(recentLeftErrors.getAverage()) < tolerance && Math.abs(recentRightErrors.getAverage()) < tolerance;
    }


}