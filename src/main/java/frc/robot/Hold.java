package frc.robot;

import edu.wpi.first.wpilibj.command.Command;
import static frc.robot.RobotMap.*;
import frc.robot.Robot;
public class Hold extends Command{

    private double angle;

    public Hold(double angle){
        this.angle = angle;
        setTimeout(0.02);
    }
    @Override
    protected void initialize() {
        angle += offsetToStraight;
    }

    @Override
    protected void execute() {
        Robot.offset = angle;
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }
}