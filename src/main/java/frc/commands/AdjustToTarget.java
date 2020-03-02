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

    private SizeLimitedQueue recentRotations = new SizeLimitedQueue(7);

    private double integral, derivative, previousRotationError, goalRotationPower, currentTX;
    private double goalLeftPower, goalRightPower;

    private double DRIVE_ROTATION_P = 0.0125;
    private double DRIVE_ROTATION_I = 0.0023;//0.05
    private double DRIVE_ROTATION_D = 0;

    private double TARGET_HEIGHT_INCHES = 83.25; //83.25 on real field, 95 on shelf
    private double CAMERA_ANGLE_DEGREES = 27.21212218; //REMEMBER TO CHANGE THIS EVERY MATCH IF WE ADJUST THE SHOOTER
    private double CAMERA_HEIGHT_INCHES = 26;

    private double runTime = 2;

    private Timer timer = new Timer();

    //Limelight values
    private double tv, tx, ty, ta;

    public AdjustToTarget() {
        drivetrain = Robot.drivetrain;
        addRequirements(drivetrain);

        timer.start();
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
    }

    @Override
    public void execute() {
        double distanceToTarget = (TARGET_HEIGHT_INCHES-CAMERA_HEIGHT_INCHES)/(Math.tan(Math.toRadians(CAMERA_ANGLE_DEGREES+ty)));

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
            SmartDashboard.putNumber("Distance To Target", distanceToTarget);
            SmartDashboard.putNumber("Tangent", Math.tan(Math.toRadians(CAMERA_ANGLE_DEGREES+ty)));

            drivetrain.set(goalLeftPower, goalRightPower);
        } else {
            System.out.println("No target");
        }
    }

    @Override
    public boolean isFinished() {
        if(timer.get() > runTime){
            System.out.println("Timed out of AdjustToTarget");
        }
        return timer.get() > runTime || 1 >= Math.abs(recentRotations.getAverage());
    }

    private void fetchValues() {
        DRIVE_ROTATION_P = SmartDashboard.getNumber("P Constant", 0);
        DRIVE_ROTATION_I = SmartDashboard.getNumber("I Constant", 0);
        DRIVE_ROTATION_D = SmartDashboard.getNumber("D Constant", 0);

        tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
        tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
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
        currentTX = recentRotations.getAverage();

        integral += (currentTX*.02); // Integral is increased by the error*time (which is .02 seconds using normal IterativeRobot)
        derivative = (currentTX - previousRotationError) / .02;

        goalRotationPower = DRIVE_ROTATION_P*currentTX + DRIVE_ROTATION_I*integral + DRIVE_ROTATION_D*derivative;

        previousRotationError = currentTX;
    }
}