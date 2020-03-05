package frc.autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.commands.DriveDistance;
import frc.commands.TurnToAngle;
import frc.robot.Robot;

/**
 *
 *  Shoots from exactly in front of the power port and then drives to the control panel depending on the angel of approach
 *  A lot of the code is still a work in progress or are estimates as we don't got all of the measurements yet
 */
public class PowerPortToControlPanel extends SequentialCommandGroup {

    private final double degrees  =  -20; //We can change this to whatever we want after some testing on a field or once we get exact measurements
    private final double distance = -220; //For how far the robot has to go to get to the first ball (inches)

    public PowerPortToControlPanel() {
        addRequirements(Robot.shooter,Robot.drivetrain);
        addCommands(//new ShootWithSensors(ShootWithSensors.ShootType.Speed, 2000, 0),
                    new TurnToAngle(0,false),
                    new TurnToAngle(degrees,false),
                    new DriveDistance(distance),
                    new TurnToAngle(0,false),
                    new DriveDistance(74));
                   // new ShootWithSensors(ShootWithSensors.ShootType.Speed, 2000, 0));
    }
}
