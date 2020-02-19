package frc.Autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.commands.ShootWithSensors;
import frc.robot.Robot;

/***
 * Starts 102.5 inches from the right side of the field, shoots,
 * and then moves to the shield generator, picks up balls, then runs again
 */
public class ShootAndShieldGenerator extends SequentialCommandGroup {
    public ShootAndShieldGenerator(){
        addRequirements(Robot.shooter, Robot.drivetrain);
        addCommands(new ShootWithSensors(2000), );
    }
}
