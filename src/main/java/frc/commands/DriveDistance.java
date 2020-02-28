package frc.commands;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Drivetrain;

public class DriveDistance extends CommandBase {
    private double ENCODER_COUNTS_PER_INCH = 10.3 / (5 * Math.PI);

    private double targetDistanceInEncoderCounts;
    private Drivetrain drivetrain;

    private PIDController leftPid;
    private PIDController rightPid;
    private double leftKP = 0.07;
    private double leftKI = 0;
    private double leftKD = 0;

    private double rightKP = 0.07;
    private double rightKI = 0;
    private double rightKD = 0;


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

        leftPid = new PIDController(leftKP, leftKI, leftKD);
        drivetrain.leftLeaderEncoder.setPosition(0);

        rightPid = new PIDController(rightKP, rightKI, rightKD);
        drivetrain.rightLeaderEncoder.setPosition(0);

    }

    @Override
    public void execute() {

        leftPid.setP(SmartDashboard.getNumber("left P", 0));
        leftPid.setI(SmartDashboard.getNumber("left I", 0));
        leftPid.setD(SmartDashboard.getNumber("left D", 0));

        rightPid.setP(SmartDashboard.getNumber("right P", 0));
        rightPid.setI(SmartDashboard.getNumber("right I", 0));
        rightPid.setD(SmartDashboard.getNumber("right D", 0));

        double leftPower = leftPid.calculate(drivetrain.getLeftPosition(), -targetDistanceInEncoderCounts);
        double rightPower = rightPid.calculate(-drivetrain.getRightPosition(), -targetDistanceInEncoderCounts);


        Robot.drivetrain.set(leftPower, rightPower);
        SmartDashboard.putNumber("drive left error", leftPid.getPositionError());
        SmartDashboard.putNumber("drive right error", rightPid.getPositionError());
    }


}