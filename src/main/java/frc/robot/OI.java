package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Hold;

public class OI{

    public Joystick cont;
    public JoystickButton lock45right;
    public JoystickButton lock45left;
    public JoystickButton lock127right;
    public JoystickButton lock37left;
    public JoystickButton lock0;
    public JoystickButton lock180;

    public OI(){

        cont = new Joystick(0);

        lock45right = new JoystickButton(cont, 1);
        lock45right.whenPressed(new Hold(45));
        
        lock45left = new JoystickButton(cont, 2);
        lock45left.whenPressed(new Hold(-45));

        lock127right = new JoystickButton(cont, 3);
        lock127right.whenPressed(new Hold(127));

        lock37left = new JoystickButton(cont, 4);
        lock37left.whenPressed(new Hold(-37));

        lock0 = new JoystickButton(cont, 5);
        lock0.whenPressed(new Hold(0));

        lock180 = new JoystickButton(cont, 6);
        lock180.whenPressed(new Hold(-180));
        
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