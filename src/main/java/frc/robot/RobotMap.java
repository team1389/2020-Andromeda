package frc.robot;

/**
 * define Hardware Ports in here
 */
public class RobotMap {
    public static int SHOOTER_TOP;
    public static int SHOOTER_BOTTOM;


    public static int RIGHT_DRIVE_LEADER = 1;
    public static int RIGHT_DRIVE_FOLLOWER = 2;
    public static int LEFT_DRIVE_LEADER = 3;
    public static int LEFT_DRIVE_FOLLOWER = 4;
    //Intake
    public static int INTAKE_MOTOR = 11;
    public static int INTAKE_RIGHT_FORWARD_SOLENOID;
    public static int INTAKE_RIGHT_REVERSE_SOLENOID;
    public static int INTAKE_LEFT_FORWARD_SOLENOID;
    public static int INTAKE_LEFT_REVERSE_SOLENOID;

    //Conveyor
    public static int CONVEYOR_MOTOR_BACK = 9;
    public static int CONVEYOR_MOTOR_FRONT = 12;

    public static int INDEX_MOTOR = 10;

    //Sensors
    public static int DIO_SHOOTER_BEAM_BREAK;
    public static int DIO_PRE_INDEX_BEAM_BREAK;

    // Climber
    public static int CLIMBER_SPARK; //somewhere between 5 and 8
    public static int CLIMBER_RIGHT_FORWARD_SOLENOID;
    public static int CLIMBER_RIGHT_REVERSE_SOLENOID;
    public static int CLIMBER_LEFT_FORWARD_SOLENOID;
    public static int CLIMBER_LEFT_REVERSE_SOLENOID;

}