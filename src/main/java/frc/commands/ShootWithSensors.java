package frc.commands;


import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Robot;

import static frc.robot.Robot.*;

public class ShootWithSensors extends SequentialCommandGroup {

    public ShootWithSensors() {
        addRequirements(shooter);
        addCommands(new ShootOnce());
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stopMotors();
    }

    public static class SpinUpShooters extends CommandBase {
        private CANPIDController pid;
        private double shooterRPM;
        private double shooterTargetRPM;
        private double tolerance, threshold;


        public SpinUpShooters() {
            addRequirements(Robot.shooter);
            pid = Robot.shooter.getShooterTopPIDController();
        }

        @Override
        public void initialize() {
            shooterTargetRPM = 20;
            pid.setReference(shooterTargetRPM, ControlType.kVelocity);

        }

        @Override
        public void execute() {
            shooterRPM = Robot.shooter.getShooterTopRPM();
            System.out.println("Shooter RPM " + shooterRPM);
        }


        //@Override
        /*public boolean isFinished() {
            return tolerance <= Math.abs(shooterTargetRPM - threshold);
        }*/

    }

    private class ShootOnce extends SequentialCommandGroup {
        public ShootOnce() {
            addRequirements(shooter, Robot.indexer);
            addCommands(new SpinUpShooters(), new InstantCommand(() -> indexer.runIndexer(1)), new WaitCommand(0.25),
                    new InstantCommand(() -> conveyor.startConveying(1)), new WaitCommand(0.5));
        }

        @Override
        public void end(boolean interrupted) {
            shooter.stopMotors();
        }
    }


}