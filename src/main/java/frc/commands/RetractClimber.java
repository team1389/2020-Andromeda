package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class RetractClimber extends CommandBase {
    public RetractClimber(){
        addRequirements(Robot.climber);
    }

    @Override
    public void initialize() {
        Robot.climber.retract();
    }
}
