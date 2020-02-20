package frc.robot;


import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.subsystems.*;

/**
 * Don't change the name of this class since the VM is set up to run this
 */
public class Robot extends TimedRobot {

    /**
     * Initialize all systems here as public & static.
     * Ex: public static System system = new System();
     */


    public static Drivetrain drivetrain = new Drivetrain();
    public static Conveyor conveyor = new Conveyor();
    public static Indexer indexer = new Indexer();
    public static Intake intake = new Intake();
    public static Shooter shooter = new Shooter();
    //NOTE: OI must be initialized after all the the other systems
    public static Climber climber = new Climber();

    public static OI oi = new OI();

    public SendableChooser shooterSlotChooser = new SendableChooser();
    public SendableChooser<Command> autoChooser = new SendableChooser();

    @Override

    public void robotInit() {
        Shuffleboard.getTab("gyro tab").add(drivetrain.ahrs);

        shooterSlotChooser.addOption("Slot 1", 1);
        shooterSlotChooser.addOption("Slot 2", 2);
        shooterSlotChooser.addOption("Slot 3", 3);
        shooterSlotChooser.addOption("Slot 4", 4);
        shooterSlotChooser.addOption("Slot 5", 5);
        shooterSlotChooser.addOption("Slot 6", 6);
        shooterSlotChooser.addOption("Slot 7", 7);
        shooterSlotChooser.addOption("Slot 8", 8);
        SmartDashboard.putData(shooterSlotChooser);
        SmartDashboard.putData("Autonomous Chooser", autoChooser);
    }

    /**
     * This function is called every robot packet, no matter the mode. Use
     * this for items like diagnostics that you want ran during disabled,
     * autonomous, teleoperated and test.
     */
    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();

    }


    @Override
    public void autonomousInit() {
        intake.extendIntake();
        //Example of setting auto: CommandScheduler.getInstance().schedule(YOUR AUTO);
        CommandScheduler.getInstance().schedule(autoChooser.getSelected());
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {

    }
}