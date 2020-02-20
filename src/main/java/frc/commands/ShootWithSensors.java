package frc.commands;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Robot;
import frc.utils.SizeLimitedQueue;

public class ShootWithSensors extends SequentialCommandGroup {
    double distance;
    double ShooterSpeed;
    static boolean outOfBalls;

    public ShootWithSensors(double distance, int slot) {
        outOfBalls = false;

        addRequirements(Robot.shooter, Robot.conveyor, Robot.indexer);
        this.distance = distance;
        double shooterspeed;
        switch (slot) {
            case 1: ShooterSpeed = distance/10; break;
            case 2: ShooterSpeed = distance/10; break;
            case 3: ShooterSpeed = distance/10; break;
            case 4: ShooterSpeed = distance/10; break;
            case 5: ShooterSpeed = distance/10; break;
            case 6: ShooterSpeed = distance/10; break;
            case 7: ShooterSpeed = distance/10; break;
            case 8: ShooterSpeed = distance/10; break;

        }

        addCommands(
                new InstantCommand(() -> Robot.conveyor.runConveyor(1)),
                new WaitCommand(0.25),
                new ShootOnce(0.25),
                new ShootOnce(0),
                new ShootOnce(0),
                new ShootOnce(0),
                new ShootOnce(0));
    }

    @Override
    public void end(boolean interrupted) {
        Robot.shooter.stopMotors();
        Robot.indexer.stopIndexer();
        Robot.conveyor.stopConveyor();
    }

    @Override
    public boolean isFinished() {
        return outOfBalls;
    }

    private class ShootOnce extends SequentialCommandGroup {

        public ShootOnce(double waitSeconds) {
            addRequirements(Robot.shooter, Robot.conveyor, Robot.indexer);
            addCommands(
                    // To make sure only 1 ball is in range of shooter by moving conveyor backwards (need testing wrong)
                    new ParallelCommandGroup(new SendBallToIndexer(), new SpinUpShooters(ShooterSpeed)),
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
            System.out.println("Running command");
            Robot.conveyor.runConveyor(1);     // Move conveyor if not in place
            ballPreIndex = Robot.indexer.ballAtIndexer();     //Update ballPreIndex
        }

        @Override
        public void end(boolean interrupted) {
            System.out.println("killed command");
            Robot.indexer.stopIndexer();
            Robot.conveyor.stopConveyor();
        }

        @Override
        public boolean isFinished() {
            if(timer.get() >= 1) {
                outOfBalls = true;
                return true;
            }

            //End if the ball reaches the indexer or if 1 second has passed
            return ballPreIndex;
        }
    }

    public static class SpinUpShooters extends CommandBase {
        private CANPIDController pid = Robot.shooter.getShooterTopPIDController();
        private double shooterTargetRPM;
        private double tolerance;
        private SizeLimitedQueue recentErrors = new SizeLimitedQueue(7);

        public SpinUpShooters(double shooterTargetRPM) {
            addRequirements(Robot.shooter);
            this.shooterTargetRPM = shooterTargetRPM;
            tolerance = 5;
        }

        @Override
        public void execute() {
            pid.setReference(shooterTargetRPM, ControlType.kVelocity);

        }

        @Override
        public void end(boolean interrupted) {
        }

        @Override
        public boolean isFinished() {
            double error = shooterTargetRPM - Robot.shooter.getShooterTopRPM();
            recentErrors.addElement(error);
            SmartDashboard.putNumber("average error", recentErrors.getAverage());

            return tolerance >= Math.abs(recentErrors.getAverage());
        }
    }

    public static class SendBallToShooter extends CommandBase {
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
            return ballPostIndex || timer.get() >= 1;
        }

        @Override
        public void end(boolean interrupted) {
            Robot.conveyor.stopConveyor();
        }
    }

}