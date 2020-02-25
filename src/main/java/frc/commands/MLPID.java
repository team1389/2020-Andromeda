package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class MLPID extends CommandBase {
    public MLPID() {
        addRequirements(Robot.ml);

    }
    @Override
    public void execute() {
        double p = Robot.ml.movement()/1350;
        Robot.drivetrain.set(p,-p);

    }

    @Override
    public void end(boolean interrupted) {
    }
}
