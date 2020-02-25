package frc.commands;


import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Intake;

public class RunIntake extends CommandBase {

    private Intake intake;

    public RunIntake() {
        intake = Robot.intake;
        addRequirements(intake, Robot.conveyor);
    }

    @Override
    public void execute() {
        intake.runIntake(0.75);
        Robot.conveyor.conveyorMotorFront.set(ControlMode.PercentOutput, 0.5);
        Robot.conveyor.conveyorMotorBack.set(ControlMode.PercentOutput, 0.15);
    }

    @Override
    public void end(boolean interrupted) {
        intake.stopIntaking();
        Robot.conveyor.stopConveyor();
    }
}