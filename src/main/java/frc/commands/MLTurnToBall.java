package frc.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.utils.SizeLimitedQueue;

public class MLTurnToBall extends CommandBase {
    double kP = 0.5;
    double kI = 0.05;
    double kD = 0;
    PIDController pid;
//    Timer timer;

    public MLTurnToBall() {
        addRequirements(Robot.ml);
        pid = new PIDController(kP, kI, kD);
//        SmartDashboard.putNumber("kP", kP);
//        SmartDashboard.putNumber("kI", kI);
//        SmartDashboard.putNumber("kD", kD);
//        timer = new Timer();
    }

    @Override
    public void initialize() {
//        timer.reset();
//        timer.start();
    }

    @Override
    public void execute() {
        double error = Robot.ml.movement();
        double power = pid.calculate(error,0);
        SmartDashboard.putNumber("power", power);
        SmartDashboard.putNumber("ML error", error);
        //pid.setP(SmartDashboard.getNumber("kP", 0));
        //pid.setI(SmartDashboard.getNumber("kI", 0));
        //pid.setD(SmartDashboard.getNumber("kD", 0));

        Robot.drivetrain.set(-power,power);
    }

    @Override
    public boolean isFinished() {
        return false;

    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("ML Turn to ball ended");
        SmartDashboard.putBoolean("finished", true);
    }
}
