package frc.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class Outtake extends CommandBase {
    public Outtake(){
        addRequirements(Robot.intake);
    }

    @Override
    public void initialize() {
        Robot.intake.runIntake(-1);
    }

    @Override
    public void end(boolean interrupted) {
        Robot.intake.runIntake(0);
    }
}
