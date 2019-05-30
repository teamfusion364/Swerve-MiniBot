package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Hold;

import static frc.robot.RobotMap.*;

public class OI{

    public Joystick cont;

    public Command hold;
    public double angle = 0;
    public double previousButton = 0;
    public double currentButton;
    public double temp;

    public OI(){

        cont = new Joystick(0);
        hold = new Hold(angle);
        
    }

    public void controlloop(){

        for(int i = 1; i < 7; i++){
            if(cont.getRawButton(i)){
                currentButton = i;
                if(currentButton != previousButton){
                    angle = angles[i];
                }
                temp = i;
            }
        }

        if(currentButton != previousButton){
            hold = new Hold(angle);
            hold.start();
        }
        previousButton = temp;
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