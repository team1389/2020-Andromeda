package frc.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Robot;

public class ExtendClimber extends CommandBase {

    public ExtendClimber() {
        addRequirements(Robot.climber);
    }

    @Override
    public void initialize() {
        Robot.climber.extend();
    }

}
