package frc.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;

public class ShootWithoutPID extends SequentialCommandGroup {
    double voltagePercent;
    double spinUpTime;
    double postSpinUpTimeToWait;

    /**
     * @param voltagePercent       Percent of voltage to set shooter at
     * @param spinUpTimeInSeconds  Time to give the shooter to speed up
     * @param postSpinUpTimeToWait Time to run the command after the shooter is given time to speed up
     */
    public ShootWithoutPID(double voltagePercent, double spinUpTimeInSeconds, double postSpinUpTimeToWait) {
        addRequirements(Robot.shooter, Robot.conveyor, Robot.indexer);
        this.voltagePercent = voltagePercent;
        this.spinUpTime = spinUpTimeInSeconds;
        this.postSpinUpTimeToWait = postSpinUpTimeToWait;
        double conveyorPercent = 0.7;
        addCommands(new InstantCommand(() -> Robot.shooter.setShooterVoltage(voltagePercent)),
                new InstantCommand(() -> Robot.indexer.runIndexer(1)),
                new WaitCommand(spinUpTimeInSeconds),
                //new InstantCommand(() -> Robot.conveyor.runConveyor(conveyorPercent)),
                new WaitCommand(postSpinUpTimeToWait)
        );
    }

    @Override
    public void initialize() {
        super.initialize();
        Robot.shooter.setShooterVoltage(voltagePercent);

    }


    @Override
    public void end(boolean interrupted) {
        Robot.shooter.setShooterVoltage(0);
        Robot.indexer.stopIndexer();
        Robot.conveyor.stopConveyor();
        super.end(interrupted);
    }
}
