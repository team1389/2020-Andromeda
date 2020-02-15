package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Intake extends SubsystemBase {
    private DoubleSolenoid leftExtender, rightExtender;
    private VictorSPX intakeMotor;
    private DigitalInput intakeBeamBreak;

    public int ballCounter = 3;


    public Intake() {
        intakeMotor = new VictorSPX(RobotMap.INTAKE_MOTOR);
        leftExtender = new DoubleSolenoid(RobotMap.INTAKE_LEFT_FORWARD_SOLENOID, RobotMap.INTAKE_LEFT_REVERSE_SOLENOID);
        rightExtender = new DoubleSolenoid(RobotMap.INTAKE_RIGHT_FORWARD_SOLENOID,
                RobotMap.INTAKE_RIGHT_REVERSE_SOLENOID);
        intakeBeamBreak = new DigitalInput(0);
    }

    public void extendIntake() {
        leftExtender.set(DoubleSolenoid.Value.kForward);
        rightExtender.set(DoubleSolenoid.Value.kForward);
    }

    public void runIntake(double percent) {
        intakeMotor.set(ControlMode.PercentOutput, percent);
    }

    public void stopIntaking() {
        intakeMotor.set(ControlMode.PercentOutput, 0);
    }

    public void checkForBalls() {
        if(intakeBeamBreak.get())
            ballCounter++;


    }
}
