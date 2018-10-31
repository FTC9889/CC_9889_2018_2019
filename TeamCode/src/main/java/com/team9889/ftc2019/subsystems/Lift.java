package com.team9889.ftc2019.subsystems;

import com.qualcomm.hardware.motors.NeveRest20Gearmotor;
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
    private DigitalChannel touch;
    private PID pid = new PID(160, 32, 112);

    @Override
    public void init(HardwareMap hardwareMap, boolean auto) {
        left = hardwareMap.get(DcMotorEx.class, Constants.kLeftLift);
        right = hardwareMap.get(DcMotorEx.class, Constants.kRightLift);

        hook = hardwareMap.get(Servo.class, Constants.kHookServo);

        touch = hardwareMap.get(DigitalChannel.class, Constants.kLiftTouchSensor);
        touch.setMode(DigitalChannel.Mode.INPUT);

        left.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void zeroSensors() {

    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("Height of lift", getHeight());
        telemetry.addData("Difference of lift height", left.getCurrentPosition() - right.getCurrentPosition());
        telemetry.addData("Hook deployed?", isHookDeployed());
        telemetry.addData("Touch sensor pressed?", isBottomLimitReached());
    }

    @Override
    public void test(Telemetry telemetry) {

    }

    @Override
    public void stop() {

    }

    public double getHookPosition(){
        return (hook.getPosition()*180);
    }

    public boolean isHookDeployed(){
        return getHookPosition() > 90;
    }

    public boolean isBottomLimitReached(){
        return (touch.getState());
    }

    public double getHeightTicks(){
        return (left.getCurrentPosition() + right.getCurrentPosition()) / 2.;
    }

    public double getHeight(){
        return getHeightTicks() / Constants.kLiftTicksToHeightRatio;
    }

    public void setLiftPower(double power){
        if (power < 0 && isBottomLimitReached() == true){
            left.setPower(0);
            right.setPower(0);
        }
        else {
            left.setPower(power);
            right.setPower(power);
        }
    }

    @Override
    public String toString() {
        return "Lift";
    }
}
