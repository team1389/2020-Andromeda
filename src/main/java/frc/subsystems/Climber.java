package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

/**
 * IMPORTANT: Make sure the rope is wrapped the right way when running the climber. Ask Jorian for right direction
 */
public class Climber extends SubsystemBase {

    public final DoubleSolenoid extender;
    public CANSparkMax leftMotor;
    public CANSparkMax rightMotor;


    public Climber() {                  // Here
        extender = new DoubleSolenoid(RobotMap.PCM_CAN, RobotMap.CLIMBER_FORWARD_SOLENOID,
                RobotMap.CLIMBER_REVERSE_SOLENOID);
        leftMotor = new CANSparkMax(RobotMap.CLIMBER_LEFT_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        leftMotor.restoreFactoryDefaults();
        leftMotor.setInverted(true);
        rightMotor = new CANSparkMax(RobotMap.CLIMBER_RIGHT_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
        rightMotor.restoreFactoryDefaults();

    }

    public void extend() {
        extender.set(DoubleSolenoid.Value.kForward);
    }

    public void retract()
    {
        extender.set(DoubleSolenoid.Value.kReverse);
    }


    public void winch(double percent){
        leftMotor.set(percent);
        rightMotor.set(percent);
    }

}
