package frc.autos;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;

/***
 * Shoots and then gets off the auto line. Works from any position
 */
public class ShootAndCrossAutoLine extends SequentialCommandGroup {

    public ShootAndCrossAutoLine(){
        addRequirements(Robot.drivetrain, Robot.shooter);
        addCommands(  new InstantCommand(() -> Robot.drivetrain.drive(1,0,false, false)),
                new WaitCommand(1.5),
                new InstantCommand(() -> Robot.drivetrain.set(0,0)));
               // new ShootWithSensors(ShootWithSensors.ShootType.Speed, 4750, 0));
    }
}
