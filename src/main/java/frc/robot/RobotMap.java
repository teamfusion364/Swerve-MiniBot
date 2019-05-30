package frc.robot;

public class RobotMap{

    //hardware
    public static final int trackwidth = 19;
    public static final int wheelbase = 19;
    
    public static final int rTopAngle = 0;
    public static final int rTopDrive = 1;
    public static final int gyroTalon = 1;//In actual robot this will not be the same as rTopDrive

    //constants
    public static final double stickDeadband = 0.05;
    public static final double encoderTicks = 4096.0;
    public static final double offsetToStraight = 180;


    public static final int slotIDX = 0;
    public static final int swerveModuleTimeout = 0;

    public static final double angleP = 20.0;
    public static final double angleI = 0.001;
    public static final double angleD = 130;

    public static final int angleVelocity = 1000;
    public static final int angleAcceleration = 2400;

    public static final double angleNominalForward = 0;
    public static final double angleNominalReverse = 0;
    public static final double anglePeakForward = 1;
    public static final double anglePeakReverse = -1;

    public static final int angleContinuousCurrentLimit = 30;
    public static final int anglePeakCurrent = 30;
    public static final int anglePeakCurrentDuration = 100;
    public static final boolean angleEnableCurrentLimit = true;

    public static final int driveContinuousCurrentLimit = 25;
    public static final int drivePeakCurrent = 25;
    public static final int drivePeakCurrentDuration = 100;
    public static final boolean driveEnableCurrentLimit = true;


}