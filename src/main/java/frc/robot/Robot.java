package frc.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

public class Robot extends TimedRobot {
  TalonSRX Talon = new TalonSRX(0);
  TalonSRX GyroTalon = new TalonSRX(1);
  final int kTimeoutMs = 30;
  public Joystick cont = new Joystick(0);
  PigeonIMU gyro = new PigeonIMU(GyroTalon);

  public int swerveMod = 3;

	public void robotInit() {
		Talon.configFactoryDefault();
		initQuadrature();
    Talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,	0, kTimeoutMs);	
    Talon.selectProfileSlot(0, 0);		
    Talon.config_kF(0, 0, kTimeoutMs);
    Talon.config_kP(0, 1.30, kTimeoutMs);
    Talon.config_kI(0, 0, kTimeoutMs);
    Talon.config_kD(0, 0, kTimeoutMs);		
    Talon.configNominalOutputForward(0, kTimeoutMs);
    Talon.configNominalOutputReverse(0, kTimeoutMs);
    Talon.configPeakOutputForward(1, kTimeoutMs);
    Talon.configPeakOutputReverse(-1, kTimeoutMs);
    Talon.configMotionCruiseVelocity(1000,kTimeoutMs);
    Talon.configMotionAcceleration(2400,kTimeoutMs);	
    gyro.setFusedHeading(0);
	}

  @Override 
  public void robotPeriodic() {
    Talon.getSensorCollection().setPulseWidthPosition(Talon.getSensorCollection().getPulseWidthPosition() & 0xFFF, kTimeoutMs);
  }

	public void initQuadrature() {
		int pulseWidth = Talon.getSensorCollection().getPulseWidthPosition();
		pulseWidth = pulseWidth & 0xFFF;
		Talon.getSensorCollection().setQuadraturePosition(pulseWidth, kTimeoutMs);
  }
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
      double forward = cont.getRawAxis(1);
      double strafe = cont.getRawAxis(0);
      double rotation = cont.getRawAxis(4);
  
      forward *= Math.abs(forward);
      strafe *= Math.abs(strafe);
      rotation *= Math.abs(rotation);
  
      forward = deadband(forward);
      strafe = deadband(strafe);
      double adjustedAngle;
      if(calculateSwerveModuleAngle(forward, strafe, rotation)[swerveMod] >=0){
        adjustedAngle = calculateSwerveModuleAngle(forward, strafe, rotation)[swerveMod];
      }else{
        adjustedAngle = calculateSwerveModuleAngle(forward, strafe, rotation)[swerveMod] + 360;
      }
      double speed = speed(forward, strafe, rotation)[swerveMod];
      moveTo(adjustedAngle);
      drive(speed);
      System.out.println("Angle for module " + adjustedAngle);
      System.out.println("Actual angle" + ToDeg(Talon.getClosedLoopTarget()));

 
  }

	double ToDeg(double units) {
		return units * 360 / 4096;
  }
  double ToPulse(double units){
    return units * 4096 / 360;
  }

  public void moveTo(double degrees){
    Talon.set(ControlMode.MotionMagic, ToPulse(degrees));
  }
  public void drive(double speed){
    GyroTalon.set(ControlMode.PercentOutput, speed);
  }

  private double deadband(double input) {
    if (Math.abs(input) < 0.05) return 0;
    return input;
  }

  public double[] calculateSwerveModuleAngle(double forward, double strafe, double rotation) {
    double angleRad = Math.toRadians(getYaw());//use gyro reading
    double temp = forward * Math.cos(angleRad) + strafe * Math.sin(angleRad);
    strafe = -forward * Math.sin(angleRad) + strafe * Math.cos(angleRad);
    forward = temp;

    double a = strafe - rotation;
    double b = strafe + rotation;
    double c = forward - rotation;
    double d = forward + rotation;

    return new double[]{
      Math.atan2(b, c) * 180 / Math.PI,
      Math.atan2(b, d) * 180 / Math.PI,
      Math.atan2(a, d) * 180 / Math.PI,
      Math.atan2(a, c) * 180 / Math.PI
};
}
  public double[] speed(double forward, double strafe, double rotation){
    double a = strafe - rotation;
    double b = strafe + rotation;
    double c = forward - rotation;
    double d = forward + rotation;

    return new double[]{
      Math.sqrt(b * b + c * c),
      Math.sqrt(b * b + d * d),
      Math.sqrt(a * a + d * d),
      Math.sqrt(a * a + c * c)
};

  }
  public double getYaw(){
      if(gyro.getFusedHeading() > 360){
        gyro.setFusedHeading(0);
        return 0;
      }else if(gyro.getFusedHeading() >= 0){
        return gyro.getFusedHeading();
      }else if(gyro.getFusedHeading() < -360 && gyro.getFusedHeading() < 0){
        gyro.setFusedHeading(0);
        return 0;
      }else{
        return (gyro.getFusedHeading() + 360);
      }
      
  }

}
