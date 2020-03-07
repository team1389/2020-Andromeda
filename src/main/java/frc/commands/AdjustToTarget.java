package frc.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Drivetrain;
import frc.utils.SizeLimitedQueue;

public class AdjustToTarget extends CommandBase {
    private Drivetrain drivetrain;

    private SizeLimitedQueue recentRotations = new SizeLimitedQueue(14);

    private double integral, derivative, previousRotationError, goalRotationPower, currentTX;
    private double goalLeftPower, goalRightPower;

    private double DRIVE_ROTATION_P = 0.01;
    private double DRIVE_ROTATION_I = 0.0073;//0.05
    private double DRIVE_ROTATION_D = 0;

    private double runTime = 3;

    private Timer timer = new Timer();

    //Limelight values
    private double tv, tx, ty, ta;

    public AdjustToTarget() {
        drivetrain = Robot.drivetrain;
        addRequirements(drivetrain);

    }

    @Override
    public void initialize() {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);

        SmartDashboard.putNumber("P Constant", DRIVE_ROTATION_P);
        SmartDashboard.putNumber("I Constant", DRIVE_ROTATION_I);
        SmartDashboard.putNumber("D Constant", DRIVE_ROTATION_D);

        tv = 0;
        tx = 0;
        ty = 0;
        ta = 0;
        recentRotations = new SizeLimitedQueue(14);
        timer.reset();
        timer.start();

    }

    @Override
    public void execute() {
        fetchValues();

        goalLeftPower = 0;
        goalRightPower = 0;

        //Only run if the target is in view
        if (tv >= 1) {
            rotateAlign();
            System.out.print("rotating");

            //Clip the speed while testing, remove in final
            goalLeftPower = Math.max(-0.2, Math.min(0.2, goalLeftPower));
            goalRightPower = Math.max(-0.2, Math.min(0.2, goalRightPower));

            SmartDashboard.putNumber("Goal Left Power", goalLeftPower);

            drivetrain.set(goalLeftPower, goalRightPower);
        } else {
            System.out.println("No target");
        }
    }

    @Override
    public void end(boolean interrupted) {
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);

    }

    @Override
    public boolean isFinished() {
        return timer.get() > runTime;
//        return 0.5 >= Math.abs(recentRotations.getAverage());
    }

    private void fetchValues() {


        tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
        tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
    }

    private void rotateAlign() {
        recentRotations.addElement(tx);

        RotationPID();

        goalLeftPower += goalRotationPower;
        goalRightPower += -goalRotationPower;

        SmartDashboard.putNumber("Error", recentRotations.getAverage());
    }

    private void RotationPID() {
        DRIVE_ROTATION_P = SmartDashboard.getNumber("P Constant", 0);
        DRIVE_ROTATION_I = SmartDashboard.getNumber("I Constant", 0);

        DRIVE_ROTATION_D = SmartDashboard.getNumber("D Constant", 0);
        currentTX = recentRotations.getAverage();

        integral += (currentTX*.02); // Integral is increased by the error*time (which is .02 seconds using normal IterativeRobot)
        derivative = (currentTX - previousRotationError) / .02;

        goalRotationPower = DRIVE_ROTATION_P*currentTX + DRIVE_ROTATION_I*integral + DRIVE_ROTATION_D*derivative;

        previousRotationError = currentTX;
    }
}