package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.Hold;

import static frc.robot.RobotMap.*;

public class OI{

    public Joystick cont;
    public JoystickButton aButton;
    public JoystickButton bButton;
    public JoystickButton xButton;
    public JoystickButton yButton;
    public JoystickButton leftBumper;
    public JoystickButton rightBumper;

    public OI(){

        cont = new Joystick(0);

        aButton = new JoystickButton(cont, 1);
        aButton.whenPressed(new Hold(aButtonAngle));
        
        bButton = new JoystickButton(cont, 2);
        bButton.whenPressed(new Hold(bButtonAngle));

        xButton = new JoystickButton(cont, 3);
        xButton.whenPressed(new Hold(xButtonAngle));

        yButton = new JoystickButton(cont, 4);
        yButton.whenPressed(new Hold(yButtonAngle));

        leftBumper = new JoystickButton(cont, 5);
        leftBumper.whenPressed(new Hold(leftBumperAngle));

        rightBumper = new JoystickButton(cont, 6);
        rightBumper.whenPressed(new Hold(rightBumperAngle));
        
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