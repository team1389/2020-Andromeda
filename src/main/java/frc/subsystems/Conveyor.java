package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Conveyor extends SubsystemBase {
    private TalonSRX conveyorMotorA;
    private TalonSRX conveyorMotorB;

    public Conveyor() {
        conveyorMotorA = new TalonSRX(RobotMap.CONVEYOR_MOTOR_A);
        conveyorMotorB = new TalonSRX(RobotMap.CONVEYOR_MOTOR_B);

    }

    public void startConveying(double percent) {
        conveyorMotorA.set(ControlMode.PercentOutput, percent);
        conveyorMotorB.set(ControlMode.PercentOutput, percent);
    }

    public void stopConveying() {
        conveyorMotorA.set(ControlMode.PercentOutput, 0);
        conveyorMotorB.set(ControlMode.PercentOutput, 0);

    }
}
