package frc.robot;

import edu.wpi.first.wpilibj.command.Command;
import static frc.robot.RobotMap.*;
import static frc.robot.Robot.*;

public class Hold extends Command{

    private double angle;

    public Hold(double angle){
        this.angle = angle;
        setTimeout(0.02);
    }
    
    @Override
    protected void initialize() {
    
    }

    @Override
    protected void execute() {
        angle += offsetToStraight;
        offset = angle;
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }
    @Override
    protected void end() {

    }
}