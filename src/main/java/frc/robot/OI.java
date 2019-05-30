package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

public class OI{

    public Joystick cont;

    public OI(){

        cont = new Joystick(0);
        
    }

    /**
     * @return Left Stick X Axis
     */
    public double strafe(){
        return cont.getRawAxis(0);
    }

    /**
     * @return Left Stick Y Axis
     */
    public double forward(){
        return cont.getRawAxis(1);
    }
    
    /**
     * @return Right Stick X Axis
     */
    public double rotation(){
        return cont.getRawAxis(4);
    }

}