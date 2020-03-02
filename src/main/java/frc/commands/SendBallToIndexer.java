package frc.commands;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class SendBallToIndexer extends CommandBase {
    private boolean ballPreIndex;

    public SendBallToIndexer() {
        addRequirements(Robot.conveyor, Robot.indexer);
    }

    @Override
    public void initialize() {
        ballPreIndex = Robot.indexer.ballAtIndexer();
    }

    @Override
    public void execute() {
        Robot.conveyor.runConveyor(1);     // Move conveyor if not in place
        ballPreIndex = Robot.indexer.ballAtIndexer();     //Update ballPreIndex
    }

    @Override
    public void end(boolean interrupted) {
        Robot.conveyor.stopConveyor();
    }

    @Override
    public boolean isFinished() {
        return ballPreIndex;
    }
}
