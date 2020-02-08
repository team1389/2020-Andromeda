package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Indexer extends SubsystemBase {   //The motor to stop the balls is a BAG Motor

    private VictorSPX indexMotor;

    public Indexer() {
        indexMotor = new VictorSPX(RobotMap.INDEX_MOTOR);
        indexMotor.setInverted(true);
    }


    public void runIndexer(double percent) {
        indexMotor.set(ControlMode.PercentOutput, percent);
    }

    public void stopIndexer() {
        indexMotor.set(ControlMode.PercentOutput, 0);
    }
}
