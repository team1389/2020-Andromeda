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

    private CANSparkMax shooterTopLeader;
    private CANSparkMax shooterBottomFollower;
    private DigitalInput shooterBeamBreak;
    private double kP = 0.000350;
    private double kI = 0.000001;
    private int kD = 0;
    private CANPIDController pid;

    public Shooter() {
        shooterTopLeader = new CANSparkMax(RobotMap.SHOOTER_TOP, CANSparkMaxLowLevel.MotorType.kBrushless);
        shooterTopLeader.restoreFactoryDefaults();
        shooterBottomFollower = new CANSparkMax(RobotMap.SHOOTER_BOTTOM, CANSparkMaxLowLevel.MotorType.kBrushless);
        shooterBottomFollower.restoreFactoryDefaults();
        shooterBottomFollower.follow(shooterTopLeader);

        shooterBeamBreak = new DigitalInput(RobotMap.DIO_SHOOTER_BEAM_BREAK);

        pid = new CANPIDController(shooterTopLeader);
        pid.setP(kP);
        pid.setI(kI);
        pid.setD(kD);
    }

    public void setShooterVoltage(double percent) {
        shooterTopLeader.set(percent);
    }

    public void stopMotors() {
        shooterTopLeader.set(0);
        shooterBottomFollower.set(0);
    }

    public CANPIDController getShooterTopPIDController() {
        return pid;
    }

    public double getShooterTopRPM() {
        return shooterTopLeader.getEncoder().getVelocity();
    }

    public double getShooterBottomRPM() {
        return shooterBottomFollower.getEncoder().getVelocity();
    }

    public boolean ballInShooter() {
        return !shooterBeamBreak.get();
    }
    public double shootDistance(double distance, int slot){
        double ShooterSpeed=0;
        switch (slot) {
            case 1: ShooterSpeed = distance/10; break;
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
        SmartDashboard.putBoolean("Ball in shooter", ballInShooter());
    }
}