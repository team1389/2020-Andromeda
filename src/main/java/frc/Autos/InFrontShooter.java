package frc.Autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.commands.DriveDistance;
import frc.commands.ShootWithSensors;
import frc.commands.TurnToAngle;
import frc.robot.Robot;

/**
 *
 *  Shoots from exactly in front of the goal and then drives to the control panel depending on the angel of approach
 *  A lot of the code is still a work in progress or are estimates as we don't got all of the measurements yet
 */
public class InFrontShooter extends SequentialCommandGroup {
    public double degrees  =  -20; //We can change this to whatever we want after some testing on a field or once we get exact measurements
   public double distance = -220; //For how far the robot has to go to get to the first ball (inches)
    public InFrontShooter(){
addRequirements(Robot.shooter,Robot.drivetrain);
addCommands(new ShootWithSensors(2000), new TurnToAngle(0,false), new TurnToAngle(degrees,false), new DriveDistance(distance), new TurnToAngle(0,false), new DriveDistance(74), new ShootWithSensors(2000));



}
}
