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
    private DigitalChannel touch;
    private PID pid = new PID(.001, 0 ,0);

    public enum LiftStates{
        DOWN, HOOKHEIGHT, SCOREINGHEIGHT
    }

    @Override
    public void init(HardwareMap hardwareMap, boolean auto) {
        left = hardwareMap.get(DcMotorEx.class, Constants.kLeftLift);
        right = hardwareMap.get(DcMotorEx.class, Constants.kRightLift);
        right.setDirection(DcMotorSimple.Direction.REVERSE);
        left.setDirection(DcMotorSimple.Direction.REVERSE);
        setMode(DcMotor.ZeroPowerBehavior.BRAKE);

        hook = hardwareMap.get(Servo.class, Constants.kHookServo);
        stopper = hardwareMap.get(Servo.class, Constants.kLiftStopServo);

        touch = hardwareMap.get(DigitalChannel.class, Constants.kLiftTouchSensor);
        touch.setMode(DigitalChannel.Mode.INPUT);

        if(auto) {
            setHookPosition(180);
            setStopperPosition(0);
            zeroSensors();
        }
        else
            setStopperPosition(.3);
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
        telemetry.addData("Difference of lift height", left.getCurrentPosition() - right.getCurrentPosition());
        telemetry.addData("Hook deployed?", isHookDeployed());
        telemetry.addData("", stopper.getPosition());
        telemetry.addData("Touch sensor pressed?", isBottomLimitReached());
        telemetry.addData("left", left.getCurrentPosition());
        telemetry.addData("right", right.getCurrentPosition());
    }

    @Override
    public void test(Telemetry telemetry) {

    }

    @Override
    public void stop() {
        setLiftPower(0);
        setMode(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setHookPosition(double position){
        hook.setPosition(position/180.0);
    }

    public double getHookPosition(){
        return hook.getPosition()*180;
    }

    public boolean isHookDeployed(){
        return getHookPosition() > 90;
    }

    public boolean isBottomLimitReached(){
        return touch.getState();
    }

    public double getHeightTicks(){
        return (right.getCurrentPosition());
    }

    public double getHeight(){
        return getHeightTicks() * Constants.kLiftTicksToHeightRatio;
    }

    public void setLiftPower(double power){
        left.setPower(power);
        right.setPower(power);
    }

    public void setStopperPosition(double position){
        stopper.setPosition(position);
    }

    public void setMode(DcMotor.ZeroPowerBehavior zeroPowerBehavior){
        left.setZeroPowerBehavior(zeroPowerBehavior);
        right.setZeroPowerBehavior(zeroPowerBehavior);
    }


    /**
     * @param wantedHeight In inches
     */
    public void setLiftPosition(double wantedHeight){
        setLiftPower(pid.update(getHeight(), wantedHeight));
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
                setLiftPosition(30);
                break;
        }
    }

    @Override
    public String toString() {
        return "Lift";
    }
}
