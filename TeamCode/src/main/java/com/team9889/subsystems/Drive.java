package com.team9889.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.RobotLog;
import com.team9889.Constants;
import com.team9889.lib.CruiseLib;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by joshua9889 on 10/6/2017.
 */

public class Drive extends Subsystem {

    //Identify variables
    private DcMotorEx rightMaster_, leftMaster_ = null;

    public enum DriveZeroPowerStates {
        BRAKE,
        FLOAT
    }

    public enum DriveControlStates {
        POWER,
        SPEED,
        POSITION,
        OPERATOR_CONTROL
    }

    @Override //This is KINDA like the hardwareMap, but then again I'm not too sure.
    public void init(HardwareMap hardwareMap, boolean auton) {
        this.rightMaster_ = hardwareMap.get(DcMotorEx.class, Constants.kRightDriveMasterId);
        this.leftMaster_ = hardwareMap.get(DcMotorEx.class, Constants.kLeftDriveMasterId);
        this.rightMaster_.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void stop() {
        this.DriveZeroPowerState(DriveZeroPowerStates.BRAKE);
        this.DriveControlState(DriveControlStates.POWER);
        this.setLeftRightPower(0,0);
    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("Left Position", this.getLeftTicks());
        telemetry.addData("Right Position", this.getRightTicks());
        telemetry.addData("Left Power", this.leftMaster_.getPower());
        telemetry.addData("Right Power", this.rightMaster_.getPower());
        telemetry.addData("Gyro Angle", this.getGyroAngleDegrees());
    }

    @Override
    public void zeroSensors() {
        resetEncoders();
    }

    @Override
    public void update() {

    }

    @Override
    public void test(Telemetry telemetry) {
        if(getGyroAngleDegrees()<0.5 && getGyroAngleDegrees()>-0.5){
            RobotLog.a("Gyro OK");
            telemetry.addData("Gyro", "OK");
        } else {
            RobotLog.a("Gyro Bad");
            RobotLog.a("Gyro Angle: " + String.valueOf(getGyroAngleDegrees()));
            telemetry.addData("Gyro", "OK");
            telemetry.addData("Gyro Angle", String.valueOf(getGyroAngleDegrees()));
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

    public double getGyroAngleDegrees() {
        return 0;
    }

    public double getGyroAngleRadians(){
        return CruiseLib.degreesToRadians(getGyroAngleDegrees());
    }

    public double getLeftDistanceInches(){
        return Constants.ticks2Inches(getLeftTicks());
    }

    public int getLeftTicks() {
        return this.leftMaster_.getCurrentPosition();
    }

    public double getRightDistanceInches(){
        return Constants.ticks2Inches(getRightTicks());
    }

    public int getRightTicks(){
        return this.rightMaster_.getCurrentPosition();
    }

    /**
     * @return Returns the current Left velocity , in Radians per second
     */
    public double getLeftVelocity(){
        return this.leftMaster_.getVelocity(AngleUnit.RADIANS);
    }

    /**
     * @return Returns the current Right velocity , in Radians per second
     */
    public double getRightVelocity(){
        return this.rightMaster_.getVelocity(AngleUnit.RADIANS);
    }

    /**
     * @param left Wanted Left Power between [-1.0,1.0]
     * @param right Wanted Right Power between [-1.0,1.0]
     */
    public void setLeftRightPower(double left, double right) {
        try {
            this.leftMaster_.setPower(CruiseLib.limitValue(left, 1, -1));
            this.rightMaster_.setPower(CruiseLib.limitValue(right, 1, -1));
        } catch (Exception e){}

    }

    public void SpeedTurn(double speed, double turn){
        double left = speed + turn;
        double right = speed - turn;
        setVelocityTarget(left, right);
    }

    /**
     * @param left Wanted Left Velocity, in Radians per second
     * @param right Wanted RIght Velocity, in Radians per second
     */
    public void setVelocityTarget(double left, double right) {
        this.leftMaster_.setVelocity(2*left, AngleUnit.RADIANS);
        this.rightMaster_.setVelocity(2*right, AngleUnit.RADIANS);
    }

    public void DriveControlState(DriveControlStates state){
        switch (state){
            case POWER:
                this.withoutEncoders();
                break;
            case SPEED:
                this.withEncoders();
                break;
            case POSITION:
                this.runToPosition();
                break;
            case OPERATOR_CONTROL:
                this.withoutEncoders();
                break;
        }
    }

    public void DriveZeroPowerState(DriveZeroPowerStates state){
        switch (state){
            case BRAKE:
                this.BRAKE();
                break;
            case FLOAT:
                this.FLOAT();
                break;
        }
    }

    private void BRAKE(){
        try {
            this.leftMaster_.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            this.rightMaster_.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        } catch (Exception e){}
    }

    private void FLOAT(){
        try {
            this.leftMaster_.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            this.rightMaster_.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        } catch (Exception e){}
    }

    private void withoutEncoders(){
        try {
            this.leftMaster_.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            this.rightMaster_.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        } catch (Exception e){}
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
            }
        } catch (Exception e) {}
    }
}
