package frc.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Conveyor;

public class RunConveyor extends CommandBase {
    private Conveyor conveyor = null;
    private boolean isConveying = false;

    private double bufferZone = .1;

    public RunConveyor() {
        conveyor = Robot.conveyor;

        addRequirements(conveyor);
    }

    @Override
    public void execute() {
        if (Math.abs(Robot.oi.manipController.getY(GenericHID.Hand.kLeft)) > bufferZone) {
            conveyor.runConveyor(1);
        }
        else {
            conveyor.runConveyor(0);
        }
    }
    @Override
    public void end(boolean interrupted) {
        conveyor.stopConveyor();
    }
}