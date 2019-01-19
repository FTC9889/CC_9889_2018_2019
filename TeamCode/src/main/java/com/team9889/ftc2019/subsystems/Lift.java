package com.team9889.ftc2019.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.Constants;
import com.team9889.ftc2019.states.LiftStates;
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
    private double wantedHeight = 0;
    private boolean bangBang = true;
    private boolean inPosition = false;
    private PID pid = new PID(.4, 0.005 ,0, 50);

    public boolean inPosition() {
        return ;
    }

    @Override
    public void init(HardwareMap hardwareMap, boolean auto) {
        left = hardwareMap.get(DcMotorEx.class, Constants.LiftConstants.kLeftLiftId);
        right = hardwareMap.get(DcMotorEx.class, Constants.LiftConstants.kRightLiftId);

        lowerLimit = hardwareMap.get(DigitalChannel.class, Constants.LiftConstants.kLiftLowerLimitSensorId);
        upperLimit = hardwareMap.get(DigitalChannel.class, Constants.LiftConstants.kLiftUpperLimitSensorId);

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

        telemetry.addData("Upper limit pressed", getUpperLimitPressed());
        telemetry.addData("Lower limit pressed", getLowerLimitPressed());
    }

    @Override
    public void update(ElapsedTime time) {
        if(!bangBang)
            setLiftPower(pid.update(getHeight(), wantedHeight));
        else {
            if(getHeight() > wantedHeight + 0.5)
                setLiftPower(-0.3);
            else if(getHeight() < wantedHeight - 0.5)
                setLiftPower(0.3);
            else
                setLiftPower(0.0);
        }
    }

    @Override
    public void test(Telemetry telemetry) {}

    @Override
    public void stop() {
        setLiftPower(0);
        setMode(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    private double getHeightTicks(){
        return (left.getCurrentPosition());
    }

    public double getHeight(){
        return getHeightTicks() * Constants.LiftConstants.kLiftTicksToHeightRatio;
    }

    public void setLiftPower(double power){
        left.setPower(power);
        right.setPower(power);
    }

    private void setMode(DcMotor.ZeroPowerBehavior zeroPowerBehavior){
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
        this.wantedHeight = wantedHeight;
    }

    public boolean getUpperLimitPressed(){
        return !upperLimit.getState();
    }

    public boolean getLowerLimitPressed(){
        return !lowerLimit.getState();
    }

    @Override
    public String toString() {
        return "Lift";
    }

    public void setRunMode(DcMotor.RunMode runMode){
        left.setMode(runMode);
        right.setMode(runMode);
    }
}
