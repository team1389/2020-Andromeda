package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;


public class Conveyor extends SubsystemBase {
    //Front of the robot is the intake side
    public VictorSPX conveyorMotorFront;
    public VictorSPX conveyorMotorBack;

    public Conveyor() {
        conveyorMotorFront = new VictorSPX(RobotMap.CONVEYOR_MOTOR_FRONT);
        conveyorMotorBack = new VictorSPX(RobotMap.CONVEYOR_MOTOR_BACK);

    }

    public void runConveyor(double percent) {
        conveyorMotorFront.set(ControlMode.PercentOutput, percent);
        conveyorMotorBack.set(ControlMode.PercentOutput, percent);

    }

    public void stopConveyor() {
        conveyorMotorFront.set(ControlMode.PercentOutput, 0);
        conveyorMotorBack.set(ControlMode.PercentOutput, 0);

    }
}
