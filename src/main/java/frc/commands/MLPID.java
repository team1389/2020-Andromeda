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
        kP = SmartDashboard.getNumber("kP", kP);
        kI = SmartDashboard.getNumber("kI", kI);
        kD = SmartDashboard.getNumber("kD", kD);
        pid.setP(kP);
        pid.setI(kI);
        pid.setD(kD);
        double error = Robot.ml.movement();
        double power = pid.calculate(error,0); //from -1 to 1
        SmartDashboard.putNumber("ML error", error);
        Robot.drivetrain.set(power,-power);

    }

    @Override
    public void end(boolean interrupted) {
    }
}
