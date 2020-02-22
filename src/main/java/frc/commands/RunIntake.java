package frc.commands;


import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Drivetrain;
import frc.subsystems.Intake;

public class RunIntake extends CommandBase {
    private Intake intake = null;

    public RunIntake() {
        intake = Robot.intake;
        addRequirements(intake, Robot.conveyor);

    }

    @Override
    public void execute() {
        intake.runIntake(0.9);
        Robot.conveyor.conveyorMotorFront.set(ControlMode.PercentOutput, 1);
        Robot.conveyor.conveyorMotorBack.set(ControlMode.PercentOutput, 0.2);
    }

    @Override
    public void end(boolean interrupted) {
        intake.stopIntaking();
        Robot.conveyor.stopConveyor();
    }
}