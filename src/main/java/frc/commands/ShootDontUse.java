package frc.commands;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;

public class ShootDontUse extends SequentialCommandGroup {

    public ShootDontUse() {
        addRequirements(Robot.shooter, Robot.indexer);
        addCommands(new ShootOnce(), new ShootOnce(), new ShootOnce(), new ShootOnce(), new ShootOnce());
    }

    private class ShootOnce extends SequentialCommandGroup {
        public ShootOnce() {
            addRequirements(Robot.shooter, Robot.indexer);
            addCommands(new InstantCommand(() -> Robot.shooter.setShooterVoltage(1)),
                        new WaitCommand(2),
                        new InstantCommand(() -> Robot.indexer.runIndexer(0.5)),
                        new WaitCommand(1)
            );
        }

        @Override
        public void end(boolean interrupted) {
            Robot.indexer.stopIndexer();
        }
    }

    @Override
    public void end(boolean interrupted) {
        Robot.shooter.stopMotors();
    }

}