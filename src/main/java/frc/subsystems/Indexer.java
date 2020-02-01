package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Indexer extends SubsystemBase {   //The motor to stop the balls is a BAG Motor
    public DigitalInput di;
    public CANSparkMax indexMotor;
//Forward and Backward is used to specify the percent power of the motor which we are using.
    public double forward = 0.5;
    public double backward = -0.5;

    public Indexer() {
        di = new DigitalInput(0); //parameter is the port
        indexMotor = new CANSparkMax(RobotMap.INDEX_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushless);

    }

    public void checkerForward() {
        //returns true if it is open
        if (di.get()) {
            indexMotor.set(forward);
        }
    }
    public void checkerBackward() {
        //returns true if it is open
        if (di.get()) {
            indexMotor.set(backward);
        }
    }

}
