package frc.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

//Run this to shoot
public class DistanceShoot extends SequentialCommandGroup {
    public DistanceShoot() {
        addCommands(new GetDistanceToTarget(), new ShootWithSensors());
    }
}
