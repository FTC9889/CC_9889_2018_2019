package com.team9889.ftc2019.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;
import com.team9889.ftc2019.Constants;
import com.team9889.ftc2019.states.LiftStates;
import com.team9889.lib.control.controllers.PID;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by joshua9889 on 3/28/2018.
 */

public class ScoringLift extends Subsystem {

    private DcMotorEx liftMotor;
    private DigitalChannel lowerLimit;
    private boolean auto;
    private PID pid = new PID(.6, 0, 0.6);
    public boolean liftOperatorControl = false;

    private double offset = 0;

    private LiftStates currentState = LiftStates.NULL;
    private LiftStates wantedState = LiftStates.NULL;

    @Override
    public void init(HardwareMap hardwareMap, boolean auto) {
        liftMotor = hardwareMap.get(DcMotorEx.class, Constants.ScoringLiftConstants.kLeftLiftId);

        lowerLimit = hardwareMap.get(DigitalChannel.class, Constants.ScoringLiftConstants.kLiftLowerLimitSensorId);

        liftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        offset = 0;
        getLowerLimitPressed();

        currentState = LiftStates.NULL;
        wantedState = LiftStates.NULL;
    }

    @Override
    public void zeroSensors() {
        offset = getHeightTicks();
    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("Offset", offset);
        telemetry.addData("Height of lift", getHeight());
        telemetry.addData("Height of lift in ticks", getHeightTicks());
        telemetry.addData("ScoringLift PID Output", pid.getOutput());

        telemetry.addData("Lower limit pressed", getLowerLimitPressed());

        telemetry.addData("Is ScoringLift in position", isCurrentWantedState());

        telemetry.addData("Wanted State", wantedState);
        telemetry.addData("Current State", currentState);
    }

    @Override
    public void update(ElapsedTime time) {
        getLowerLimitPressed();

        switch (wantedState) {
            case DOWN:
                setLiftPosition(0);

                if (inPosition()) {
                    setLiftPower(0);
                    currentState = LiftStates.DOWN;
                }
                break;

            case UP:
                setLiftPosition(-1500);

                if (getHeight() < -1150) {
                    setLiftPower(0);
                    currentState = LiftStates.UP;
                }
                break;

            case NULL:
                setLiftPower(0);
                currentState = LiftStates.NULL;
                break;
        }

        liftOperatorControl = currentState == wantedState;
    }

    @Override
    public void test(Telemetry telemetry) {}

    @Override
    public void stop() {
        setLiftState(LiftStates.NULL);
    }

    /**
     * @param power Power to the motors
     */
    public void setLiftPower(double power) {
        double mPower = power;
        if((getLowerLimitPressed() && mPower>0))
            mPower = 0;

        liftMotor.setPower(mPower);
        RobotLog.a("Power to ScoringLift: " + String.valueOf(mPower));
    }

    /**
     * @param wantedHeight In inches
     */
    private void setLiftPosition(double wantedHeight) {
        double power = pid.update(getHeight(), wantedHeight);

        if (power>0.3)
            power = 0.3;

        setLiftPower(power);
    }

    private double getHeightTicks() {
        return liftMotor.getCurrentPosition();
    }

    public double getHeight() {
        return (getHeightTicks() - offset);
    }

    private boolean inPosition() {
        return Math.abs(pid.getError()) < 0.5;
    }

    public boolean getLowerLimitPressed() {
        boolean lowerLimitPressed = !lowerLimit.getState();
        if (lowerLimitPressed)
            zeroSensors();

        return lowerLimitPressed;
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

    public LiftStates getCurrentState() {
        return currentState;
    }

    @Override
    public String toString() {
        return "ScoringLift";
    }
}
