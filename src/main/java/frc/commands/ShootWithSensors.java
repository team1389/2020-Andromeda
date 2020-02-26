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

    public enum ShootType {
        Distance,
        Speed
    }

    private double value;
    private double shooterSpeed;
    public static double timeToWait = 3;
    private final double MAX_SHOOTER_SPEED = 5000;

    public ShootWithSensors(ShootType type, double distanceOrSpeedValue, int slot){
        addRequirements(Robot.shooter, Robot.conveyor, Robot.indexer);
        this.value = distanceOrSpeedValue;
        if(type == ShootType.Distance)
            shooterSpeed = Robot.shooter.shootDistance(distanceOrSpeedValue, slot);
        else if(type == ShootType.Speed)
            shooterSpeed = distanceOrSpeedValue;

        addCommands(
                new InstantCommand(() -> Robot.conveyor.runConveyor(1)),new InstantCommand(() -> Robot.shooter.setShooterVoltage(shooterSpeed)),
                new WaitCommand(0.25),
                new ShootOnce(0.25),
                new ShootOnce(0),
                new ShootOnce(0),
                new ShootOnce(0),
                new ShootOnce(0));
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void end(boolean interrupted) {
        Robot.shooter.stopMotors();
        Robot.indexer.stopIndexer();
        Robot.conveyor.stopConveyor();
        System.out.println("Killed Shoot With Sensors");
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);

    }

    private class ShootOnce extends SequentialCommandGroup {

        public ShootOnce(double waitSeconds) {
            addRequirements(Robot.shooter, Robot.conveyor, Robot.indexer);
            addCommands(
                    // To make sure only 1 ball is in range of shooter by moving conveyor backwards (need testing wrong)
                    new ParallelCommandGroup(new SendBallToIndexer(), new SpinUpShooters(shooterSpeed)),
                    new InstantCommand(() -> Robot.indexer.runIndexer(1)),
                    new WaitCommand(waitSeconds),
                    new SendBallToShooter()
            );
        }

        @Override
        public void end(boolean interrupted) {
            Robot.indexer.stopIndexer();
        }
    }

    //Does the process to make sure only 1 ball is in place and preps the shooter motors
    private class SendBallToIndexer extends CommandBase {
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
            Robot.indexer.stopIndexer();
            Robot.conveyor.stopConveyor();

        }

        @Override
        public boolean isFinished() {
            if(timer.get() >= ShootWithSensors.timeToWait) {
                System.out.println("timed out on Send Ball To indexer");
                return true;
            }

            //End if the ball reaches the indexer or if 1 second has passed
            return ballPreIndex;
        }
    }

    private class SpinUpShooters extends CommandBase {
        private CANPIDController topPid = Robot.shooter.getShooterTopPIDController();
        private CANPIDController bottomPid = Robot.shooter.getShooterBottomPIDController();
        private double shooterTargetRPM;
        private double tolerance;
        private SizeLimitedQueue recentErrors = new SizeLimitedQueue(7);

        public SpinUpShooters(double shooterTargetRPM) {
            addRequirements(Robot.shooter);

            this.shooterTargetRPM = Math.min(MAX_SHOOTER_SPEED, shooterTargetRPM);
            tolerance = 3;
        }

        @Override
        public void execute() {
            SmartDashboard.putBoolean("pid-ing", true);
            topPid.setReference(shooterTargetRPM, ControlType.kVelocity);
            bottomPid.setReference(shooterTargetRPM, ControlType.kVelocity);

        }

        @Override
        public void end(boolean interrupted) {
            SmartDashboard.putBoolean("pid-ing", false);

        }

        @Override
        public boolean isFinished() {
            double error = shooterTargetRPM - Robot.shooter.getShooterTopRPM();
            recentErrors.addElement(error);
            SmartDashboard.putNumber("average top error", recentErrors.getAverage());
            SmartDashboard.putNumber("Shooter Bottom RPM", Robot.shooter.getShooterBottomRPM());

            return tolerance >= Math.abs(recentErrors.getAverage());
        }
    }

    private class SendBallToShooter extends CommandBase {
        private Timer timer = new Timer();
        private boolean ballPostIndex;

        public SendBallToShooter() {
            addRequirements(Robot.conveyor, Robot.shooter);
            ballPostIndex = Robot.shooter.ballInShooter();
        }

        @Override
        public void initialize() {
            timer.reset();
            timer.start();
        }

        @Override
        public void execute() {
            ballPostIndex = Robot.shooter.ballInShooter();
            Robot.conveyor.runConveyor(1);
        }

        @Override
        public boolean isFinished() {
            return ballPostIndex;
        }

        @Override
        public void end(boolean interrupted) {
            Robot.conveyor.stopConveyor();
        }
    }

}