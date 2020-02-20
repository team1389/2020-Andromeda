package frc.Autos;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.commands.DriveDistance;
import frc.commands.ShootWithSensors;
import frc.commands.TurnToAngle;
import frc.robot.Robot;

/**
 *
 * Shoots from the left of the power port and then picks up from the shield generator
 * This one will be especially hard to do
 *
 * Our Approach to get the balls from the shield generator should be further discussed
 * (For now it just goes straight forward)
 */
public class LeftToShieldGen extends SequentialCommandGroup {
public double da3 = 102.5; //Distance to the shield generator
public LeftToShieldGen(){
addRequirements(Robot.drivetrain,Robot.shooter);
addCommands(new ShootWithSensors(2000),new TurnToAngle(0,false), new DriveDistance(da3));

}


}
