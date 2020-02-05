package frc.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Drivetrain extends SubsystemBase {
    public AHRS ahrs = new AHRS(SerialPort.Port.kMXP);
    private DifferentialDrive differentialDrive;
    private SpeedControllerGroup right, left;
    private CANSparkMax rightA, rightB, leftA, leftB;
    private CANEncoder leftAEncoder, leftBEncoder, rightAEncoder, rightBEncoder;

    public Drivetrain() {
        rightA = new CANSparkMax(RobotMap.RIGHT_DRIVE_A, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightB = new CANSparkMax(RobotMap.RIGHT_DRIVE_B, CANSparkMaxLowLevel.MotorType.kBrushless);
        right = new SpeedControllerGroup(rightA, rightB);

        leftA = new CANSparkMax(RobotMap.LEFT_DRIVE_A, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftB = new CANSparkMax(RobotMap.LEFT_DRIVE_B, CANSparkMaxLowLevel.MotorType.kBrushless);
        left = new SpeedControllerGroup(leftA, leftB);

        differentialDrive = new DifferentialDrive(left, right);

        leftAEncoder = new CANEncoder(leftA);
        leftBEncoder = new CANEncoder(leftB);
        rightAEncoder = new CANEncoder(rightA);
        rightBEncoder = new CANEncoder(rightB);

        leftAEncoder.setPosition(0);

        ahrs.reset();
    }

    public double leftAEncoder() {
        return leftAEncoder.getPosition();
    }

    public void drive(double leftY, double rightX, boolean leftBumper) {
        differentialDrive.curvatureDrive(leftY, rightX, leftBumper);
    }

    public void set(double leftPower, double rightPower) {
        leftA.set(leftPower);
        leftB.follow(leftA);
        //leftB.set(leftPower);

        rightA.set(-rightPower);
        //rightB.set(-rightPower);
        rightB.follow(rightA);
    }

    public double encoderCountsPerRevolution() {
        return leftAEncoder.getCountsPerRevolution();
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run; use it for debugging and stuff
    }

}
