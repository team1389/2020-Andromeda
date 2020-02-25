package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Conveyor;

public class RunConveyor extends CommandBase {

    private Conveyor conveyor;

    public RunConveyor() {
        conveyor = Robot.conveyor;

        addRequirements(conveyor);
    }

    @Override
    public void execute() {
        conveyor.runConveyor(1);
    }

    @Override
    public void end(boolean interrupted) {
        conveyor.stopConveyor();
    }
}