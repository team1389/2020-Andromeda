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

        if (!(Robot.conveyor.ballAtSecondPosition() &&
                Robot.conveyor.ballAtBottomConveyor() &&
                Robot.indexer.ballAtIndexer())) {

            Robot.conveyor.runBottomConveyor(conveyorSpeed);

        } else {
            Robot.conveyor.conveyorMotorBack.set(ControlMode.PercentOutput, 0);
        }

        if (Robot.conveyor.ballAtBottomConveyor() && !Robot.indexer.ballAtIndexer()) {
            Robot.conveyor.runTopConveyor(conveyorSpeed);
            Robot.indexer.runIndexer(conveyorSpeed); // Move conveyor if not in place
        } else {
            Robot.conveyor.conveyorMotorFront.set(ControlMode.PercentOutput, 0);
            Robot.indexer.stopIndexer();// Move conveyor if not in place
        }
    }

    @Override
    public void end(boolean interrupted) {
        intake.stopIntaking();
        Robot.conveyor.stopConveyor();
    }
}