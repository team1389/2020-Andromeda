package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Conveyor extends SubsystemBase {
    private TalonSRX conveyorMotor;

    public Conveyor() {
        conveyorMotor = new TalonSRX(RobotMap.CONVEYOR_MOTOR);

        conveyorMotor.setInverted(true);
    }

    public void startConveying() {
        conveyorMotor.set(ControlMode.PercentOutput, 0.5);
    }

    public void stopConveying() {
        conveyorMotor.set(ControlMode.PercentOutput, 0);
    }
}
