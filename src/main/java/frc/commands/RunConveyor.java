package frc.commands;


import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Conveyor;

public class RunConveyor extends CommandBase {

    private Conveyor conveyor;
    private double bufferZone;


    public RunConveyor() {
        conveyor = Robot.conveyor;
        addRequirements(conveyor);
        bufferZone = 0.1;
    }

    @Override
    public void execute() {
        if(conveyor.ballAtSecondPosition() || Robot.oi.manipController.getTriggerAxis(GenericHID.Hand.kLeft) > bufferZone){
            conveyor.runConveyor(1);
        }
        else{
            conveyor.runConveyor(0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        conveyor.stopConveyor();
    }
}