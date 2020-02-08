package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Climber extends SubsystemBase {

    private final DoubleSolenoid rSolenoid, lSolenoid;
    private final CANSparkMax spark;

    public Climber() {                  // Here
        rSolenoid = new DoubleSolenoid(RobotMap.can_PCM_2, RobotMap.CLIMBER_RIGHT_FORWARD_SOLENOID,
                RobotMap.CLIMBER_RIGHT_REVERSE_SOLENOID);
        lSolenoid = new DoubleSolenoid(RobotMap.can_PCM_2, RobotMap.CLIMBER_LEFT_FORWARD_SOLENOID,
                RobotMap.CLIMBER_LEFT_REVERSE_SOLENOID);
        spark = new CANSparkMax(RobotMap.CLIMBER_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public void extend() {
        rSolenoid.set(DoubleSolenoid.Value.kForward);
        lSolenoid.set(DoubleSolenoid.Value.kForward);
        spark.set(1);
    }

    public void retract() {
        rSolenoid.set(DoubleSolenoid.Value.kReverse);
        lSolenoid.set(DoubleSolenoid.Value.kReverse);
    }

}
