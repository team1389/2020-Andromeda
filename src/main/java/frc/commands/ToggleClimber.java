package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class ToggleClimber extends CommandBase {

    public ToggleClimber() {
        addRequirements(Robot.climber);
    }

    @Override
    public void initialize() {
        Robot.climber.extend();
        System.out.println("extending climber");
    }

    @Override
    public void end(boolean interrupted) {
        Robot.climber.retract();
    }
}
