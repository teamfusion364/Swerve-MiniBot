package frc.robot;

import static frc.robot.RobotMap.*;

public class Conversions{

    public static double toCounts(double units){
        return units * encoderTicks / 360.0;
    }

    public static double toDegrees(double units){
        return units * (360 / encoderTicks);
    }

    public double modulate360(double units){
        return units %= 360;
    }
    
}