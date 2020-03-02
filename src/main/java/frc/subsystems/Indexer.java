package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Indexer extends SubsystemBase {   //The motor to stop the balls is a BAG Motor

    private DigitalInput indexPreBeamBreak;
    private VictorSPX indexMotor;

    public Indexer() {
        indexPreBeamBreak = new DigitalInput(RobotMap.DIO_PRE_INDEX_BEAM_BREAK);
        indexMotor = new VictorSPX(RobotMap.INDEX_MOTOR);
    }

    public boolean ballAtIndexer() {
        return !indexPreBeamBreak.get();
    }



    public void runIndexer(double percent) {
        indexMotor.set(ControlMode.PercentOutput, percent);
    }

    public void stopIndexer() {
        indexMotor.set(ControlMode.PercentOutput, 0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Pre index", ballAtIndexer());
    }
}
