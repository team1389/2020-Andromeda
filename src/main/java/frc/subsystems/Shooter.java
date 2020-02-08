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
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase {

    public final CANSparkMax shooterTop;
    public final CANSparkMax shooterBottom;
    public int kP = 1;
    public int kI = 0;
    public int kD = 0;
    public CANPIDController pid;

    public Shooter() {
        shooterTop = new CANSparkMax(RobotMap.SHOOTER_TOP, CANSparkMaxLowLevel.MotorType.kBrushless);
        shooterBottom = new CANSparkMax(RobotMap.SHOOTER_BOTTOM, CANSparkMaxLowLevel.MotorType.kBrushless);
        shooterBottom.follow(shooterTop);

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
}