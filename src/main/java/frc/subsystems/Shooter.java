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

    public final CANSparkMax shooterLeft;
    public final CANSparkMax shooterRight;
    public int kP = 1;
    public int kI = 0;
    public int kD = 0;
    public CANPIDController pid;

    public Shooter() {
        shooterLeft = new CANSparkMax(RobotMap.FRONT_SHOOTER_LEFT, CANSparkMaxLowLevel.MotorType.kBrushless);
        shooterRight = new CANSparkMax(RobotMap.FRONT_SHOOTER_RIGHT, CANSparkMaxLowLevel.MotorType.kBrushless);
        shooterRight.follow(shooterLeft);

        pid = new CANPIDController(shooterLeft);
        pid.setP(kP);
        pid.setI(kI);
        pid.setD(kD);
        shooterRight.setInverted(true);
    }

    public void setShooterVoltage(double percent) {
        shooterLeft.set(percent);

    }

    public void stopMotors() {
        shooterLeft.set(0);
        shooterRight.set(0);
    }

    public CANPIDController getShooterLeftPIDController() {
        return pid;
    }

    public double getShooterLeftRPM() {
        return shooterLeft.getEncoder().getVelocity();
    }

    public double getShooterRightRPM() {
        return shooterRight.getEncoder().getVelocity();
    }
}