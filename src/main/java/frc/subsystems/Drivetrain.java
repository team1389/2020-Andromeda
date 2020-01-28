package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Drivetrain extends SubsystemBase {
    private DifferentialDrive differentialDrive;
    private SpeedControllerGroup right, left;
    private CANSparkMax rightA, rightB, leftA, leftB;

    public Drivetrain() {
        rightA = new CANSparkMax(RobotMap.RIGHT_DRIVE_A, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightB = new CANSparkMax(RobotMap.RIGHT_DRIVE_B, CANSparkMaxLowLevel.MotorType.kBrushless);
        right = new SpeedControllerGroup(rightA, rightB);

        leftA = new CANSparkMax(RobotMap.LEFT_DRIVE_A, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftB = new CANSparkMax(RobotMap.LEFT_DRIVE_B, CANSparkMaxLowLevel.MotorType.kBrushless);
        left = new SpeedControllerGroup(leftA, leftB);

        differentialDrive = new DifferentialDrive(left, right);
    }

    public void drive(double leftY, double rightX, boolean leftBumper) {
        differentialDrive.curvatureDrive(leftY, rightX, leftBumper);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run; use it for debugging and stuff
    }
}
