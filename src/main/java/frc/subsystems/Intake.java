package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Intake extends SubsystemBase {
    private double intakePercent = 0.75;
    private VictorSPX intakeMotor;

    public Intake() {
        intakeMotor = new VictorSPX(RobotMap.INTAKE_MOTOR);
    }


    public void runIntake() {
        intakeMotor.set(ControlMode.PercentOutput, intakePercent);
    }
    public void runIntake(double intakePercent){
        intakeMotor.set(ControlMode.PercentOutput, intakePercent);
    }
    public void runOuttake(){
        intakeMotor.set(ControlMode.PercentOutput, -1);
    }
    public void stopIntaking() {
        intakeMotor.set(ControlMode.PercentOutput, 0);
    }
}