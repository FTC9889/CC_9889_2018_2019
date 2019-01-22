package com.team9889.ftc2019.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.Constants;
import com.team9889.ftc2019.states.LiftStates;
import com.team9889.lib.control.controllers.PID;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by joshua9889 on 3/28/2018.
 * <p>
 * Example subsystem
 */

public class Lift extends Subsystem {

    private DcMotorEx left, right;
    private DigitalChannel lowerLimit;
    private DigitalChannel upperLimit;
    private DistanceSensor robotToGround;
    private PID pid = new PID(.42, 0.005, 0, 50);

    private LiftStates currentState = LiftStates.NULL;
    private LiftStates wantedState = LiftStates.NULL;

    @Override
    public void init(HardwareMap hardwareMap, boolean auto) {
        left = hardwareMap.get(DcMotorEx.class, Constants.LiftConstants.kLeftLiftId);
        right = hardwareMap.get(DcMotorEx.class, Constants.LiftConstants.kRightLiftId);

        lowerLimit = hardwareMap.get(DigitalChannel.class, Constants.LiftConstants.kLiftLowerLimitSensorId);
        upperLimit = hardwareMap.get(DigitalChannel.class, Constants.LiftConstants.kLiftUpperLimitSensorId);

        robotToGround = hardwareMap.get(DistanceSensor.class, Constants.LiftConstants.kRobotToGround);

        right.setDirection(DcMotorSimple.Direction.REVERSE);
        left.setDirection(DcMotorSimple.Direction.REVERSE);
        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        currentState = LiftStates.NULL;
        wantedState = LiftStates.NULL;

        if (auto)
            setLiftState(LiftStates.HANGING);
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
        telemetry.addData("Lift PID Output", pid.getOutput());

        telemetry.addData("Upper limit pressed", getUpperLimitPressed());
        telemetry.addData("Lower limit pressed", getLowerLimitPressed());

        telemetry.addData("Is Lift in position", isCurrentWantedState());

        telemetry.addData("Wanted State", wantedState);
        telemetry.addData("Current State", currentState);
    }

    @Override
    public void update(ElapsedTime time) {
        if (currentState != wantedState) {
            switch (wantedState) {
                case DOWN:
                    if (getLowerLimitPressed()) {
                        setLiftPower(0);
                        zeroSensors();
                        currentState = LiftStates.DOWN;
                    } else {
                        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        setLiftPower(-.7);
                    }
                    break;

                case HOOKHEIGHT:
                    setLiftPosition(11);
                    Robot.getInstance().liftCruiseControl = false;
                    if (inPosition()) {
                        Robot.getInstance().liftCruiseControl = true;
                        currentState = LiftStates.HOOKHEIGHT;
                    }
                    break;

                case SCOREINGHEIGHT:
                    Robot.getInstance().liftCruiseControl = false;
                    if (getUpperLimitPressed()) {
                        setLiftPower(0);
                        Robot.getInstance().liftCruiseControl = true;
                        currentState = LiftStates.SCOREINGHEIGHT;
                    } else {
                        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        setLiftPower(1);
                    }
                    break;

                case READY:
                    setLiftPosition(7);

                    if (inPosition()) {
                        setLiftPower(0);
                        currentState = wantedState;
                    }
                    break;

                case HANGING:
                    if (getLowerLimitPressed()) {
                        setLiftPower(-0.2);
                        zeroSensors();
                    } else {
                        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        setLiftPower(-.7);
                    }
                    break;

                case NULL:
                    setLiftPower(0);
                    currentState = LiftStates.NULL;
                    break;
            }
        }
    }

    @Override
    public void test(Telemetry telemetry) {

    }

    @Override
    public void stop() {
        setLiftState(LiftStates.NULL);
    }

    public void setLiftPower(double power) {
        left.setPower(power);
        right.setPower(power);
    }

    /**
     * @param wantedHeight In inches
     */
    public void setLiftPosition(double wantedHeight) {
        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setLiftPower(pid.update(getHeight(), wantedHeight));
    }

    private double getHeightTicks() {
        return left.getCurrentPosition();
    }

    public double getHeight() {
        return getHeightTicks() * Constants.LiftConstants.kLiftTicksToHeightRatio;
    }

    private boolean inPosition() {
        return Math.abs(pid.getError()) < 0.5;
    }

    public boolean getUpperLimitPressed() {
        return !upperLimit.getState();
    }

    public boolean getLowerLimitPressed() {
        return !lowerLimit.getState();
    }

    public void setLiftState(LiftStates state) {
        this.wantedState = state;
    }

    public boolean isCurrentWantedState() {
        return currentState == wantedState;
    }

    public LiftStates getWantedState() {
        return wantedState;
    }

    public double getDistanceSensorRange() {
        return robotToGround.getDistance(DistanceUnit.INCH);
    }

    @Override
    public String toString() {
        return "Lift";
    }

    private void setRunMode(DcMotor.RunMode runMode) {
        left.setMode(runMode);
        right.setMode(runMode);
    }
}
