package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class ClimbControl extends CommandBase {

    public ClimbControl() {
        addRequirements(Robot.climber);
    }

    @Override
    public void initialize() {
        Robot.climber.extend();
    }

    @Override
    public void end(boolean interrupted) {
        Robot.climber.retract();
    }
}
