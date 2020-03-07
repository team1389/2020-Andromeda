package frc.commands;


import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Intake;

public class RunIntakeAndSendBallToIndexer extends CommandBase {

    private Intake intake;
    double conveyorSpeed = 0.75;
    private boolean ballPreIndex;

    public RunIntakeAndSendBallToIndexer() {
        intake = Robot.intake;
        addRequirements(Robot.conveyor, Robot.intake, Robot.indexer);
    }

    @Override
    public void initialize() {
        ballPreIndex = Robot.indexer.ballAtIndexer();
    }

    @Override
    public void execute() {
        intake.runIntake();
        if(Robot.indexer.ballAtIndexer()) {
            Robot.conveyor.stopConveyor();     // Move conveyor if not in place
        }
        else
        {
            Robot.conveyor.runConveyor(conveyorSpeed);     // Move conveyor if not in place
        }
    }

    @Override
    public void end(boolean interrupted) {
        intake.stopIntaking();
        Robot.conveyor.stopConveyor();
    }
}