package frc.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Indexer extends SubsystemBase {   //The motor to stop the balls is a BAG Motor
    public DigitalInput indexBeamBreak;
    public CANSparkMax indexMotor;

    public Indexer() {
        indexBeamBreak = new DigitalInput(RobotMap.DIO_INDEX_BEAM_BREAK); //parameter is the port
        indexMotor = new CANSparkMax(RobotMap.INDEX_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);
    }

    public boolean ballDetected() {
        return indexBeamBreak.get();
    }

    public void runForward() {
        indexMotor.set(1);
    }

    public void runBackward() {
        indexMotor.set(-0.5);
    }

}
