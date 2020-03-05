package frc.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;

/**
 * Moves back and forth to drop the intake out
 */
public class ShakeToDropIntake extends SequentialCommandGroup {
    double speed = 1;
    double shakeTime = 0.5;

    public ShakeToDropIntake() {
        addRequirements(Robot.drivetrain);
        addCommands(new InstantCommand(() -> Robot.drivetrain.set(-speed, -speed)), new WaitCommand(shakeTime),
                new InstantCommand(() -> Robot.drivetrain.set(speed, speed)),
                new WaitCommand(shakeTime),
                new InstantCommand(() -> Robot.drivetrain.set(0, 0)));
    }

}
