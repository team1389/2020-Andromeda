package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Indexer extends SubsystemBase {   //The motor to stop the balls is a BAG Motor

    private final DigitalInput indexPreBeamBreak;
    private final DigitalInput indexPostBeamBreak;
    private final VictorSPX indexMotor;

    public Indexer() {
        indexPreBeamBreak = new DigitalInput(RobotMap.DIO_INDEX_PRE_BEAM_BREAK); //parameter is the port
        indexPostBeamBreak = new DigitalInput(RobotMap.DIO_INDEX_POST_BEAM_BREAK); //parameter is the port
        indexMotor = new VictorSPX(RobotMap.INDEX_MOTOR);
    }

    public boolean preBallDetector() {
        return indexPreBeamBreak.get();
    }
    public boolean postBallDetector() {
        return indexPostBeamBreak.get();
    }

    public void runIndexer(double percent) {
        indexMotor.set(ControlMode.PercentOutput, percent);
    }

    public void stopIndexer() {
        indexMotor.set(ControlMode.PercentOutput, 0);
    }
}
