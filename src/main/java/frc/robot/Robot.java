package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.subsystems.SwerveSubsystem;
public class Robot extends TimedRobot {

  public static TalonSRX GyroTalon = new TalonSRX(1);
  public static PigeonIMU gyro = new PigeonIMU(GyroTalon);
  public static OI oi;
  private static SwerveSubsystem swerveSubsystem;

	public void robotInit() {

    swerveSubsystem = new SwerveSubsystem();
    gyro.setFusedHeading(0);
    oi = new OI();

	}

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    postSmartDashVars(); 
  }

  public void postSmartDashVars(){
    SmartDashboard.putNumber("Gyro Angle modified", getGyroAngle());
    SmartDashboard.putNumber("Raw Pos", swerveSubsystem.getSwerveModule(0).getAngleMotor().getSelectedSensorPosition(0));
    SmartDashboard.putNumber("Pos", swerveSubsystem.getSwerveModule(0).getCurrentAngle());
    SmartDashboard.putNumber("Closed loop error Deg", swerveSubsystem.getSwerveModule(0).getError() *360.0 / 4096.0);
  }

  public static double getGyroAngle() {
    double angle = Robot.gyro.getFusedHeading();
    angle %= 360;
    if (angle < 0) angle += 360;
        return angle;
}

}
