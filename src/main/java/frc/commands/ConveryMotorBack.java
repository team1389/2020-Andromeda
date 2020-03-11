package frc.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class ConveryMotorBack extends CommandBase {
    public ConveryMotorBack(){
        addRequirements(Robot.conveyor);
    }

    @Override
    public void initialize() {
        Robot.conveyor.conveyorMotorBack.set(ControlMode.PercentOutput, 0.5);
    }

    @Override
    public void end(boolean interrupted) {
        Robot.conveyor.conveyorMotorBack.set(ControlMode.PercentOutput, 0);
    }
}
