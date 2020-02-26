package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Conveyor extends SubsystemBase {
    //Front of the robot is the intake side
    public VictorSPX conveyorMotorFront;
    public VictorSPX conveyorMotorBack;
    private DigitalInput secondBallPositionBeamBreak;

    public Conveyor() {
        conveyorMotorFront = new VictorSPX(RobotMap.CONVEYOR_MOTOR_FRONT);
        conveyorMotorBack = new VictorSPX(RobotMap.CONVEYOR_MOTOR_BACK);
        secondBallPositionBeamBreak = new DigitalInput(RobotMap.DIO_SECOND_BALL_INDEX);


    }
    /**
     *
     * @return second ball is at the stopping point
     */
    public boolean ballAtSecondPosition(){
        return !secondBallPositionBeamBreak.get();
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
