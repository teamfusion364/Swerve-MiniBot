package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.commands.DriveTrainCommand;

import com.ctre.phoenix.sensors.PigeonIMU;

import static frc.robot.RobotMap.*;

public class SwerveSubsystem extends Subsystem {

	/*
	 * 0 is Front Right
	 * 1 is Front Left
	 * 2 is Back Left
	 * 3 is Back Right
	 */
    private SwerveMod[] mSwerveModules;
    
    public SwerveSubsystem() {
        // super(WIDTH, LENGTH);
            mSwerveModules = new SwerveMod[] {
                    new SwerveMod(0,
                            new TalonSRX(rTopAngle),
                            new TalonSRX(rTopDrive),
                            0),//TODO: Find offset for swerve module: 224.473
                    // new SwerveDriveModule(1,
                    //         new TalonSRX(DRIVETRAIN_FRONT_RIGHT_ANGLE_MOTOR),
                    //         new TalonSRX(DRIVETRAIN_FRONT_RIGHT_DRIVE_MOTOR),
                    //         235.195),
                    // new SwerveDriveModule(2,
                    //         new TalonSRX(DRIVETRAIN_BACK_RIGHT_ANGLE_MOTOR),
                    //         new TalonSRX(DRIVETRAIN_BACK_RIGHT_DRIVE_MOTOR),
                    //         320.976),
                    // new SwerveDriveModule(3,
                    //         new TalonSRX(DRIVETRAIn_BACK_LEFT_ANGLE_MOTOR),
                    //         new TalonSRX(DRIVETRAIN_BACK_LEFT_DRIVE_MOTOR),
                    //         245.742),
            };

            mSwerveModules[0].setDriveInverted(true);
            // mSwerveModules[3].setDriveInverted(true);
    

        for (SwerveMod module : mSwerveModules) {
            module.setTargetAngle(0);
        }
    }

    public double[] calculateSwerveModuleAngles(double forward, double strafe, double rotation) {
            double angleRad = Math.toRadians(Robot.getGyroAngle());
            double temp = forward * Math.cos(angleRad) + strafe * Math.sin(angleRad);
            strafe = -forward * Math.sin(angleRad) + strafe * Math.cos(angleRad);
            forward = temp;

        double a = strafe - rotation;//w/t
        double b = strafe + rotation;//w/t
        double c = forward - rotation;//t/w
        double d = forward + rotation;//t/w

        return new double[]{
                Math.atan2(b, c) * 180 / Math.PI
                // ,
                // Math.atan2(b, d) * 180 / Math.PI,
                // Math.atan2(a, d) * 180 / Math.PI,
                // Math.atan2(a, c) * 180 / Math.PI
        };
    }

    public SwerveMod getSwerveModule(int i) {
        return mSwerveModules[i];
    }

    public void holonomicDrive(double forward, double strafe, double rotation) {

            double angleRad = Math.toRadians(Robot.getGyroAngle());
            double temp = forward * Math.cos(angleRad) + strafe * Math.sin(angleRad);
            strafe  = -forward * Math.sin(angleRad) + strafe * Math.cos(angleRad);
            forward = temp;

        double a = strafe - rotation;//w/t
        double b = strafe + rotation;//w/t
        double c = forward - rotation;//t/w
        double d = forward + rotation;//t/w

        double[] angles = new double[]{
                Math.atan2(b, c) * 180 / Math.PI
                // ,
                // Math.atan2(b, d) * 180 / Math.PI,
                // Math.atan2(a, d) * 180 / Math.PI,
                // Math.atan2(a, c) * 180 / Math.PI
        };

        double[] speeds = new double[]{
                Math.sqrt(b * b + c * c)
                // ,
                // Math.sqrt(b * b + d * d),
                // Math.sqrt(a * a + d * d),
                // Math.sqrt(a * a + c * c)
        };

        double max = speeds[0];

        for (double speed : speeds) {
            if (speed > max) {
                max = speed;
            }
        }

        for (int i = 0; i < 1; i++) {//was 4
            if (Math.abs(forward) > 0.05 ||
                    Math.abs(strafe) > 0.05 ||
                    Math.abs(rotation) > 0.05) {
                mSwerveModules[i].setTargetAngle(angles[i] + 180);
            } else {
                mSwerveModules[i].setTargetAngle(mSwerveModules[i].getTargetAngle());
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
        setDefaultCommand(new DriveTrainCommand(this));
    }
}
