package com.team9889.ftc2019.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.configuration.LynxConstants;
import com.qualcomm.robotcore.util.RobotLog;
import com.team9889.ftc2019.Constants;
import com.team9889.lib.CruiseLib;
import com.team9889.lib.control.math.cartesian.Pose;
import com.team9889.lib.control.math.cartesian.Rotation2d;
import com.team9889.lib.control.math.cartesian.Vector2d;
import com.team9889.lib.hardware.RevIMU;
import com.team9889.lib.loops.Loop;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by joshua9889 on 10/6/2017.
 */

public class Drive extends Subsystem implements Loop{

    /**
     * ticks to inch
     * (Wheel Diameter * PI) * Counts Per Rotation
     */
    public final double ENCODER_TO_DISTANCE_RATIO = (4.0 * Math.PI) * 537.6;

    /**
     * Hardware
     */
    private DcMotor rightMaster_, leftMaster_ = null;
    private RevIMU imu = null;

    /**
     * Tracker
     */
    private static Pose currentPose = new Pose();
    private static double lastLeftDistance;
    private static double lastRightDistance;

    @Override
    public void start(double timestamp) {
        lastLeftDistance = getLeftDistance();
        lastRightDistance = getRightDistance();
    }

    @Override
    public void loop(double timestamp) {
        double avgDistance = (getLeftDistance() + getRightDistance()) / 2.0;
        double leftDistance = getLeftDistance()-lastLeftDistance;
        double rightDistance = getRightDistance()-lastRightDistance;

        double changeInAngle = (rightDistance - leftDistance) / Constants.WheelbaseWidth;
        double changeInX = avgDistance * Math.cos(changeInAngle);
        double changeInY = avgDistance * Math.sin(changeInAngle);

        Vector2d vector2d = Vector2d.add(getPose().getVector2D(), new Vector2d(changeInX, changeInY));
        Rotation2d rotation2d = Rotation2d.add(getPose().getRotation2d(), new Rotation2d(changeInAngle, Rotation2d.Unit.RADIANS));

        lastLeftDistance = leftDistance;
        lastRightDistance = rightDistance;

        getPose().setVector2D(vector2d);
        getPose().setRotation2d(rotation2d);
    }

    @Override
    public void stop(double timestamp) {
        
    }

    /**
     * Used to easily modify the type of control we want
     */
    public enum DriveControlStates {
        POWER,
        SPEED,
        POSITION,
        MOTION_PROFILE,
        OPERATOR_CONTROL
    }

    @Override
    public void init(HardwareMap hardwareMap, boolean auto) {
        this.rightMaster_ = hardwareMap.get(DcMotor.class, Constants.kRightDriveMasterId);
        this.leftMaster_ = hardwareMap.get(DcMotor.class, Constants.kLeftDriveMasterId);
        this.rightMaster_.setDirection(DcMotorSimple.Direction.REVERSE);

        if(LynxConstants.isRevControlHub()){ // Allows us to use MR practice bot
            imu = new RevIMU("imu", hardwareMap);
        }
    }

    @Override
    public void stop() {
        this.DriveZeroPowerState(DcMotor.ZeroPowerBehavior.FLOAT);
        this.DriveControlState(DriveControlStates.POWER);
        this.setLeftRightPower(0,0);
    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("Left Position", this.getLeftTicks());
        telemetry.addData("Right Position", this.getRightTicks());
        telemetry.addData("Left Power", this.leftMaster_.getPower());
        telemetry.addData("Right Power", this.rightMaster_.getPower());
        telemetry.addData("Gyro Angle", this.getAngle().getTheda(Rotation2d.Unit.DEGREES));
    }

    @Override
    public void zeroSensors() {
        resetEncoders();
    }

    @Override
    public void test(Telemetry telemetry) {
        if(getAngle().getTheda(Rotation2d.Unit.DEGREES)<0.5 && getAngle().getTheda(Rotation2d.Unit.DEGREES)>-0.5){
            RobotLog.a("Gyro OK");
            telemetry.addData("Gyro", "OK");
        } else {
            RobotLog.a("Gyro Bad");
            RobotLog.a("Gyro Angle: " + String.valueOf(getAngle().getTheda(Rotation2d.Unit.DEGREES)));
            telemetry.addData("Gyro", "OK");
            telemetry.addData("Gyro Angle", String.valueOf(getAngle().getTheda(Rotation2d.Unit.DEGREES)));
        }

        setLeftRightPower(0.1, 0);
        sleep(1000);
        setLeftRightPower(0, 0.1);
        sleep(1000);
        setLeftRightPower(0,0);
        if(getLeftTicks()!=0 && getLeftTicks()!=1) {
            RobotLog.a("Left Drive OK");
            telemetry.addData("Left Drive", "OK");
        } else {
            RobotLog.a("Left Drive Bad");
            RobotLog.a("Left Drive Ticks: "+String.valueOf(getLeftTicks()));
            telemetry.addData("Left Drive", "Bad");
            telemetry.addData("Left Drive Ticks", String.valueOf(getLeftTicks()));
        }

        if(getRightTicks()!=0 && getRightTicks()!=1) {
            RobotLog.a("Right Drive OK");
            telemetry.addData("Right Drive", "OK");
        }else {
            RobotLog.a("Right Drive Bad");
            RobotLog.a("Right Drive Ticks: "+ String.valueOf(getRightTicks()));
            telemetry.addData("Right Drive", "Bad");
            telemetry.addData("Right Drive Ticks", String.valueOf(getRightTicks()));
        }
    }

    @Override
    public String toString() {
        return "Drive";
    }

    /**
     * @return Angle of the robot in Rotation2d.
     */
    public Rotation2d getAngle(){
        try {
            return new Rotation2d(imu.getNormalHeading(), Rotation2d.Unit.DEGREES);
        } catch (Exception e){
            return new Rotation2d(0, Rotation2d.Unit.DEGREES);
        }
    }

    public Pose getPose() {
        return currentPose;
    }

    /**
     * @return Left distance in Inches
     */
    public double getLeftDistance(){
        return getLeftTicks() * ENCODER_TO_DISTANCE_RATIO;
    }

    /**
     * @return Right distance in Inches
     */
    public double getRightDistance(){
        return getRightTicks() * ENCODER_TO_DISTANCE_RATIO;
    }

    /**
     * @return Left distance in ticks
     */
    public int getLeftTicks() {
        return this.leftMaster_.getCurrentPosition();
    }

    /**
     * @return Right distance in ticks
     */
    public int getRightTicks(){
        return this.rightMaster_.getCurrentPosition();
    }

    /**
     * @param left Wanted Left Power between [-1.0,1.0]
     * @param right Wanted Right Power between [-1.0,1.0]
     */
    public void setLeftRightPower(double left, double right) {
        this.leftMaster_.setPower(CruiseLib.limitValue(left, 1, -1));
        this.rightMaster_.setPower(CruiseLib.limitValue(right, 1, -1));
    }

    /**
     * Think of a car. Your gas peddle is throttle. The steering wheel is turn
     */
    public void setThrottleSteerPower(double throttle, double turn){
        double left = throttle + turn;
        double right = throttle - turn;
        setLeftRightPower(left, right);
    }

    public void DriveControlState(DriveControlStates state){
        switch (state){
            case POWER:
                this.withoutEncoders();
                this.DriveZeroPowerState(DcMotor.ZeroPowerBehavior.FLOAT);
                break;
            case SPEED:
                this.withEncoders();
                this.DriveZeroPowerState(DcMotor.ZeroPowerBehavior.BRAKE);
                break;
            case POSITION:
                this.runToPosition();
                this.DriveZeroPowerState(DcMotor.ZeroPowerBehavior.BRAKE);
                break;
            case OPERATOR_CONTROL:
                this.withoutEncoders();
                this.DriveZeroPowerState(DcMotor.ZeroPowerBehavior.FLOAT);
                break;
            case MOTION_PROFILE:
                this.withoutEncoders();
                this.DriveZeroPowerState(DcMotor.ZeroPowerBehavior.FLOAT);
                break;
        }
    }

    public void DriveZeroPowerState(DcMotor.ZeroPowerBehavior behavior){
        this.leftMaster_.setZeroPowerBehavior(behavior);
        this.rightMaster_.setZeroPowerBehavior(behavior);
    }

    private void withoutEncoders(){
        this.leftMaster_.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.rightMaster_.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    private void withEncoders(){
        try {
            this.leftMaster_.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            this.rightMaster_.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        } catch (Exception e){}
    }

    private void runToPosition(){
        try {
            this.leftMaster_.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            this.rightMaster_.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } catch (Exception e){}
    }

    //Reset encoders until They both equal 0
    public void resetEncoders() {
        try {
            while(getLeftTicks() != 0 && getRightTicks() != 0){
                leftMaster_.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rightMaster_.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                Thread.yield();
            }
        } catch (Exception e) {}
    }
}