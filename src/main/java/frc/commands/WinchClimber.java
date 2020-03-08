package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class WinchClimber extends CommandBase {
    public WinchClimber(){
        addRequirements(Robot.climber);
    }

    @Override
    public void initialize() {
        Robot.climber.winch(0.6);
    }

    @Override
    public void end(boolean interrupted) {
        Robot.climber.winch(0);
    }
}