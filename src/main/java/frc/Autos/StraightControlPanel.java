package frc.Autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.commands.DriveDistance;
import frc.commands.MLTurnToBall;
import frc.commands.ShootWithSensors;
import frc.commands.TurnToAngle;
import frc.robot.Robot;
import frc.subsystems.ML;

/**
 *
 * Shoots and picks up from the control panel and then shoots again
 *
 */
public class StraightControlPanel extends SequentialCommandGroup {

    public StraightControlPanel(){
        addRequirements(Robot.drivetrain, Robot.shooter);
        addCommands(new ShootWithSensors(ShootWithSensors.ShootType.Speed, 5000, false), new InstantCommand(() -> Robot.intake.runIntake()),
                new TurnToAngle(15, false),
                new MLTurnToBall(),
                new DriveDistance(194.63),
                new InstantCommand(() -> Robot.intake.stopIntaking()),
                new ShootWithSensors(ShootWithSensors.ShootType.Speed, 3300));
    }
}
