package frc.robot;


import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.*;
import frc.autos.PowerPortToControlPanel;
//import frc.Autos.ShootAndShieldGenerator;
import frc.autos.ShootAndCrossAutoLine;
import frc.autos.StraightControlPanel;
import frc.commands.SystemsTest;
import frc.subsystems.*;

import javax.naming.ldap.Control;

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
    public static ML ml = new ML();

    //NOTE: OI must be initialized after all the the other systems
    public static Climber climber = new Climber();
    public static OI oi = new OI();
    public SendableChooser<Integer> shooterSlotChooser = new SendableChooser();
    public SendableChooser<Command> autoChooser = new SendableChooser();
    Compressor compressor;

    @Override

    public void robotInit() {

        Shuffleboard.getTab("gyro tab").add(drivetrain.ahrs);
        drivetrain.ahrs.reset();
        climber.retract();
        compressor = new Compressor(RobotMap.PCM_CAN);

        drivetrain.setCoast();

        CameraServer.getInstance().startAutomaticCapture();
        Number tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getNumber(0);
        boolean limelightHasTarget = tv.intValue() > 0;
        SmartDashboard.putBoolean("Has Target", limelightHasTarget);

//        configChoosers();

        //NOTE: This isn't actually turning off the limelight
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);
    }

    /**
     * This function is called every robot packet, no matter the mode. Use
     * this for items like diagnostics that you want ran during disabled,
     * autonomous, teleoperated and test.
     */
    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();

        SmartDashboard.putBoolean("In Range", NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0) == 1);
    }


    @Override
    public void autonomousInit() {
         //This is so 0 is the heading of robot on start of auto
        Robot.intake.stopIntaking();
        Robot.drivetrain.setBrake();
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(3);
        //CommandScheduler.getInstance().schedule(new StraightControlPanel());
        CommandScheduler.getInstance().schedule(new StraightControlPanel());
    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {

    }

    @Override
    public void teleopInit() {
        drivetrain.setCoast();
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);

    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
       // Robot.indexer.runIndexer(1);

    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {


    }

    public void configChoosers() {

        //Running this kills the code. IDK why. Nice comment
        shooterSlotChooser.addOption("Slot 1", 1);
        shooterSlotChooser.addOption("Slot 2", 2);
        shooterSlotChooser.addOption("Slot 3", 3);
        shooterSlotChooser.addOption("Slot 4", 4);
        shooterSlotChooser.addOption("Slot 5", 5);
        shooterSlotChooser.addOption("Slot 6", 6);
        shooterSlotChooser.addOption("Slot 7", 7);
        shooterSlotChooser.addOption("Slot 8", 8);
        SmartDashboard.putData(shooterSlotChooser);

        autoChooser.addOption("in front shoot -> control panel", new PowerPortToControlPanel());
        autoChooser.addOption("Basic Shoot and cross auto line", new ShootAndCrossAutoLine());
        //autoChooser.addOption("left shoot -> Shield gen", new ShootAndShieldGenerator());
        autoChooser.addOption("shoot -> control panel", new StraightControlPanel());
        autoChooser.setDefaultOption("Basic Shoot and Cross auto line", new ShootAndCrossAutoLine());
        SmartDashboard.putData("Autonomous Chooser", autoChooser);
    }
}