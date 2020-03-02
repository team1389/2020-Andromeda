package frc.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class MLMoveToBall extends CommandBase {
    double kP;
    double kI;
    double kD;
    PIDController pid;


    public MLMoveToBall() {
        addRequirements(Robot.ml);
        pid = new PIDController(0.5, 0.05, 0);
    }
    @Override
    public void execute() {
        double error = Robot.ml.movement();
        double power = pid.calculate(error,0);
//        SmartDashboard.putNumber("ML error", error);
        if (Math.abs(power) < 0.05) {
            power += 0.6;
        }
        Robot.drivetrain.set(-power,power);
    }

    @Override
    public void end(boolean interrupted) {
    }
}
