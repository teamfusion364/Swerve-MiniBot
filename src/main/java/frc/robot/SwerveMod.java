package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.robot.Conversions;
import static frc.robot.RobotMap.*;

public class SwerveMod extends Conversions{
    private double lastTargetAngle = 0;
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

        angleMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, slotIDX, swerveModuleTimeout);
        angleMotor.setSelectedSensorPosition(0);
        angleMotor.setSensorPhase(false);
        angleMotor.config_kP(slotIDX, angleP);
        angleMotor.config_kI(slotIDX, angleI);
        angleMotor.config_kD(slotIDX, angleD);
        angleMotor.setNeutralMode(NeutralMode.Brake);
        angleMotor.set(ControlMode.Position, 0);
        angleMotor.configMotionCruiseVelocity(angleVelocity, swerveModuleTimeout);
        angleMotor.configMotionAcceleration(angleAcceleration, swerveModuleTimeout);

        angleMotor.configNominalOutputForward(angleNominalForward, swerveModuleTimeout);
        angleMotor.configNominalOutputReverse(angleNominalReverse, swerveModuleTimeout);
        angleMotor.configPeakOutputForward(anglePeakForward, swerveModuleTimeout);
        angleMotor.configPeakOutputReverse(anglePeakReverse, swerveModuleTimeout);

        driveMotor.setNeutralMode(NeutralMode.Brake);

        // Set amperage limits
        angleMotor.configContinuousCurrentLimit(angleContinuousCurrentLimit, swerveModuleTimeout);
        angleMotor.configPeakCurrentLimit(anglePeakCurrent, swerveModuleTimeout);
        angleMotor.configPeakCurrentDuration(anglePeakCurrentDuration, swerveModuleTimeout);
        angleMotor.enableCurrentLimit(angleEnableCurrentLimit);

        driveMotor.configContinuousCurrentLimit(driveContinuousCurrentLimit, swerveModuleTimeout);
        driveMotor.configPeakCurrentLimit(drivePeakCurrent, swerveModuleTimeout);
        driveMotor.configPeakCurrentDuration(drivePeakCurrentDuration, swerveModuleTimeout);
        driveMotor.enableCurrentLimit(driveEnableCurrentLimit);
    }

    public TalonSRX getAngleMotor(){
        return mAngleMotor;
    }

    public double getCurrentAngle(){
        double angle = toDegrees(getPos());
        angle -= mZeroOffset;
        angle = modulate360(angle);
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
        targetAngle = modulate360(targetAngle);
        // SmartDashboard.putNumber("Module Target Angle " + moduleNumber, modulate360(targetAngle));
        targetAngle += mZeroOffset;
        double currentAngle = toDegrees(getPos());
        double currentAngleMod = modulate360(currentAngle);

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

        double currentError = getRawError();
        targetAngle = toCounts(targetAngle);
        mAngleMotor.set(ControlMode.Position, targetAngle);
    }

    public void setTargetSpeed(double speed) {
        if (driveInverted) speed = -speed;
        mDriveMotor.set(ControlMode.PercentOutput, speed);
    }

    public double getRawError(){
        return mAngleMotor.getClosedLoopError(0);
    }
    public double getAdjustedError(){
        return toDegrees(getRawError());
    }

    public double getPos(){
        return mAngleMotor.getSelectedSensorPosition(0);
    }
}
