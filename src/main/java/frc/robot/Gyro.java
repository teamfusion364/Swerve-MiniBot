package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import static frc.robot.RobotMap.*;

public class Gyro{

    public static TalonSRX GyroTalon = new TalonSRX(gyroTalon);
    public static PigeonIMU gyro = new PigeonIMU(GyroTalon);

    public Gyro(){
        zero();
    }

    /**
     * @return Adjusted Angle [0, 360)
     */
     public double getAngle() {
    double angle = getFusedHeading();
    angle %= 360;
    if (angle < 0) angle += 360;
        return angle;
    
    }

    public static double getFusedHeading(){
        return gyro.getFusedHeading();
    }
    
    public void zero(){
        gyro.setFusedHeading(0);
    }
}
