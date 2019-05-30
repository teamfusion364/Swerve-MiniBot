package frc.robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.SwerveSubsystem;

import static frc.robot.RobotMap.*;
public class DriveTrainCommand extends Command {

	private final SwerveSubsystem mDrivetrain;

	public DriveTrainCommand(SwerveSubsystem drivetrain) {
		mDrivetrain = drivetrain;
		requires(drivetrain);
	}

	private double deadband(double input) {
		if (Math.abs(input) < stickDeadband) return 0;
		return input;
	}

	@Override
	protected void execute() {
        double forward = -Robot.oi.forward();
		double strafe = Robot.oi.strafe();
		double rotation = Robot.oi.rotation();

		forward *= Math.abs(forward);
		strafe *= Math.abs(strafe);
		rotation *= Math.abs(rotation);

		forward = deadband(forward);
		strafe = deadband(strafe);
		rotation = deadband(rotation);

		SmartDashboard.putNumber("Forward", forward);
		SmartDashboard.putNumber("Strafe", strafe);
		SmartDashboard.putNumber("Rotation", rotation);

		mDrivetrain.holonomicDrive(forward, strafe, rotation);
	}

	@Override
	protected void end() {
		mDrivetrain.stopDriveMotors();
	}

	@Override
	protected void interrupted() {
		end();
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}
