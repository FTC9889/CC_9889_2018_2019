package com.team9889.lib;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.MotorType;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.lib.control.Math.Pose;
import com.team9889.lib.control.Math.Rotation2d;
import com.team9889.lib.drivetrain.DriveConfig;
import com.team9889.subsystems.Subsystem;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by joshua9889 on 7/3/2018.
 **/

public class Drivetrain extends Subsystem {
    public Drivetrain(Type type_, DriveConfig config_, MotorType motorType_){
        mType = type_;
        mConfig = config_;
        mMotorType = motorType_;
    }

    public enum Type{
        MECANUM, DIFFERENTIAL_2Motor, DIFFERENTIAL_4Motor
    }

    public enum ControlType{
        TELEOP, PATH_FOLLOW
    }

    private DriveConfig mConfig;
    private Type mType;
    private MotorType mMotorType;
    private ControlType mControlType = ControlType.TELEOP;

    // Current Position on the Cartesian Place
    public Pose currentPose = new Pose();

    private DcMotorEx lf, lb, rf, rb;
    private RevIMU imu;

    private ElapsedTime t = new ElapsedTime();

    // Used to find the velocity of the robot and for PID loops
    private double lastLeftPosition = 0.0;
    private double lastRightPosition = 0.0;

    /**
     * Current Velocity of the motors in ticks/millisecond
     */
    private double leftVelocity = 0.0;
    private double rightVelocity = 0.0;

    // Used to find the acceleration of the motors
    private double lastLeftVelocity = 0.0;
    private double lastRightVelocity = 0.0;

    /**
     * Current Acceleration of the motors in ticks/millisecond^2
     */
    private double leftAcceleration = 0.0;
    private double rightAcceleration = 0.0;

    @Override
    public void init(HardwareMap hardwareMap, boolean auton) {
        switch (mType){
                case DIFFERENTIAL_2Motor:
                    lf = hardwareMap.get(DcMotorEx.class, "l");
                    rf = hardwareMap.get(DcMotorEx.class, "r");

                    lf.setDirection(DcMotorSimple.Direction.REVERSE);
                    break;
                default:
                    lf = hardwareMap.get(DcMotorEx.class, "lf");
                    lb = hardwareMap.get(DcMotorEx.class, "lb");
                    rf = hardwareMap.get(DcMotorEx.class, "rf");
                    rb = hardwareMap.get(DcMotorEx.class, "rb");

                    lf.setDirection(DcMotorSimple.Direction.REVERSE);
                    lb.setDirection(DcMotorSimple.Direction.REVERSE);
                break;
        }

        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        imu = new RevIMU("imu", hardwareMap);
    }

    @Override
    public void zeroSensors() {
        resetEncoders();
    }

    @Override
    public void update() {
        double dt = t.milliseconds();

        switch (mType){
            case MECANUM:
                System.out.println("You built a bad robot");
                break;
            default:
                double currentLeftPosition = getLeftPosition();
                double currentRightPosition = getRightPosition();

                double leftDelta = currentLeftPosition - lastLeftPosition;
                double rightDelta = currentRightPosition - lastRightPosition;

                // Going to slow down the loop bc Rev IMU is slow to update
                double heading = getGyroAngle().getTheda(Rotation2d.Unit.RADIANS);

                if (Math.abs(leftDelta - rightDelta) < 5) { // basically going straight
//                    currentPose.setX(currentPose.getX() + leftDelta * Math.cos(heading));
//                    currentPose.setX(currentPose.getY() + rightDelta * Math.sin(heading));
//                    currentPose.setTheda(heading, Rotation2d.Unit.RADIANS);
                } else {
                    double R = mConfig.wheelbase_diameter * (leftDelta + rightDelta) / (2 * (rightDelta - leftDelta));
                    double wd = (rightDelta - leftDelta) / mConfig.wheelbase_diameter;

//                    currentPose.setX(currentPose.getY() + R*Math.sin(wd + heading) - R * Math.sin(heading));
//                    currentPose.setY(currentPose.getY() - R * Math.cos(wd + heading) + R * Math.cos(heading));
//                    currentPose.setTheda(heading + wd, Rotation2d.Unit.RADIANS);
                }

                leftVelocity = leftDelta / dt;
                rightVelocity = rightDelta / dt;

                leftAcceleration = (leftVelocity - lastLeftVelocity) / dt;
                rightAcceleration = (rightVelocity - lastRightVelocity) / dt;

                lastLeftVelocity = leftVelocity;
                lastRightVelocity = rightVelocity;

                lastLeftPosition = currentLeftPosition;
                lastRightPosition = currentRightPosition;
                break;
        }

        switch (mControlType){
            case PATH_FOLLOW:

                break;
            case TELEOP:

                break;
        }

        t.reset();
    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("Left Position", getLeftPosition());
        telemetry.addData("Right Position", getRightPosition());
        telemetry.addData("Left Position IN", getLeftPositionInches());
        telemetry.addData("Right Position IN", getRightPositionInches());
//        telemetry.addData("Current Pose X", currentPose.getX());
//        telemetry.addData("Current Pose Y", currentPose.getY());
//        telemetry.addData("Current Pose Theda",
//                currentPose.getTheda(Rotation2d.Unit.DEGREES));
    }

    @Override
    public void test(Telemetry telemetry) {

    }

    @Override
    public void stop() {
        setLeftRightPower(0,0);
    }

    public Rotation2d getGyroAngle(){
        return new Rotation2d(imu.getNormalHeading(), Rotation2d.Unit.DEGREES);
    }

    public double getLeftPosition(){
        switch (mType){
            case DIFFERENTIAL_2Motor:
                return lf.getCurrentPosition();
            case DIFFERENTIAL_4Motor:
                // Used to make the reading more reliable,
                // If one encoder fails, use the other one
                double lfPos = lf.getCurrentPosition();
                if(lfPos == 0)
                    return lb.getCurrentPosition();
                else {
                    return lfPos;
                }
            case MECANUM:
                return (lf.getCurrentPosition() + lb.getCurrentPosition())/2.0;
        }
        return 0;
    }

    public double getRightPosition(){
        switch (mType){
            case DIFFERENTIAL_2Motor:
                return rf.getCurrentPosition();
            case DIFFERENTIAL_4Motor:
                // Used to make the reading more reliable,
                // If one encoder fails, use the other one
                double rfPos = rf.getCurrentPosition();
                if(rfPos == 0)
                    return rb.getCurrentPosition();
                else {
                    return rfPos;
                }
            case MECANUM:
                return (rf.getCurrentPosition() + rb.getCurrentPosition())/2.0;
        }
        return 0;
    }

    public double getLeftPositionInches(){
        return getLeftPosition() / ((mMotorType.ticksPerRev() * mConfig.gear_reduction) / (mConfig.wheel_diameter*Math.PI));
    }

    public double getRightPositionInches(){
        return getLeftPosition() / ((mMotorType.ticksPerRev() * mConfig.gear_reduction) / (mConfig.wheel_diameter*Math.PI));
    }

    public void resetEncoders(){
        while(getLeftPosition()!=0 && getRightPosition() !=0) {
            setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }

        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setRunMode(DcMotor.RunMode runMode){
        switch (mType){
            case DIFFERENTIAL_2Motor:
                lf.setMode(runMode);
                rf.setMode(runMode);
                break;
            default:
                lf.setMode(runMode);
                lb.setMode(runMode);
                rf.setMode(runMode);
                rb.setMode(runMode);
                break;
        }
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior){
        switch (mType){
            case DIFFERENTIAL_2Motor:
                lf.setZeroPowerBehavior(behavior);
                rf.setZeroPowerBehavior(behavior);
                break;
            default:
                lf.setZeroPowerBehavior(behavior);
                lb.setZeroPowerBehavior(behavior);
                rf.setZeroPowerBehavior(behavior);
                rb.setZeroPowerBehavior(behavior);
                break;
        }
    }

    public void setLeftRightPower(double left, double right){
        switch (mType){
            case DIFFERENTIAL_2Motor:
                lf.setPower(left);
                rf.setPower(right);
                break;
            default:
                lf.setPower(left);
                lb.setPower(left);
                rf.setPower(right);
                rb.setPower(right);
                break;
        }
    }
}
