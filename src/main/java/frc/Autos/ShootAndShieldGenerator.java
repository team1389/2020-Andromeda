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
public class ShootAndShieldGenerator extends SequentialCommandGroup {

    private final double da3 = -102.5; //Distance to the shield generator
    //^ Measured at field, this number should be 142.5 from the power port side

    public ShootAndShieldGenerator(){
        addRequirements(Robot.drivetrain,Robot.shooter);
        addCommands(new ShootWithSensors(ShootWithSensors.ShootType.Speed, 2000, 0),
                    new TurnToAngle(0,false),
                    new DriveDistance(da3),
                    new ShootWithSensors(ShootWithSensors.ShootType.Speed, 2000, 0));
    }
}
