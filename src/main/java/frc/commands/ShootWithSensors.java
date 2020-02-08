package frc.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Robot;
import frc.subsystems.Shooter;

public class ShootWithSensors extends SequentialCommandGroup {
    public boolean ballInPlace, ballPostIndex;
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
            addCommands(new SendBallToIndexer(),
                        // To make sure only 1 ball is in range of shooter by moving conveyor backwards (need testing)
                        new WaitCommand(1),
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

        public SendBallToIndexer() {
            addRequirements(Robot.shooter, Robot.conveyor, Robot.indexer);
        }
        public PIDController pid = new PIDController(0.1, 0.1, 0.1);

        @Override
        public void initialize() {
            ballInPlace = Robot.indexer.preBallDetector();
        }

        @Override
        public void execute() {
            Robot.shooter.setShooterVoltage(pid.calculate(Robot.shooter.leftEncoder.getDistance(), 1));       // Prep the shooter to speed, encoder value for measurement
            while (!ballInPlace) {                       //Check if a ball is in place
                Robot.conveyor.startConveying(0.5);        // Move conveyor if not in place
                ballInPlace = Robot.indexer.preBallDetector();        //Update ballInPlace
            }
            Robot.indexer.runIndexer(.75);              //runs indexers at 75 percent speed
        }

        @Override
        public void end(boolean interrupted) {
            Robot.conveyor.stopConveying();
            Robot.shooter.stopMotors();
            Robot.indexer.stopIndexer();
        }
    }
    private class SendBallToShooter extends CommandBase{
        public SendBallToShooter() {
            addRequirements(Robot.conveyor, Robot.indexer);
        }

        @Override
        public void execute() {
            while (!ballPostIndex) {                            //runs while the ball is not at the second beam break
                Robot.conveyor.startConveying(.5);          //moves conveyor
                ballPostIndex = Robot.indexer.postBallDetector();   //updates ballPostIndex
            }
        }

        @Override
        public void end(boolean interrupted) {
            Robot.conveyor.stopConveying();
            Robot.indexer.stopIndexer();
        }
    }
}