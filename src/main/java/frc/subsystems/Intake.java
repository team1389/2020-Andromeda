package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Intake extends SubsystemBase {
    private DoubleSolenoid leftExtender, rightExtender;
    private TalonSRX intakeMotor;

    public Intake() {
        intakeMotor = new TalonSRX(RobotMap.INTAKE_MOTOR);
        leftExtender = new DoubleSolenoid(RobotMap.INTAKE_LEFT_FORWARD_SOLENOID, RobotMap.INTAKE_LEFT_REVERSE_SOLENOID);
        rightExtender = new DoubleSolenoid(RobotMap.INTAKE_RIGHT_FORWARD_SOLENOID,
                RobotMap.INTAKE_RIGHT_REVERSE_SOLENOID);
    }

    public void extendIntake() {
        leftExtender.set(DoubleSolenoid.Value.kForward);
        rightExtender.set(DoubleSolenoid.Value.kForward);
    }

    public void startIntaking() {
        intakeMotor.set(ControlMode.PercentOutput, 0.75);
    }

    public void stopIntaking() {
        intakeMotor.set(ControlMode.PercentOutput, 0);
    }
}