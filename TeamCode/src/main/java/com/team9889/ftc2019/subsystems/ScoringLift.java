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
import com.team9889.lib.RunningAverage;
import com.team9889.lib.control.controllers.PID;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by joshua9889 on 3/28/2018.
 */

public class ScoringLift extends Subsystem {

    private DcMotorEx liftMotor;
    private DigitalChannel lowerLimit;
    private PID pid;
    public boolean liftOperatorControl = false;

    private double offset = 0;

    private boolean downFirst = true;
    private ElapsedTime timer = new ElapsedTime();

    private double lastTime = 0;
    private double lastPosition = 0;
    private double currentSpeed = 0;
    private boolean first = true;
    private RunningAverage averageSpeed = new RunningAverage(10);

    private LiftStates currentState = LiftStates.NULL;
    private LiftStates wantedState = LiftStates.NULL;

    public static void main(String... args) {
        System.out.println(-2625 + 2500);

        double p = .01/2625.0;
        System.out.println(2625.0/2 * p);
        System.out.println(p *2);
        System.out.println(p);
    }

    @Override
    public void init(HardwareMap hardwareMap, boolean auto) {
        liftMotor = hardwareMap.get(DcMotorEx.class, Constants.ScoringLiftConstants.kLeftLiftId);

        lowerLimit = hardwareMap
                .get(DigitalChannel.class, Constants.ScoringLiftConstants.kLiftLowerLimitSensorId);

        liftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        pid = new PID(1./125., 0, 0.005);

        averageSpeed.calculate(20);

        if(auto) {
            zeroSensors();
            currentState = LiftStates.DOWN;
            wantedState = LiftStates.DOWN;
        } else {
            getLowerLimitPressed();
            wantedState = LiftStates.NULL;
        }

        first = true;
    }

    @Override
    public void zeroSensors() {
        offset = getHeightTicks();
    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("Velocity", currentSpeed);
        telemetry.addData("Average Speed", averageSpeed.get());
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

        RobotLog.e(String.valueOf(averageSpeed.get()));

        if(first) {
            lastPosition = getHeight();
            lastTime = time.milliseconds();
            first = !first;
        } else {
            currentSpeed = (getHeight() - lastPosition) / (time.milliseconds() - lastTime);
            averageSpeed.calculate(currentSpeed);

            lastPosition = getHeight();
            lastTime = time.milliseconds();
        }

        if(currentState != wantedState) {
            switch (wantedState) {
                case DOWN:
                    setLiftPower(1);

                    if (downFirst) {
                        timer.reset();
                        downFirst = false;
                    }else if (getLowerLimitPressed() || timer.milliseconds() > 2000) {
                        setLiftPower(0);
                        currentState = LiftStates.DOWN;
                        downFirst = true;
                    }
                    break;

                case UP:
                    setLiftPosition(-2290);

                    if (getHeight() < -2170 || Math.abs(averageSpeed.get()) < 0.01) {
                        setLiftPower(0);
                        currentState = LiftStates.UP;
                    }
                    break;

                case NULL:
                    setLiftPower(0);
                    currentState = LiftStates.NULL;
                    break;
            }
        } else {
            averageSpeed.calculate(2);
        }

        liftOperatorControl = currentState == wantedState;
    }

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
    }

    /**
     * @param wantedHeight In inches
     */
    private void setLiftPosition(double wantedHeight) {
        double power = pid.update(getHeight(), wantedHeight);

        setLiftPower(power);
    }

    private double getHeightTicks() {
        return liftMotor.getCurrentPosition();
    }

    public double getHeight() {
        return (getHeightTicks() - offset);
    }

    private boolean inPosition() {
        return Math.abs(pid.getError()) < 200;
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
