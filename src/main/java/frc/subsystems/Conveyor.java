package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Conveyor extends SubsystemBase {
    public VictorSPX conveyorMotorA;
    public VictorSPX conveyorMotorB;

    public Conveyor() {
        conveyorMotorA = new VictorSPX(RobotMap.CONVEYOR_MOTOR_A);
        conveyorMotorA.setInverted(true);
        conveyorMotorB = new VictorSPX(RobotMap.CONVEYOR_MOTOR_B);
        conveyorMotorB.setInverted(true);

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
