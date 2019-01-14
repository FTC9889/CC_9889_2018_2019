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
    private DigitalChannel lowerLimit;
    private DigitalChannel upperLimit;
    private PID pid = new PID(.4, 0.005 ,0, 50);

    private LiftStates currentState = LiftStates.NULL;
    private LiftStates wantedState = LiftStates.NULL;

    public boolean inPosition() {
        return Math.abs(pid.getError()) < 0.5;
    }

    public enum LiftStates{
        DOWN, HOOKHEIGHT, SCOREINGHEIGHT, READY, NULL
    }

    @Override
    public void init(HardwareMap hardwareMap, boolean auto) {
        left = hardwareMap.get(DcMotorEx.class, Constants.LiftConstants.kLeftLift);
        right = hardwareMap.get(DcMotorEx.class, Constants.LiftConstants.kRightLift);

        lowerLimit = hardwareMap.get(DigitalChannel.class, Constants.LiftConstants.kLiftLowerLimitSensor);
        upperLimit = hardwareMap.get(DigitalChannel.class, Constants.LiftConstants.kLiftUpperLimitSensor);

        right.setDirection(DcMotorSimple.Direction.REVERSE);
        left.setDirection(DcMotorSimple.Direction.REVERSE);
        setMode(DcMotor.ZeroPowerBehavior.BRAKE);

        currentState = LiftStates.NULL;
        wantedState = LiftStates.NULL;

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

        telemetry.addData("Upper limit pressed", getUpperLimitPressed());
        telemetry.addData("Lower limit pressed", getLowerLimitPressed());

        telemetry.addData("Is Lift in position", isCurrentWantedState());
    }

    @Override
    public void update(ElapsedTime time) {
        if (currentState != wantedState){
            switch (wantedState) {
                case DOWN:
                    if (getLowerLimitPressed()){
                        setLiftPower(0);
                        zeroSensors();
                        currentState = LiftStates.DOWN;
                    } else {
                        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        setLiftPower(-.3);
                    }
                break;

                case HOOKHEIGHT:
                    setLiftPosition(20);

                    if(inPosition())
                        currentState = wantedState;
                break;

                case SCOREINGHEIGHT:
                    if (getUpperLimitPressed()){
                        setLiftPower(0);
                        currentState = LiftStates.SCOREINGHEIGHT;
                    } else {
                        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        setLiftPower(.3);
                    }
                break;

                case READY:
                    setLiftPosition(10);

                    if(inPosition())
                        currentState = wantedState;
                break;
            }
        }
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
        return getHeightTicks() * Constants.LiftConstants.kLiftTicksToHeightRatio;
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
        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setLiftPower(pid.update(getHeight(), wantedHeight));
    }

    public boolean getUpperLimitPressed(){
        return !upperLimit.getState();
    }

    public boolean getLowerLimitPressed(){
        return !lowerLimit.getState();
    }

    public void setLiftState(LiftStates state){
        this.wantedState = state;
    }

    public boolean isCurrentWantedState(){
        return currentState == wantedState;
    }

    @Override
    public String toString() {
        return "Lift";
    }

    private void setRunMode(DcMotor.RunMode runMode){
        left.setMode(runMode);
        right.setMode(runMode);
    }
}
