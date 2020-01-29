/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase {

    private final TalonSRX shooterLeft;
    private final TalonSRX shooterRight;
    private final TalonSRX indexerLeft;
    private final TalonSRX indexerRight;


    public Shooter() {
        shooterLeft = new TalonSRX(RobotMap.FRONT_SHOOTER_LEFT);
        shooterRight = new TalonSRX(RobotMap.FRONT_SHOOTER_RIGHT);
        indexerLeft = new TalonSRX(RobotMap.BACK_SHOOTER_LEFT);
        indexerRight = new TalonSRX(RobotMap.BACK_SHOOTER_RIGHT);

        shooterRight.setInverted(true);
        indexerRight.setInverted(true);
    }

    public void setShooterVoltage(double percent) {
        shooterLeft.set(ControlMode.PercentOutput, percent);
        shooterRight.set(ControlMode.PercentOutput, percent);
    }

    public void setIndexerVoltage(double percent) {
        indexerLeft.set(ControlMode.PercentOutput, percent);
        indexerRight.set(ControlMode.PercentOutput, percent);
    }

    public void stopMotors() {
        shooterLeft.set(ControlMode.PercentOutput, 0);
        shooterRight.set(ControlMode.PercentOutput, 0);
        indexerLeft.set(ControlMode.PercentOutput, 0);
        indexerRight.set(ControlMode.PercentOutput, 0);
    }

}