package frc.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Conveyor;

public class RunConveyor extends CommandBase {
    private Conveyor conveyor = null;
    private boolean isConveying = false;

    public RunConveyor() {
        conveyor = Robot.conveyor;

        addRequirements(conveyor);
    }

    @Override
    public void execute() {
        //a button
        if(Robot.oi.manipAButton()) {
            if(isConveying) {
                isConveying = false;
            }
            else {
                isConveying = true;
            }
        }

        if(isConveying) {
            conveyor.startConveying();
            System.out.println("Jebediah is yeeting balls through the conveyor belt!");
        }
        else {
            conveyor.stopConveying();
        }

    }
}
