package frc.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Drivetrain;

public class AdjustToTarget extends CommandBase {
    private Drivetrain drivetrain;

    //Rotation moving average variables:
    private double[] recentRotations = {0, 0, 0};
    private int rotationWriteTo = 0;
    private double rotationSum;

    //Distance moving average variables:
    private double[] recentDistances = {0, 0, 0};
    private int distanceWriteTo = 0;
    private double distanceSum;

    //Pid variables (not the constants)
    private double integral, derivative, previousRotationError, goalRotationPower, goalDistancePower, previousDistanceError, driveIntegral, currentTX;

    //Field measurements
    private final double TARGET_HEIGHT = 83.25; // 83.25 is actual 88.5 is windowsill
    private final double CAMERA_HEIGHT = 22; //28.056 on the real bot
    private final double CAMERA_ANGLE_DEGREES = 0;

    //Some extra variables for fun
    private double distance, goalLeftPower, goalRightPower;
    private double distanceError; //distance from target minus target distance (420 inches)

    //Rotation PID constants
    private final double DRIVE_ROTATION_P = 0.0125; //Ku = 0.5, Tu = 0.4.   0.3, 15, 0.15    0.75     0.025 Oscillation: 0.1
    private final double DRIVE_ROTATION_I = 0.05; //0.007
    private final double DRIVE_ROTATION_D = 0;

    //Distance PID constants
    private final double DRIVE_DISTANCE_P = 0.027; //0.1 = oscillate
    private final double DRIVE_DISTANCE_I = 0.007;
    private final double DRIVE_DISTANCE_D = 0;

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
        tv = 0;
        tx = 0;
        ty = 0;
        ta = 0;
    }

    private void fetchValues() {
        tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
        tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
    }

    //Returns the current distance average
    private double distanceAverage() {
        if(distanceWriteTo < 3) {
            recentDistances[distanceWriteTo] = distance;
            distanceWriteTo ++;
            return distance;
        }

        distanceSum = recentDistances[0] + recentDistances[1] + recentDistances[2];

        recentDistances[0] = recentDistances[1];
        recentDistances[1] = recentDistances[2];
        recentDistances[2] = distance;

        return distanceSum/3;
    }

    //Returns the current rotation average
    private double rotationAverage() {
        if(rotationWriteTo < 3) {
            recentRotations[rotationWriteTo] = tx;
            rotationWriteTo ++;
            return tx;
        }

        rotationSum = recentRotations[0] + recentRotations[1] + recentRotations[2];

        recentRotations[0] = recentRotations[1];
        recentRotations[1] = recentRotations[2];
        recentRotations[2] = tx;

        return rotationSum/3;
    }

    //Align the robot to face the target
    public void rotateAlign() {
        RotationPID();

        //drivetrain.set(goalRotationPower, -goalRotationPower);
        goalLeftPower += goalRotationPower;
        goalRightPower += -goalRotationPower;

        //System.out.println(goalRotationPower + " " + -goalRotationPower);
        //System.out.println("Found target, rotating");
    }

    //Move robot to the right distance from the target
    public void distanceAlign() {
        distance = (TARGET_HEIGHT-CAMERA_HEIGHT) / Math.tan(Math.toRadians(CAMERA_ANGLE_DEGREES+ty));

        distance = distanceAverage();

        distanceError = 200 - distance; //target shooting location is 420 inches (35 feet)

        System.out.println(distance);

        DistancePID();

        //System.out.println(goalDistancePower);

        //drivetrain.set(-goalDistancePower, -goalDistancePower);
        goalDistancePower = Math.max(-0.2, Math.min(0.2, goalDistancePower));

        goalLeftPower += -goalDistancePower;
        goalRightPower += -goalDistancePower;

        //System.out.println(distance);
        //System.out.println(ty);
        //System.out.println();
        //System.out.println("Adjusting distance");
    }

    @Override
    public void execute() {
        fetchValues();

        goalLeftPower = 0;
        goalRightPower = 0;

        //Only run if the target is in view. If the robot isn't facing the right way, align it before adjusting distance
        if (tv >= 1) {
            if(Math.abs(tx) >= 1) {
                rotateAlign();
                System.out.print("rotating");
            }
            else {
                distanceAlign();
            }

            goalLeftPower = Math.max(-0.2, Math.min(0.2, goalLeftPower));
            goalRightPower = Math.max(-0.2, Math.min(0.2, goalRightPower));

            drivetrain.set(goalLeftPower, goalRightPower);

            System.out.println(Double.toString(goalLeftPower) + " " + Double.toString(goalRightPower));

        } else {
            System.out.println("No target");
        }
    }

    private void DistancePID() {
        driveIntegral += (distanceError*.02); // Integral is increased by the error*time (which is .02 seconds using normal IterativeRobot)
        derivative = (distanceError - previousDistanceError) / .02;

        goalDistancePower = DRIVE_DISTANCE_P*distanceError + DRIVE_DISTANCE_I*driveIntegral + DRIVE_DISTANCE_D*derivative;

        previousDistanceError = distanceError;
    }

    private void RotationPID() {
        currentTX = rotationAverage();

        integral += (currentTX*.02); // Integral is increased by the error*time (which is .02 seconds using normal IterativeRobot)
        derivative = (currentTX - previousRotationError) / .02;

        if(Math.abs(derivative) == 0) {
            //System.out.println(timer.get());
            timer.reset();
        }

        goalRotationPower = DRIVE_ROTATION_P*currentTX + DRIVE_ROTATION_I*integral + DRIVE_ROTATION_D*derivative;

        //if(Math.abs(tx-previous_error) <= 0.05) {
        //    goalRotationPower = 0;
        //}

        previousRotationError = currentTX;
    }
}