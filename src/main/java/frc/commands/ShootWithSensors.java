package frc.commands;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.Robot;
import frc.utils.SizeLimitedQueue;

public class ShootWithSensors extends SequentialCommandGroup {

    private double shooterTargetRPM;
    private double bottomTargetRPM;
    private double conveyorPercent;
    private CANPIDController topPID, bottomPID;
    private double scale = 0.8;
    public ShootWithSensors(ShootType type, double distanceOrSpeedValue, int slot) {
        addRequirements(Robot.shooter, Robot.conveyor, Robot.indexer);
        if (type == ShootType.Distance)
            shooterTargetRPM = Robot.shooter.shootDistance(distanceOrSpeedValue, slot);
        else if (type == ShootType.Speed)
            shooterTargetRPM = distanceOrSpeedValue;
        topPID = Robot.shooter.getShooterTopPIDController();
        bottomPID = Robot.shooter.getShooterBottomPIDController();
        bottomTargetRPM = shooterTargetRPM * scale;

        conveyorPercent = 0.6;

        //TODO: Test if we need a wait time after running the indexer (i.e. if the indexer speed affects shot distance)
        addCommands(new WaitUntilAtSpeed(shooterTargetRPM, bottomTargetRPM),new InstantCommand(() -> Robot.indexer.runIndexer(1)), new InstantCommand(() -> Robot.conveyor.runConveyor(conveyorPercent)));


    }

    @Override
    public void initialize() {
        super.initialize();
        topPID.setReference(shooterTargetRPM, ControlType.kVelocity);
        bottomPID.setReference(bottomTargetRPM, ControlType.kVelocity);
    }

    @Override
    public void end(boolean interrupted) {
        Robot.shooter.stopMotors();
        Robot.indexer.stopIndexer();
        Robot.conveyor.stopConveyor();
        System.out.println("Killed Shoot With Sensors");
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    public enum ShootType {
        Distance,
        Speed
    }



    public static class WaitUntilAtSpeed extends CommandBase {

        private double shooterTargetRPM, bottomTargetRPM;
        private double tolerance;
        private SizeLimitedQueue recentTopErrors = new SizeLimitedQueue(15);
        private SizeLimitedQueue recentBottomErrors = new SizeLimitedQueue(15);

        public WaitUntilAtSpeed(double shooterTargetRPM, double bottomTargetRPM) {
            addRequirements(Robot.shooter);
            this.shooterTargetRPM = shooterTargetRPM;
            this.bottomTargetRPM = bottomTargetRPM;
            tolerance = 3;
        }

        @Override
        public void execute() {
            SmartDashboard.putBoolean("pid-ing", true);

            double topError = shooterTargetRPM - Robot.shooter.getShooterTopRPM();
            recentTopErrors.addElement(topError);
            SmartDashboard.putNumber("average top error", recentTopErrors.getAverage());

            double bottomError = bottomTargetRPM - Robot.shooter.getShooterBottomRPM();
            recentBottomErrors.addElement(bottomError);
            SmartDashboard.putNumber("average bottom error", recentBottomErrors.getAverage());
        }

        @Override
        public void end(boolean interrupted) {
            SmartDashboard.putBoolean("pid-ing", false);
        }

        @Override
        public boolean isFinished() {
            return tolerance >= Math.abs(recentTopErrors.getAverage()) &&
                    tolerance >= Math.abs(recentBottomErrors.getAverage());
        }
    }



}