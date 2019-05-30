package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.SwerveSubsystem;
public class Robot extends TimedRobot {

  public static OI oi;
  private static SwerveSubsystem swerveSubsystem;
  public static Gyro gyro;

	public void robotInit() {

    gyro = new Gyro();
    swerveSubsystem = new SwerveSubsystem();
    oi = new OI();

	}

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    postSmartDashVars(); 
  }

  public void postSmartDashVars(){
    SmartDashboard.putNumber("Gyro Angle modified", gyro.getAngle());
    SmartDashboard.putNumber("Raw Pos", swerveSubsystem.getSwerveModule(0).getAngleMotor().getSelectedSensorPosition(0));
    SmartDashboard.putNumber("Pos", swerveSubsystem.getSwerveModule(0).getCurrentAngle());
    SmartDashboard.putNumber("Closed loop error Deg", swerveSubsystem.getSwerveModule(0).getAdjustedError());
  }


}
