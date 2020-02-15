package frc.autos;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.commands.DriveDistance;


import static frc.robot.Robot.shooter;

public class Auto1 extends SequentialCommandGroup {

    public Auto1() {
        addCommands(
                new DriveDistance(10));

    }

}
