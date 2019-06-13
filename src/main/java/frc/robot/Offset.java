package frc.robot;

import edu.wpi.first.wpilibj.command.Command;
import static frc.robot.RobotMap.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import static frc.robot.Conversions.*;

public class Offset extends Command{

    private SwerveMod[] mod;
    private double[] offset;
    private double[] target;

    public Offset(SwerveMod[] mod){
        setTimeout(1);
        this.mod = mod;
        offset = new double[mod.length];
        target = new double[mod.length];
        for(int i = 0; i < mod.length; i++){
            offset[i] = mod[i].getOffset();
        }
    }
    
    @Override
    protected void initialize() {
        for(int i = 0; i < mod.length; i++){
            double absolute = mod[i].getTicks();
            target[i] = toCounts(offset[i]) - absolute;
        }    
    }

    @Override
    protected void execute() {
        for(int i = 0; i < mod.length; i++){
            mod[i].getAngleMotor().set(ControlMode.MotionMagic, target[i]);
        }
    }

    @Override
    protected boolean isFinished() {
        int lastMod = mod.length - 1;
        if((mod[lastMod].getAngleMotor().getClosedLoopError(SLOTIDX) < 10) && isTimedOut()){
            return true;
        }else{
            return false;
        }
    }
    @Override
    protected void end() {
        for(int i = 0; i < mod.length; i++){
            mod[i].zero();
            mod[i].getAngleMotor().set(ControlMode.PercentOutput, 0);//Redudant?
        }
    }
    @Override
    protected void interrupted() {
        end();
    }
    
}