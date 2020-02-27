package frc.commands;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Drivetrain;

public class DriveDistance extends CommandBase {
    private double WHEEL_DIAMETER_CONSTANT = 10.3/(5*Math.PI);

    private double targetDistanceInRotations;
    private Drivetrain drivetrain;

    private CANPIDController leftPid;
    private CANPIDController rightPid;
    private double leftKP = 0.0001;
    private double leftKI = 0;
    private double leftKD = 0;

    private double rightKP = 0.0001;
    private double rightKI = 0;
    private double rightKD = 0;


    public void initialize() {
        drivetrain = Robot.drivetrain;

        leftPid = drivetrain.getLeftPidController();
        leftPid.setP(leftKP);
        leftPid.setI(leftKI);
        leftPid.setD(leftKD);


        rightPid = drivetrain.getRightPidController();
        rightPid.setP(rightKP);
        rightPid.setI(rightKI);
        rightPid.setD(rightKD);

    }

    public DriveDistance(double targetInches) {
        addRequirements(drivetrain);

        targetDistanceInRotations = targetInches*WHEEL_DIAMETER_CONSTANT;
    }

    @Override
    public void execute() {
        double leftError = targetDistanceInRotations - drivetrain.getLeftPosition();
        double rightError = targetDistanceInRotations - drivetrain.getRightPosition();

        leftPid.setReference(targetDistanceInRotations, ControlType.kPosition);
        rightPid.setReference(targetDistanceInRotations, ControlType.kPosition);

        SmartDashboard.putNumber("drive left error", leftError);
        SmartDashboard.putNumber("drive right error", rightError);
    }


}