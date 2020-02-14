package frc.commands;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Robot;
import frc.utils.SizeLimitedQueue;

public class ShootWithSensors extends SequentialCommandGroup {
    double shootingSpeed;

    public ShootWithSensors() {
        addRequirements(Robot.shooter, Robot.conveyor, Robot.indexer);
        shootingSpeed = 2000;
        addCommands(new ShootOnce(), new ShootOnce(), new ShootOnce(), new ShootOnce(), new ShootOnce());
    }

    @Override
    public void end(boolean interrupted) {
        Robot.shooter.stopMotors();
        Robot.indexer.stopIndexer();
        Robot.conveyor.stopConveyor();
    }

    private class ShootOnce extends SequentialCommandGroup {

        public ShootOnce() {
            addRequirements(Robot.shooter, Robot.conveyor, Robot.indexer);
            addCommands(
// To make sure only 1 ball is in range of shooter by moving conveyor backwards (need testing wrong)
                    new ParallelCommandGroup(new SendBallToIndexer(), new SpinUpShooters(shootingSpeed)),
                    new InstantCommand(() -> Robot.indexer.runIndexer(.75)),
                    new WaitCommand(0.25),
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

        private boolean ballPreIndex;

        public SendBallToIndexer() {
            addRequirements(Robot.conveyor, Robot.indexer);
        }

        @Override
        public void initialize() {
            ballPreIndex = Robot.indexer.ballAtIndexer();
        }

        @Override
        public void execute() {
            Robot.conveyor.runConveyor(0.5);     // Move conveyor if not in place
            ballPreIndex = Robot.indexer.ballAtIndexer();     //Update ballPreIndex
        }

        @Override
        public void end(boolean interrupted) {
            Robot.indexer.stopIndexer();
            Robot.conveyor.stopConveyor();
        }

        @Override
        public boolean isFinished() {
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

    private class SendBallToShooter extends CommandBase {
        private boolean ballPostIndex;

        public SendBallToShooter() {
            addRequirements(Robot.conveyor, Robot.shooter);
            ballPostIndex = Robot.shooter.ballInShooter();
        }


        @Override
        public void execute() {
            ballPostIndex = Robot.shooter.ballInShooter();
            Robot.conveyor.runConveyor(0.5);
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