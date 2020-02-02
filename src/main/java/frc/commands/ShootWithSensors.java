package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;

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
            addCommands(new SetBallInPlace(),
                        // To make sure only 1 ball is in range of shooter by moving conveyor backwards (need testing)
                        new InstantCommand(() -> Robot.indexer.runIndexer(-0.5)),
                        new WaitCommand(0.5),
                        new InstantCommand(() -> Robot.indexer.runIndexer(0.5)),
                        new WaitCommand(1)
            );
        }

        @Override
        public void end(boolean interrupted) {
            Robot.indexer.stopIndexer();
        }
    }

    //Does the process to make sure only 1 ball is in place and preps the shooter motors
    private class SetBallInPlace extends CommandBase {

        private boolean ballInPlace;

        public SetBallInPlace() {
            addRequirements(Robot.shooter, Robot.conveyor, Robot.indexer);
        }

        @Override
        public void initialize() {
            ballInPlace = Robot.indexer.ballDetected();
        }

        @Override
        public void execute() {
            Robot.shooter.setShooterVoltage(1);             // Prep the shooter to speed
            while (!ballInPlace) {                  //Check if a ball is in place
                Robot.conveyor.startConveying(0.5);     // Move conveyor if not in place
                ballInPlace = Robot.indexer.ballDetected();     //Update ballInPlace
            }
        }

        @Override
        public void end(boolean interrupted) {
            Robot.conveyor.stopConveying();
        }
    }

}
