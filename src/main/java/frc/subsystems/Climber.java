package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Climber extends SubsystemBase {

    public final DoubleSolenoid rSolenoid, lSolenoid;
    private CANSparkMax leftMotor;
    private CANSparkMax rightMotor;


    public Climber() {                  // Here
        rSolenoid = new DoubleSolenoid(RobotMap.PCM_CAN, RobotMap.CLIMBER_RIGHT_FORWARD_SOLENOID,
                RobotMap.CLIMBER_RIGHT_REVERSE_SOLENOID);
        lSolenoid = new DoubleSolenoid(RobotMap.PCM_CAN,  RobotMap.CLIMBER_LEFT_FORWARD_SOLENOID,
                RobotMap.CLIMBER_LEFT_REVERSE_SOLENOID);
        leftMotor = new CANSparkMax(RobotMap.CLIMBER_LEFT_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftMotor.restoreFactoryDefaults();
        rightMotor = new CANSparkMax(RobotMap.CLIMBER_RIGHT_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightMotor.restoreFactoryDefaults();
    }

    public void extend() {
        rSolenoid.set(DoubleSolenoid.Value.kForward);
        lSolenoid.set(DoubleSolenoid.Value.kForward);
    }

    public void winch(double percent){
        leftMotor.set(percent);
        rightMotor.set(percent);
    }
    public void retract() {
        rSolenoid.set(DoubleSolenoid.Value.kReverse);
        lSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

}
