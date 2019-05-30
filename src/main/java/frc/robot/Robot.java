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
  public static double offset = 180;

	public void robotInit() {

    gyro = new Gyro();
    drivetrain = new Drivetrain();
    oi = new OI();

	}

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    postSmartDashVars(); 
  }

  public void postSmartDashVars(){
    // SmartDashboard.putNumber("Gyro Angle modified", gyro.getAngle());
    // SmartDashboard.putNumber("Raw Pos", drivetrain.getSwerveModule(0).getAngleMotor().getSelectedSensorPosition(0));
    // SmartDashboard.putNumber("Pos", drivetrain.getSwerveModule(0).getCurrentAngle());
    // SmartDashboard.putNumber("Closed loop error Deg", drivetrain.getSwerveModule(0).getAdjustedError());
    // SmartDashboard.putNumber("Hold Offset", offset);
  }


}
