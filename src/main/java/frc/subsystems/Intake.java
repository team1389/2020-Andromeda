package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Intake extends SubsystemBase {
    private DoubleSolenoid extender;
    private VictorSPX intakeMotor;

    public Intake() {
        intakeMotor = new VictorSPX(RobotMap.INTAKE_MOTOR);
        extender = new DoubleSolenoid(RobotMap.PCM_CAN, RobotMap.INTAKE_FORWARD_SOLENOID,
                RobotMap.INTAKE_REVERSE_SOLENOID);
    }

    public void extendIntake() {
        extender.set(DoubleSolenoid.Value.kForward);
    }
    public void retractIntake(){
        extender.set(DoubleSolenoid.Value.kReverse);
    }

    public void runIntake(double percent) {
        intakeMotor.set(ControlMode.PercentOutput, percent);
    }

    public void stopIntaking() {
        intakeMotor.set(ControlMode.PercentOutput, 0);
    }
}