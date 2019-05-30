package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.Drivetrain;

public class Robot extends TimedRobot {

  public static OI oi;
  private static Drivetrain drivetrain;
  public static Gyro gyro;
  public static double offset;

	public void robotInit() {
    oi = new OI();
    gyro = new Gyro();
    drivetrain = new Drivetrain();
    offset = 180;
	}

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    postSmartDashVars(); 
    oi.controlloop();
  }

  public void postSmartDashVars(){
    SmartDashboard.putNumber("Gyro Angle modified", gyro.getAngle());


    SmartDashboard.putNumber("Hold Offset", offset);
    for(int i = 0; i < drivetrain.getSwerveModules().length; i++){
      SmartDashboard.putNumber("Swerve Module " + i + " Pos", drivetrain.getSwerveModule(i).getCurrentAngle());
      SmartDashboard.putBoolean("Swerve Module " + i +" Drive Motor Inverted", drivetrain.getSwerveModule(i).getDriveMotor().getInverted());
      SmartDashboard.putNumber("Swerve Module " + i + " Closed loop error Deg", drivetrain.getSwerveModule(i).getAdjustedError());
      SmartDashboard.putNumber("Swerve Module " + i + " Target Angle: ",  drivetrain.getSwerveModule(i).getTargetAngle());
    }

  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

}
