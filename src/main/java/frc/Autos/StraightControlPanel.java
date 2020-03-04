package frc.Autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.commands.*;
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
        addCommands(new ShootWithSensors(ShootWithSensors.ShootType.Speed, 5000), new InstantCommand(() -> Robot.drivetrain.set(1,1)), new WaitCommand(0.5),
                new InstantCommand(() -> Robot.drivetrain.set(-1,-1)), new InstantCommand(() -> Robot.drivetrain.set(0,0)), new InstantCommand(() -> Robot.intake.runIntake()),
                new TurnToAngle(0, false),
                new DriveDistance(194.63),
                new InstantCommand(() -> Robot.intake.stopIntaking()), new AdjustToTarget(),
                new ShootWithSensors(ShootWithSensors.ShootType.Speed, 3500));
    }
}
