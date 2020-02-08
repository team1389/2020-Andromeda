package frc.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Drivetrain extends SubsystemBase {
    public AHRS ahrs = new AHRS(SerialPort.Port.kMXP);
    private DifferentialDrive differentialDrive;
    private CANSparkMax rightLeader, rightFollower, leftLeader, leftFollower;
    private CANEncoder leftLeaderEncoder, rightLeaderEncoder;

    public Drivetrain() {
        rightLeader = new CANSparkMax(RobotMap.RIGHT_DRIVE_LEADER, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightFollower = new CANSparkMax(RobotMap.RIGHT_DRIVE_FOLLOWER, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightFollower.follow(rightLeader, rightFollower.getInverted());


        leftLeader = new CANSparkMax(RobotMap.LEFT_DRIVE_LEADER, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftFollower = new CANSparkMax(RobotMap.LEFT_DRIVE_FOLLOWER, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftFollower.follow(leftLeader, leftFollower.getInverted());

        differentialDrive = new DifferentialDrive(leftLeader, rightLeader);

        leftLeaderEncoder = new CANEncoder(leftLeader);
        rightLeaderEncoder = new CANEncoder(rightLeader);

        leftLeaderEncoder.setPosition(0);
        rightLeaderEncoder.setPosition(0);
        ahrs.reset();
    }

    public double leftLeaderEncoder() {
        return leftLeaderEncoder.getPosition();
    }

    public double rightLeaderEncoder() {
        return rightLeaderEncoder.getPosition();
    }

    public void drive(double leftY, double rightX, boolean isQuickTurn) {
        differentialDrive.curvatureDrive(leftY, rightX, isQuickTurn);
    }

    public void set(double leftPower, double rightPower) {
        leftLeader.set(leftPower);
        rightLeader.set(-rightPower);
    }

    public double encoderCountsPerRevolution() {
        return leftLeaderEncoder.getCountsPerRevolution();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run; use it for debugging and stuff
    }

}
