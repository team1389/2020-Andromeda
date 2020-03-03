package frc.commands;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class TestShooterSpeed extends CommandBase {
    private CANPIDController topPID, bottomPID;

    public TestShooterSpeed() {
        addRequirements(Robot.shooter);
        topPID = Robot.shooter.getShooterTopPIDController();
        bottomPID = Robot.shooter.getShooterBottomPIDController();
    }

    @Override
    public void initialize() {
        topPID.setP(0.00000000000000000001);
        topPID.setFF(1/5571);

        bottomPID.setP(0.00000000000000000001);
        bottomPID.setFF(1/5571);

        topPID.setReference(3000, ControlType.kVelocity);
        bottomPID.setReference(2000, ControlType.kVelocity);

    }
}
