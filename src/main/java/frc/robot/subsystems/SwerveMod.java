package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwerveMod {
    private double mLastError = 0, lastTargetAngle = 0;
    private final int moduleNumber;

    private final double mZeroOffset;

    private final TalonSRX mAngleMotor;
    private final TalonSRX mDriveMotor;

    private boolean driveInverted = false;

    public SwerveMod(int moduleNumber, TalonSRX angleMotor, TalonSRX driveMotor, double zeroOffset) {
        this.moduleNumber = moduleNumber;

        mAngleMotor = angleMotor;
        mDriveMotor = driveMotor;
        mZeroOffset = zeroOffset;

        angleMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
        angleMotor.setSelectedSensorPosition(0);
        angleMotor.setSensorPhase(false);
        angleMotor.config_kP(0, 2, 0);
        angleMotor.config_kI(0, 0.01, 0);
        angleMotor.config_kD(0, 0.0, 0);
        angleMotor.setNeutralMode(NeutralMode.Brake);
        angleMotor.set(ControlMode.Position, 0);
        angleMotor.configMotionCruiseVelocity(1000, 0);
        angleMotor.configMotionAcceleration(2400, 0);

        driveMotor.setNeutralMode(NeutralMode.Brake);

        // Set amperage limits
        angleMotor.configContinuousCurrentLimit(30, 0);
        angleMotor.configPeakCurrentLimit(30, 0);
        angleMotor.configPeakCurrentDuration(100, 0);
        angleMotor.enableCurrentLimit(true);

        driveMotor.configContinuousCurrentLimit(25, 0);
        driveMotor.configPeakCurrentLimit(25, 0);
        driveMotor.configPeakCurrentDuration(100, 0);
        driveMotor.enableCurrentLimit(true);
    }

    public TalonSRX getAngleMotor(){
        return mAngleMotor;
    }

    public double getCurrentAngle(){
        double angle = mAngleMotor.getSelectedSensorPosition(0) * (360.0 / 4096.0);
        angle -= mZeroOffset;
        angle %= 360;
        if (angle < 0) angle += 360;
        return angle;
    }


    public TalonSRX getDriveMotor() {
        return mDriveMotor;
    }

    public double getTargetAngle() {
        return lastTargetAngle;
    }

    public void setDriveInverted(boolean inverted) {
        driveInverted = inverted;
    }

    public void setTargetAngle(double targetAngle) {
    	
        lastTargetAngle = targetAngle;
        targetAngle %= 360;
        SmartDashboard.putNumber("Module Target Angle " + moduleNumber, targetAngle % 360);
        targetAngle += mZeroOffset;
        double currentAngle = mAngleMotor.getSelectedSensorPosition(0) * (360.0 / 4096.0);
        double currentAngleMod = currentAngle % 360;

        if (currentAngleMod < 0) currentAngleMod += 360;
        double delta = currentAngleMod - targetAngle;
        if (delta > 180) {
            targetAngle += 360;
        } else if (delta < -180) {
            targetAngle -= 360;
        }

        delta = currentAngleMod - targetAngle;
        if (delta > 90 || delta < -90) {
            if (delta > 90)
                targetAngle += 180;
            else if (delta < -90)
                targetAngle -= 180;
            mDriveMotor.setInverted(false);
        } else {
            mDriveMotor.setInverted(true);
        }

        targetAngle += currentAngle - currentAngleMod;

        double currentError = mAngleMotor.getClosedLoopError(0);
        mLastError = currentError;
        targetAngle *= 4096.0 / 360.0;
        mAngleMotor.set(ControlMode.Position, targetAngle);
    }

    public void setTargetSpeed(double speed) {
        if (driveInverted) speed = -speed;
        mDriveMotor.set(ControlMode.PercentOutput, speed);
    }

    public double getError(){
        return mAngleMotor.getClosedLoopError(0);
    }
}
