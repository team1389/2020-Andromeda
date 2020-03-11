package frc.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;
import frc.subsystems.Drivetrain;

import java.time.Instant;

//Run this to shoot
public class SystemsTest extends SequentialCommandGroup {
    public SystemsTest() {
        addRequirements(Robot.drivetrain, Robot.shooter, Robot.conveyor, Robot.intake, Robot.climber);
        addCommands(
                new InstantCommand(() -> NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3)),
                new InstantCommand(() -> Robot.intake.runIntake()),
                new InstantCommand(() -> Robot.conveyor.runConveyor(0.5)),
                new WaitCommand(0.3),
                new InstantCommand(() -> Robot.intake.stopIntaking()),
                new InstantCommand(() -> Robot.conveyor.stopConveyor()),

                new ShootWithoutPID(0.2, 0.5, 0.5),
                new DriveDistance(12), new WaitCommand(2),
                new DriveDistance(-12), new WaitCommand(2),
                new InstantCommand(() -> new TurnToAngle(360, false)),
                new InstantCommand(() -> NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1))
        );

        /*addCommands(new InstantCommand(() -> Robot.intake.runIntake()), new WaitCommand(2),
                    new InstantCommand(() -> Robot.intake.stopIntaking()),
                    new InstantCommand(() -> Robot.conveyor.runConveyor(0.5)),
                new InstantCommand(() -> Robot.conveyor.stopConveyor()),
                    new DriveDistance(12), new WaitCommand(2),
                    new DriveDistance(-12), new WaitCommand(2),
                    new InstantCommand(() -> new TurnToAngle(360, false)));

         */






    }
}
