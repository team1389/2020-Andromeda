package frc.Autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.commands.*;
import frc.robot.Robot;
import frc.subsystems.ML;

/**
 * Shoots and picks up from the control panel and then shoots again
 */
public class StraightControlPanel extends SequentialCommandGroup {

    //If this doesn't get it fast enough, speed up the last shoot command
    public StraightControlPanel() {
        addRequirements(Robot.drivetrain, Robot.shooter);
        double conveyorSpeed = 0.6;
        double topTargetRPM = 4400;
        //slowing conveyor speed to stack the balls in the conveyor
        ParallelCommandGroup driveIntakeAndSpinUp = new ParallelCommandGroup(new DriveDistance(145),
                new SendBallToIndexer(conveyorSpeed),
                new InstantCommand(() -> Robot.shooter.setPIDWithTopSpin(topTargetRPM)));

        //Shot can take less time

        addCommands(new ShootWithoutPID(0.8, 0.5, 1),
                new TurnToAngle(0, false),new DriveDistance(10), new TurnToAngle(0, false),
                new WaitCommand(0.5),
                new InstantCommand(() -> Robot.intake.runIntake()),
                driveIntakeAndSpinUp,
                new InstantCommand(() -> Robot.intake.stopIntaking()), new AdjustToTarget(),
                new ShootWithSensors(ShootWithSensors.ShootType.Speed, topTargetRPM));
    }

    @Override
    public void end(boolean interrupted) {
        Robot.intake.stopIntaking();
        Robot.shooter.stopMotors();
        super.end(interrupted);
    }
}
