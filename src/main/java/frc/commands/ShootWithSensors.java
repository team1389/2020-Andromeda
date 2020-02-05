package frc.commands;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Robot;

import javax.swing.*;

public class ShootWithSensors extends SequentialCommandGroup {

    public ShootWithSensors() {
        addRequirements(Robot.shooter, Robot.conveyor, Robot.indexer);
        addCommands(new ShootOnce(), new ShootOnce(), new ShootOnce(), new ShootOnce(), new ShootOnce());
    }

    @Override
    public void end(boolean interrupted) {
        Robot.shooter.stopMotors();
        Robot.indexer.stopIndexer();
        Robot.conveyor.stopConveying();
    }

    private class ShootOnce extends SequentialCommandGroup {

        public ShootOnce() {
            addRequirements(Robot.shooter, Robot.conveyor, Robot.indexer);
            addCommands(// To make sure only 1 ball is in range of shooter by moving conveyor backwards (need testing wrong)
                        new ParallelCommandGroup(new SendBallToIndexer(), new SpinUpShooters()),
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
            ballPreIndex = Robot.indexer.ballDetected();
        }

        @Override
        public void execute() {
            Robot.conveyor.startConveying(0.5);     // Move conveyor if not in place
            ballPreIndex = Robot.indexer.ballDetected();     //Update ballPreIndex
        }

        @Override
        public void end(boolean interrupted) {
            Robot.indexer.stopIndexer();
            Robot.conveyor.stopConveying();
        }

        @Override
        public boolean isFinished() {
            return !ballPreIndex;
        }
    }

    private class SpinUpShooters extends CommandBase {
        private CANPIDController pid = Robot.shooter.getShooterLeftPIDController();
        private double shooterRPM;
        private double shooterTargetRPM;
        private double tolerance, threshold;


        public SpinUpShooters() {
            addRequirements(Robot.shooter);
        }

        @Override
        public void initialize() {
            //shooterTargetRPM = the target rpm of the shooters
        }

        @Override
        public void execute() {
            pid.setReference(shooterTargetRPM, ControlType.kVelocity);
            shooterRPM = Robot.shooter.getShooterLeftRPM();
        }

        @Override
        public void end(boolean interrupted) {
            Robot.shooter.stopMotors();
        }

        @Override
        public boolean isFinished() {
            return tolerance <= Math.abs(shooterTargetRPM - threshold);
        }

    }

    private class SendBallToShooter extends CommandBase {
        private boolean ballPostIndex;

        public SendBallToShooter() {
            addRequirements(Robot.conveyor);
        }

        @Override
        public void initialize() {
        }


        @Override
        public void execute() {
                Robot.conveyor.startConveying(0.5);
        }

        @Override
        public boolean isFinished() {
            return !ballPostIndex;
        }

        @Override
        public void end(boolean interrupted) {
            Robot.conveyor.stopConveying();
        }
    }

}
