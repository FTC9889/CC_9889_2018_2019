package com.team9889.ftc2019.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
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
//    private DigitalChannel lowerLimit;
//    private DigitalChannel upperLimit;
    private PID pid = new PID(.4, 0.005 ,0, 50);

    private LiftStates currentState = LiftStates.DOWN;
    private LiftStates wantedState = LiftStates.DOWN;

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
        telemetry.addData("left", left.getCurrentPosition());
        telemetry.addData("right", right.getCurrentPosition());
        telemetry.addData("Lift PID", pid.getOutput());
    }

    @Override
    public void update(ElapsedTime time) {
        switch (wantedState){
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

        if(inPosition())
            currentState = wantedState;
    }

    @Override
    public void test(Telemetry telemetry) {

    }

    @Override
    public void stop() {
        setLiftPower(0);
        setMode(DcMotor.ZeroPowerBehavior.BRAKE);
    }

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
        this.wantedState = state;
    }

    @Override
    public String toString() {
        return "Lift";
    }
}
