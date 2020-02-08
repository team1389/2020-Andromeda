/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;


import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase {

    private CANSparkMax shooterTop;
    private CANSparkMax shooterBottom;
    private DigitalInput shooterBeamBreak;
    private double kP = 0.001;
    private double kI = 0;
    private double kD = 0;
    private CANPIDController pid;

    public Shooter() {
        shooterTop = new CANSparkMax(RobotMap.SHOOTER_TOP, CANSparkMaxLowLevel.MotorType.kBrushless);
        shooterBottom = new CANSparkMax(RobotMap.SHOOTER_BOTTOM, CANSparkMaxLowLevel.MotorType.kBrushless);
        shooterBottom.follow(shooterTop);

        shooterBeamBreak = new DigitalInput(RobotMap.DIO_SHOOTER_BEAM_BREAK);

        pid = new CANPIDController(shooterTop);
        pid.setP(kP);
        pid.setI(kI);
        pid.setD(kD);
    }

    public void setShooterVoltage(double percent) {
        shooterTop.set(percent);

    }

    public void stopMotors() {
        shooterTop.set(0);
        shooterBottom.set(0);
    }

    public CANPIDController getShooterTopPIDController() {
        return pid;
    }

    public double getShooterTopRPM() {
        return shooterTop.getEncoder().getVelocity();
    }

    public double getShooterBottomRPM() {
        return shooterBottom.getEncoder().getVelocity();
    }

    public boolean isBallShot() {
        //The councils still out on this name choice
        return shooterBeamBreak.get();
    }
}