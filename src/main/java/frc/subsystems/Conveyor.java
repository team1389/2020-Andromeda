package frc.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Conveyor extends SubsystemBase {
    //Front of the robot is the intake side
    public VictorSPX conveyorMotorFront;
    public VictorSPX conveyorMotorBack;
    private  DigitalInput bottomConveyorBeamBreak;
    private DigitalInput secondBallPositionBeamBreak;

    public Conveyor() {
        conveyorMotorFront = new VictorSPX(RobotMap.CONVEYOR_MOTOR_FRONT);
        conveyorMotorBack = new VictorSPX(RobotMap.CONVEYOR_MOTOR_BACK);
        secondBallPositionBeamBreak = new DigitalInput(RobotMap.DIO_SECOND_BALL_INDEX);
        bottomConveyorBeamBreak = new DigitalInput(RobotMap.DIO_INTAKE_BEAM_BREAK);

    }
    /**
     *
     * @return second ball is at the stopping point
     */
    public boolean ballAtBottomConveyor() {
        return !bottomConveyorBeamBreak.get();
    }

    public boolean ballAtSecondPosition(){
        return !secondBallPositionBeamBreak.get();
    }

    public void runBottomConveyor(double percent) {
        conveyorMotorBack.set(ControlMode.PercentOutput, percent);

    }

    public void runTopConveyor(double percent) {
        conveyorMotorFront.set(ControlMode.PercentOutput, percent);
    }

    public void stopConveyor() {
        conveyorMotorFront.set(ControlMode.PercentOutput, 0);
        conveyorMotorBack.set(ControlMode.PercentOutput, 0);

    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Ball at conveyor bottom", !bottomConveyorBeamBreak.get());
    }
}
