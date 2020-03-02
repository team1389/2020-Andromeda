package frc.commands;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Robot;
import frc.utils.SizeLimitedQueue;

public class ShootWithSensors extends SequentialCommandGroup {

    private double shooterTargetRPM;
    private CANPIDController topPID, bottomPID;

    public ShootWithSensors(ShootType type, double distanceOrSpeedValue, int slot) {
        addRequirements(Robot.shooter, Robot.conveyor, Robot.indexer);
        if (type == ShootType.Distance)
            shooterTargetRPM = Robot.shooter.shootDistance(distanceOrSpeedValue, slot);
        else if (type == ShootType.Speed)
            shooterTargetRPM = distanceOrSpeedValue;
        topPID = Robot.shooter.getShooterTopPIDController();
        bottomPID = Robot.shooter.getShooterBottomPIDController();


        //TODO: Test if we need a wait time after running the indexer (i.e. if the indexer speed affects shot distance)
        addCommands(new InstantCommand(() -> Robot.indexer.runIndexer(1)),
                new ShootOnce(),
                new ShootOnce(),
                new ShootOnce(),
                new ShootOnce(),
                new ShootOnce()
        );


    }

    @Override
    public void initialize() {
        super.initialize();
        topPID.setReference(shooterTargetRPM, ControlType.kVelocity);
        bottomPID.setReference(shooterTargetRPM, ControlType.kVelocity);
    }

    @Override
    public void end(boolean interrupted) {
        Robot.shooter.stopMotors();
        Robot.indexer.stopIndexer();
        Robot.conveyor.stopConveyor();
        System.out.println("Killed Shoot With Sensors");
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
    }

    public enum ShootType {
        Distance,
        Speed
    }

    //Does the process to make sure only 1 ball is in place and preps the shooter motors
    public static class SendBallToIndexer extends CommandBase {
        private Timer timer = new Timer();
        private boolean ballPreIndex;

        public SendBallToIndexer() {
            addRequirements(Robot.conveyor, Robot.indexer);
        }

        @Override
        public void initialize() {
            ballPreIndex = Robot.indexer.ballAtIndexer();

            timer.reset();
            timer.start();
        }

        @Override
        public void execute() {
            Robot.conveyor.runConveyor(1);     // Move conveyor if not in place
            ballPreIndex = Robot.indexer.ballAtIndexer();     //Update ballPreIndex
        }

        @Override
        public void end(boolean interrupted) {
            System.out.println("killed Send Ball to Indexer command");
            Robot.conveyor.stopConveyor();

        }

        @Override
        public boolean isFinished() {


            //End if the ball reaches the indexer or if 1 second has passed
            return ballPreIndex;
        }
    }

    public static class WaitUntilAtSpeed extends CommandBase {

        private double shooterTargetRPM;
        private double tolerance;
        private SizeLimitedQueue recentTopErrors = new SizeLimitedQueue(15);
        private SizeLimitedQueue recentBottomErrors = new SizeLimitedQueue(15);

        public WaitUntilAtSpeed(double shooterTargetRPM) {
            addRequirements(Robot.shooter);
            this.shooterTargetRPM = shooterTargetRPM;
            tolerance = 3;
        }

        @Override
        public void execute() {
            SmartDashboard.putBoolean("pid-ing", true);

            double topError = shooterTargetRPM - Robot.shooter.getShooterTopRPM();
            recentTopErrors.addElement(topError);
            SmartDashboard.putNumber("average top error", recentTopErrors.getAverage());

            double bottomError = shooterTargetRPM - Robot.shooter.getShooterBottomRPM();
            recentBottomErrors.addElement(bottomError);
            SmartDashboard.putNumber("average bottom error", recentBottomErrors.getAverage());
        }

        @Override
        public void end(boolean interrupted) {
            SmartDashboard.putBoolean("pid-ing", false);
        }

        @Override
        public boolean isFinished() {
            return tolerance >= Math.abs(recentTopErrors.getAverage()) &&
                    tolerance >= Math.abs(recentBottomErrors.getAverage());
        }
    }

    public static class SendBallToShooter extends CommandBase {
        private Timer timer = new Timer();

        public SendBallToShooter() {
            addRequirements(Robot.conveyor);
        }

        @Override
        public void initialize() {
            timer.reset();
            timer.start();
        }

        @Override
        public void execute() {
            Robot.conveyor.runConveyor(1);
        }

        @Override
        public boolean isFinished() {
            return timer.get() >= 0.1;
        }

    }


    private class ShootOnce extends SequentialCommandGroup {

        public ShootOnce() {
            addRequirements(Robot.shooter, Robot.conveyor, Robot.indexer);
            addCommands(
                    // To make sure only 1 ball is in range of shooter by moving conveyor backwards (need testing wrong)
                    new ParallelCommandGroup(new SendBallToIndexer(), new WaitUntilAtSpeed(shooterTargetRPM)),
                    new SendBallToShooter()
            );
        }

    }

}