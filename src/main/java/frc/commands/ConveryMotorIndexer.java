package frc.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class ConveryMotorIndexer extends CommandBase {
    public ConveryMotorIndexer(){
        addRequirements(Robot.intake);
    }

    @Override
    public void initialize() {
        Robot.indexer.runIndexer(0.5);
    }

    @Override
    public void end(boolean interrupted) {
        Robot.indexer.stopIndexer();
    }
}
