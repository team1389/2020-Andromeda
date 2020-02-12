package frc.commands;


import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Intake;

public class RunIntakeReverse extends CommandBase {
    private Intake intake = null;
    private boolean isIntaking = false;

    public RunIntakeReverse() {
        intake = Robot.intake;
        addRequirements(intake);

    }

    @Override
    public void execute() {
        intake.runIntake(-1);
    }

    @Override
    public void end(boolean interrupted) {
        intake.stopIntaking();
    }
}