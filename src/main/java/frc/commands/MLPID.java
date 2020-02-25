package frc.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class MLPID extends CommandBase {
    double kP;
    double kI;
    double kD;
    PIDController pid;


    public MLPID() {
        addRequirements(Robot.ml);
        kP = 0.0000001;
        SmartDashboard.putNumber("kP", kP);
        SmartDashboard.putNumber("kI", kI);
        SmartDashboard.putNumber("kD", kD);
        pid = new PIDController(kP, kI, kD);
    }
    @Override
    public void execute() {
        double error  = Robot.ml.movement(); //from -1 to 1

        Robot.drivetrain.set(kP,-kP);
        SmartDashboard.putNumber("ML error", error);
        kP = SmartDashboard.getNumber("kP", kP);
        kI = SmartDashboard.getNumber("kI", kI);
        kD = SmartDashboard.getNumber("kD", kD);

    }

    @Override
    public void end(boolean interrupted) {
    }
}
