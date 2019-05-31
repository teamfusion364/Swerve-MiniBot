package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.DrivetrainCommand;

import static frc.robot.RobotMap.*;
import static frc.robot.Robot.*;

public class Drivetrain extends Subsystem {

	/*
	 * 0 is Front Right
	 * 1 is Front Left
	 * 2 is Back Left
	 * 3 is Back Right
	 */
    private SwerveMod[] mSwerveModules;
    private int w = wheelbase;
    private int t = trackwidth;
    private Command zero;

    public Drivetrain() {
            mSwerveModules = new SwerveMod[] {
                    new SwerveMod(0,
                            new TalonSRX(rTopAngle),
                            new TalonSRX(rTopDrive),
                            224),//224.1
            };

            mSwerveModules[0].setDriveInverted(true);
            // mSwerveModules[3].setDriveInverted(true);
        
            zero = new Offset(mSwerveModules);
            zero.start();
    }  
    

    public double[] calculateSwerveModuleAngles(double forward, double strafe, double rotation) {
            double angleRad = Math.toRadians(Robot.gyro.getAngle());
            double temp = forward * Math.cos(angleRad) + strafe * Math.sin(angleRad);
            strafe = -forward * Math.sin(angleRad) + strafe * Math.cos(angleRad);
            forward = temp;
    
        double a = strafe - rotation * w / t;
        double b = strafe + rotation * w / t;
        double c = forward - rotation * t / w;
        double d = forward + rotation * t / w;

        return new double[]{
                Math.atan2(b, c) * 180 / Math.PI,
                Math.atan2(b, d) * 180 / Math.PI,
                Math.atan2(a, d) * 180 / Math.PI,
                Math.atan2(a, c) * 180 / Math.PI
        };
    }

    public SwerveMod getSwerveModule(int i) {
        return mSwerveModules[i];
    }

    public void holonomicDrive(double forward, double strafe, double rotation) {

            double angleRad = Math.toRadians(Robot.gyro.getAngle());
            double temp = forward * Math.cos(angleRad) + strafe * Math.sin(angleRad);
            strafe  = -forward * Math.sin(angleRad) + strafe * Math.cos(angleRad);
            forward = temp;

        double a = strafe - rotation * w / t;
        double b = strafe + rotation * w / t;
        double c = forward - rotation * t / w;
        double d = forward + rotation * t / w;

        double[] angles = new double[]{
                Math.atan2(b, c) * 180 / Math.PI,
                Math.atan2(b, d) * 180 / Math.PI,
                Math.atan2(a, d) * 180 / Math.PI,
                Math.atan2(a, c) * 180 / Math.PI
        };

        double[] speeds = new double[]{
                Math.sqrt(b * b + c * c),
                Math.sqrt(b * b + d * d),
                Math.sqrt(a * a + d * d),
                Math.sqrt(a * a + c * c)
        };

        double max = speeds[0];

        for (double speed : speeds) {
            if (speed > max) {
                max = speed;
            }
        }

        for (int i = 0; i < mSwerveModules.length; i++) {
            if (Math.abs(forward) > stickDeadband ||
                    Math.abs(strafe) > stickDeadband ||
                    Math.abs(rotation) > stickDeadband ||
                    Robot.oi.hold.isRunning()) {
                mSwerveModules[i].setTargetAngle(angles[i] + offset);
            } 
            mSwerveModules[i].setTargetSpeed(speeds[i]);
        }
    }

    public void stopDriveMotors() {
        for (SwerveMod module : mSwerveModules) {
            module.setTargetSpeed(0);
        }
    }

    public SwerveMod[] getSwerveModules() {
        return mSwerveModules;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DrivetrainCommand(this));
    }
}
