package frc.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class DescendConveyor extends CommandBase {
    double deadzone = 0.01;
    public DescendConveyor(){
        addRequirements(Robot.conveyor);
    }

    @Override
    public void execute() {
        //Robot.conveyor.runConveyor(-Robot.oi.manipController.getTriggerAxis(GenericHID.Hand.kLeft));
    }

    @Override
    public void end(boolean interrupted) {

    }
}
