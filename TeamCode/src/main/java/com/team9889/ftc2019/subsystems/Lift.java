package com.team9889.ftc2019.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.team9889.ftc2019.Constants;
import com.team9889.lib.control.controllers.PID;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by joshua9889 on 3/28/2018.
 *
 * Example subsystem
 */

public class Lift extends Subsystem {

    private DcMotorEx left, right;
    private Servo hook;
    private Servo stopper;
//    private DigitalChannel lowerLimit;
//    private DigitalChannel upperLimit;
    private PID pid = new PID(.3, 0.005 ,0, 50);


    public boolean inPosition() {
        return Math.abs(pid.getError()) < 0.5;
    }

    public enum LiftStates{
        DOWN, HOOKHEIGHT, SCOREINGHEIGHT, READY
    }

    @Override
    public void init(HardwareMap hardwareMap, boolean auto) {
        left = hardwareMap.get(DcMotorEx.class, Constants.kLeftLift);
        right = hardwareMap.get(DcMotorEx.class, Constants.kRightLift);
        right.setDirection(DcMotorSimple.Direction.REVERSE);
        left.setDirection(DcMotorSimple.Direction.REVERSE);
        setMode(DcMotor.ZeroPowerBehavior.BRAKE);

        if(auto)
            zeroSensors();


//        hook = hardwareMap.get(Servo.class, Constants.kHookServo);
//        stopper = hardwareMap.get(Servo.class, Constants.kLiftStopServo);

//        lowerLimit = hardwareMap.get(DigitalChannel.class, Constants.kLiftUpperLimitSensor);
//        lowerLimit.setMode(DigitalChannel.Mode.INPUT);

//        upperLimit = hardwareMap.get(DigitalChannel.class, Constants.kLiftLowerLimitSensor);
//        upperLimit.setMode(DigitalChannel.Mode.INPUT);

//        if(auto) {
//            setHookPosition(180);
//            setStopperPosition(0);
//            zeroSensors();
//        }
//        else
//            setStopperPosition(.3);
    }

    @Override
    public void zeroSensors() {
        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        Thread.yield();

        left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("Height of lift", getHeight());
        telemetry.addData("Height of lift in ticks", getHeightTicks());
//        telemetry.addData("Difference of lift height", left.getCurrentPosition() - right.getCurrentPosition());
//        telemetry.addData("Hook deployed?", isHookDeployed());
//        telemetry.addData("", stopper.getPosition());
//        telemetry.addData("Touch sensor pressed?", isBottomLimitReached());
        telemetry.addData("left", left.getCurrentPosition());
        telemetry.addData("right", right.getCurrentPosition());
        telemetry.addData("Lift PID", pid.getOutput());
    }

    @Override
    public void test(Telemetry telemetry) {

    }

    @Override
    public void stop() {
        setLiftPower(0);
        setMode(DcMotor.ZeroPowerBehavior.BRAKE);
    }

//    public void setHookPosition(double position){
//        hook.setPosition(position/180.0);
//    }

//    public double getHookPosition(){
//        return hook.getPosition()*180;
//    }

//    public boolean isHookDeployed(){
//        return getHookPosition() > 90;
//    }

//    public boolean isBottomLimitReached(){
//        return touch.getState();
//    }

    public double getHeightTicks(){
        return (left.getCurrentPosition());
    }

    public double getHeight(){
        return getHeightTicks() * Constants.kLiftTicksToHeightRatio;
    }

    public void setLiftPower(double power){
        left.setPower(power);
        right.setPower(power);
    }

//    public void setStopperPosition(double position){
//        stopper.setPosition(position);
//    }

    public void setMode(DcMotor.ZeroPowerBehavior zeroPowerBehavior){
        left.setZeroPowerBehavior(zeroPowerBehavior);
        right.setZeroPowerBehavior(zeroPowerBehavior);
    }

    public double getLiftPower(){
        return left.getPower();
    }


    /**
     * @param wantedHeight In inches
     */
    public void setLiftPosition(double wantedHeight){
        setLiftPower(pid.update(getHeight(), wantedHeight));
        if (getLiftPower() < 1){
            setLiftPower(0);
        }
    }

    public void setLiftState(LiftStates state){
        switch (state){
            case DOWN:
                setLiftPosition(0);
                break;

            case HOOKHEIGHT:
                setLiftPosition(20);
                break;

            case SCOREINGHEIGHT:
                setLiftPosition(13);
                break;

            case READY:
                setLiftPosition(5);
        }
    }

    @Override
    public String toString() {
        return "Lift";
    }
}
