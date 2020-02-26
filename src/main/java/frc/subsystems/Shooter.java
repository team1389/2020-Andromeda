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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.commands.ShootWithSensors;
import frc.robot.RobotMap;

public class Shooter extends SubsystemBase {

    private CANSparkMax shooterTop;
    private CANSparkMax shooterBottom;
    private double kP = 0.000100; //0.000400;
    private double kI = 0.000001;
    private int kD = 0;
    private CANPIDController topPid;
    private CANPIDController bottomPid;


    public Shooter() {
        shooterTop = new CANSparkMax(RobotMap.SHOOTER_TOP, CANSparkMaxLowLevel.MotorType.kBrushless);
        shooterTop.restoreFactoryDefaults();
        shooterBottom = new CANSparkMax(RobotMap.SHOOTER_BOTTOM, CANSparkMaxLowLevel.MotorType.kBrushless);
        shooterBottom.restoreFactoryDefaults();

        topPid = new CANPIDController(shooterTop);
        topPid.setP(kP);
        topPid.setI(kI);
        topPid.setD(kD);

        bottomPid = new CANPIDController(shooterBottom);
        bottomPid.setP(kP);
        bottomPid.setI(kI);
        bottomPid.setD(kD);
    }

    public void setShooterVoltage(double percent) {
        shooterTop.set(percent);
        shooterBottom.set(percent);
    }

    public void stopMotors() {
        shooterTop.set(0);
        shooterBottom.set(0);
    }

    public CANPIDController getShooterTopPIDController() {
        return topPid;
    }

    public CANPIDController getShooterBottomPIDController() {
        return bottomPid;
    }

    public double getShooterTopRPM() {
        return shooterTop.getEncoder().getVelocity();
    }

    public double getShooterBottomRPM() {
        return shooterBottom.getEncoder().getVelocity();
    }

    public double shootDistance(double distance, int slot){
        double ShooterSpeed=0;
        switch (slot) {
            case 1: ShooterSpeed = distance/10; break;//need testing for exact equation
            case 2: ShooterSpeed = distance/10; break;
            case 3: ShooterSpeed = distance/10; break;
            case 4: ShooterSpeed = distance/10; break;
            case 5: ShooterSpeed = distance/10; break;
            case 6: ShooterSpeed = distance/10; break;
            case 7: ShooterSpeed = distance/10; break;
            case 8: ShooterSpeed = distance/10; break;

        }
        return ShooterSpeed;
    }
    @Override
    public void periodic() {

        SmartDashboard.putNumber("Shooter Top RPM", shooterTop.getEncoder().getVelocity());
    }
}