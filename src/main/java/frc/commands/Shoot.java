package frc.commands;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import static frc.robot.Robot.shooter;

public class Shoot extends SequentialCommandGroup {

    public Shoot() {
        addRequirements(shooter);
        addCommands(new InstantCommand(() -> shooter.setShooterVoltage(1)), new WaitCommand(2),
                new InstantCommand(() -> shooter.setIndexerVoltage(0.5)),
                new WaitCommand(1), new InstantCommand(() -> shooter.stopMotors()));
    }

}
