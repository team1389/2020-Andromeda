package frc.commands;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class TestShooterSpeed extends CommandBase {
    private CANPIDController topPID, bottomPID;
    double topP = 0.;
    double topI = 0;
    double topD = 0;
    double topF = 1/5571;

    double bottomP = 0;
    double bottomI = 0;
    double bottomD = 0;
    double bottomF = 1/5571;

    double topSpeed = 5000;
    double bottomSpeed = 3000;

    double leftFeedForwardInVolts = topSpeed/473;
    double rightFeedForwardInVolts = bottomSpeed/473;

    public TestShooterSpeed() {
        addRequirements(Robot.shooter);
        topPID = Robot.shooter.getShooterTopPIDController();
        bottomPID = Robot.shooter.getShooterBottomPIDController();
    }

    @Override
    public void initialize() {
        SmartDashboard.putNumber("topP", topP);
        SmartDashboard.putNumber("topI", topI);
        SmartDashboard.putNumber("topD", topD);
        SmartDashboard.putNumber("topF", topF);
        topPID.setP(topP);
        topPID.setI(topI);
        topPID.setD(topD);
        topPID.setFF(topF);

        bottomPID.setP(bottomP);
        bottomPID.setI(bottomI);
        bottomPID.setD(bottomD);
        bottomPID.setFF(bottomF);

        SmartDashboard.putNumber("bottomP", bottomP);
        SmartDashboard.putNumber("bottomI", bottomI);
        SmartDashboard.putNumber("bottomD", bottomD);
        SmartDashboard.putNumber("bottomF", bottomF);

    }

    @Override
    public void execute() {
        topPID.setP(SmartDashboard.getNumber("topP", 0));
        topPID.setI(SmartDashboard.getNumber("topI", 0));
        topPID.setD(SmartDashboard.getNumber("topD", 0));
        topPID.setFF(SmartDashboard.getNumber("topF", 0));

        bottomPID.setP(SmartDashboard.getNumber("bottomP", 0));
        bottomPID.setI(SmartDashboard.getNumber("bottomI", 0));
        bottomPID.setD(SmartDashboard.getNumber("bottomD", 0));
        bottomPID.setFF(SmartDashboard.getNumber("bottomF", 0));

        topPID.setReference(topSpeed, ControlType.kVelocity, 0, leftFeedForwardInVolts);
        bottomPID.setReference(topSpeed, ControlType.kVelocity, 0, rightFeedForwardInVolts);

    }
}
