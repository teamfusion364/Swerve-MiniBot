package frc.robot;

public class RobotMap{

    //hardware
    public static final int TRACKWIDTH = 19;
    public static final int WHEELBASE = 19;
    public static final int WHEELDIAMETER = 2;
    
    public static final int FLANGLE = 2;//FL 2-> BR 8
    public static final int FLDRIVE = 1;//FL 1-> BR 7
    public static final int FRANGLE = 6;//FR 6-> BL 12
    public static final int FRDRIVE = 5;//FR 5-> BL 3
    public static final int BRANGLE = 8;//BR 8-> FL 2
    public static final int BRDRIVE = 7;//BR 7-> FL 1
    public static final int BLANGLE = 12;//was 4 //BL 12-> FR
    public static final int BLDRIVE = 3;//BL 3-> FR

    public static final int PIGEON = 3;

    //Offsets 
    /**Front Right */
    public static final double MOD0OFFSET = 101.1;
    /**Front Left */
    public static final double MOD1OFFSET = 322.1;
    /**Back Left */
    public static final double MOD2OFFSET = 188.6;
    /**Back Right */
    public static final double MOD3OFFSET = 132.45;

    //Holds
    public static final int ABUTTONANGLE = 92;
    public static final int BBUTTONANGLE = -120;
    public static final int XBUTTONANGLE = 45;
    public static final int YBUTTONANGLE = 60;
    public static final int LEFTBUMPERANGLE = -180;
    public static final int RIGHTBUMPERANGLE = 0;
    
    public static double[] angles = {
        0,
        ABUTTONANGLE,
        BBUTTONANGLE,
        XBUTTONANGLE,
        YBUTTONANGLE,
        LEFTBUMPERANGLE,
        RIGHTBUMPERANGLE};

    //constants
    public static final double STICKDEADBAND = 0.05;
    public static final double ENCODERTICKS = 4096.0;
    public static final double OFFSETTOSTRAIGHT = 180;


    public static final int SLOTIDX = 0;
    public static final int SWERVETIMEOUT = 0;

    public static final double ANGLEP = 1.5;//20
    public static final double ANGLEI = 0.01;//0.001
    public static final double ANGLED = 0;//130 //200

    public static final int ANGLEVELOCITY = 10000;
    public static final int ANGLEACCELERATION = 10000;

    public static final double ANGLENOMINALFORWARD = 0;
    public static final double ANGLENOMINALREVERSE = 0;
    public static final double ANGLEPEAKFORWARD = 1;
    public static final double ANGLEPEAKREVERSE = -1;

    public static final int ANGLECONTINUOUSCURRENTLIMIT = 30;
    public static final int ANGLEPEAKCURRENT = 30;
    public static final int ANGLEPEAKCURRENTDURATION = 100;
    public static final boolean ANGLEENABLECURRENTLIMIT = true;

    public static final int DRIVECONTINUOUSCURRENTLIMIT = 25;
    public static final int DRIVEPEAKCURRENT = 25;
    public static final int DRIVEPEAKCURRENTDURATION = 100;
    public static final boolean DRIVEENABLECURRENTLIMIT = true;


}