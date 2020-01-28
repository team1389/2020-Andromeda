package frc.commands;


import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.subsystems.Drivetrain;
import frc.subsystems.Intake;

public class RunIntake extends CommandBase {
    private Intake intake = null;
    private boolean isIntaking = false;

    public RunIntake() {
        intake = Robot.intake;
        addRequirements(intake);
    }

    @Override
    public void execute() {
        //THIS CODE IS FOR PISTONS, SO IF YOU WANT THEM TO WORK UNCOMMENT THIS FUTURE SELF:
        /*if(Robot.oi.manipStartButton()) {
            intake.leftExtender.set(DoubleSolenoid.Value.kForward);
            intake.rightExtender.set(DoubleSolenoid.Value.kForward);
        }
        else if(Robot.oi.manipBackButton()) {
            intake.leftExtender.set(DoubleSolenoid.Value.kReverse);
            intake.rightExtender.set(DoubleSolenoid.Value.kReverse);
        }*/


        if(Robot.oi.manipLeftTrigger() > 0) {
            intake.startIntaking();
            System.out.println("Jebediah is trying to intake power cells, and probably failing!");
        }
        else {
            intake.stopIntaking();
        }
    }
}
