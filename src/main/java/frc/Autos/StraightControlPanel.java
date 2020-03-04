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
        double conveyorSpeed = 0.5;
        double topTargetRPM = 3500;
        //slowing conveyor speed to stack the balls in the conveyor
        ParallelCommandGroup driveIntakeAndSpinUp = new ParallelCommandGroup(new DriveDistance(194.63),
                new SendBallToIndexer(0.5),
                new InstantCommand(() -> Robot.shooter.setPIDWithTopSpin(topTargetRPM)));


        addCommands(new ShootWithoutPID(0.8, 0.5, 1),
                new TurnToAngle(0, false), new ShakeToDropIntake(), new InstantCommand(() -> Robot.intake.runIntake()),
                new DriveDistance(194.63),
                new InstantCommand(() -> Robot.intake.stopIntaking()), new AdjustToTarget(), driveIntakeAndSpinUp,
                new ShootWithSensors(ShootWithSensors.ShootType.Speed, topTargetRPM));
    }
}
