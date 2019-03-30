package com.team9889.ftc2019.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.team9889.ftc2019.Constants;
import com.team9889.ftc2019.states.LiftStates;
import com.team9889.lib.CruiseLib;
import com.team9889.lib.control.controllers.PID;
import com.team9889.lib.hardware.RevColorDistance;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by licorice17 on 9/14/2018.
 */

public class Intake extends Subsystem {

    // Hardware
    private DcMotor intakeMotor, extender;
    private Servo intakeRotator, intakeGate;
    private DigitalChannel scoringSwitch, inSwitch;
    public RevColorDistance revLeftHopper, revRightHopper;

    // PID for extending the intake
    private PID extenderPID = new PID(0.5, 0.0, 2);

    // Tracker
    private double maximumPosition = 23; // Inches
    private double offset = 0; // Ticks

    //
    private boolean intakeOperatorControl = true;
    public IntakeStates currentIntakeState = IntakeStates.NULL;
    private IntakeStates wantedIntakeState = IntakeStates.ZEROING;
    private boolean first = true;
    private double intakeRotatorPosition = 0;

    public boolean intakeZeroingHardstop;

    public double autoIntakeOut;
    private boolean auto;

    private boolean firstIntaking = true;
    public ElapsedTime transitionTimer = new ElapsedTime();

    public ElapsedTime hardStopTimer = new ElapsedTime();
    public ElapsedTime collectingTimer = new ElapsedTime();
    private boolean transitionExtender = true;

    public ElapsedTime settleTimer = new ElapsedTime();
    public Boolean firstSettle = true;

    public ElapsedTime autonomousTimer = new ElapsedTime();
    public boolean autonomousFirst = true;

    public RotatorStates currentIntakeRotatorState;

    public boolean isIntakeOperatorControl() {
        return intakeOperatorControl;
    }

    @Override
    public void init(HardwareMap hardwareMap, boolean auto) {
        intakeMotor = hardwareMap.get(DcMotor.class, Constants.IntakeConstants.kIntakeMotorId);
        extender = hardwareMap.get(DcMotor.class, Constants.IntakeConstants.kIntakeExtenderId);

        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        extender.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        extender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intakeRotator = hardwareMap.get(Servo.class, Constants.IntakeConstants.kIntakeRotatorId);
        intakeGate = hardwareMap.get(Servo.class, Constants.IntakeConstants.kIntakeGate);

        inSwitch = hardwareMap.get(DigitalChannel.class, Constants.IntakeConstants.kIntakeInSwitchId);

        offset = 0;
        this.auto = auto;
        if (auto){
            zeroSensors();
            wantedIntakeState = IntakeStates.ZEROING;
            currentIntakeState = IntakeStates.ZEROING;
        }else {
            wantedIntakeState = IntakeStates.NULL;
        }

    }

    @Override
    public void zeroSensors() {
        offset = getIntakeExtenderPositionTicks();
    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("PID Output", extenderPID.getOutput());
        telemetry.addData("Left Rev Distance", revLeftHopper.getIN());
        telemetry.addData("Right Rev Distance", revRightHopper.getIN());

        telemetry.addData("IntakePower", intakeMotor.getPower());
        telemetry.addData("Intake Extender Real Position", getIntakeExtenderPosition());
        telemetry.addData("Intake Extender Position", extender.getCurrentPosition());
        telemetry.addData("Offset", offset);
        telemetry.addData("Offset is true", !CruiseLib.isBetween(offset, -0.1, 0.1));

        telemetry.addData("Angle of Intake", intakeRotator.getPosition());
        telemetry.addData("Fully In Intake Switch", intakeInSwitchValue());

        telemetry.addData("Wanted State", wantedIntakeState);
        telemetry.addData("Current State", currentIntakeState);

        telemetry.addData("Intake Cruise Control", isIntakeOperatorControl());


        telemetry.addData("Current Rotator State", currentIntakeRotatorState);
    }

    @Override
    public void update(ElapsedTime time) {

        switch (wantedIntakeState) {
            case INTAKING:
                if (Robot.getInstance().stopIntake) {
                        intakeOperatorControl = false;

                        currentIntakeState = IntakeStates.INTAKING;
                        setWantedIntakeState(IntakeStates.GRABBING);
                        firstIntaking = true;
                } else {
                    intakeOperatorControl = true;
                    intake();
                }
                break;
            case GRABBING:
                if (intakeInSwitchValue()){
                    setIntakeExtenderPower(0);
                    intakeOperatorControl = true;
                    transitionTimer.reset();
                    currentIntakeState = IntakeStates.GRABBING;
                    wantedIntakeState = IntakeStates.TRANSITION;
                }else{
                    intakeOperatorControl = false;
                    setIntakeRotatorState(RotatorStates.UP);

                    setIntakeExtenderPower(-1);
                }
                break;

            case ZEROING:
                if (currentIntakeState != wantedIntakeState) {
                    if (intakeInSwitchValue()) {
                        setIntakeExtenderPower(0);
                        intakeOperatorControl = true;
                        currentIntakeState = IntakeStates.ZEROING;
                    } else {
                        intakeOperatorControl = false;
                        setIntakeRotatorState(RotatorStates.UP);

                        setIntakeExtenderPower(-0.5);
                    }
                }
                break;

            case AUTONOMOUS:
                if (getIntakeExtenderPosition() >= autoIntakeOut){
                    setIntakeExtenderPower(0);
                    currentIntakeState = IntakeStates.AUTONOMOUS;
                }else {
                    setIntakeExtenderPower(1);
                    autonomousFirst = true;
                }
                break;

            case NULL:
                currentIntakeState = IntakeStates.NULL;
                intakeOperatorControl = true;
                break;

            case TRANSITION:
                if (Robot.getInstance().getLift().getCurrentState() == LiftStates.READY) {
                    if (transitionTimer.milliseconds() < 300){
                        setIntakeGateState(IntakeGateStates.UP);
                    }else if(transitionTimer.milliseconds() > 300 && transitionTimer.milliseconds() < 500) {
                        intakeOperatorControl = false;
                        setIntakeExtenderPower(1);
                        transitionExtender = true;
                        Robot.getInstance().transitionDone = true;
                    } else if(transitionTimer.milliseconds() > 500 && transitionTimer.milliseconds() < 700) {
                        setIntakeExtenderPower(0);
                        transitionExtender = false;
                        Robot.getInstance().stopIntake = false;
                        currentIntakeState = IntakeStates.TRANSITION;
                        intakeOperatorControl = true;
                    }
                }
                break;

            case DRIVER:
                break;
        }
    }

    @Override
    public void test(Telemetry telemetry) {
    }

    @Override
    public void stop() {
        setIntakePower(0);
    }

    /**
     * @param power Power that the Core Hex Motor should go
     */
    public void setIntakePower(double power) {
        intakeMotor.setPower(power);
    }

    /**
     * Turn On Intake
     */
    public void intake() {
        setIntakePower(1);
        if (auto)
            setIntakeRotatorState(RotatorStates.DOWN);
    }

    /**
     * Outtake
     */
    public void outtake() {
        setIntakePower(-1);
    }

    /**
     * @param power Power the extender should go at
     */
    public void setIntakeExtenderPower(double power) {
        if (offset != 0 && (getIntakeExtenderPosition() > maximumPosition && power > 0)
                || (intakeInSwitchValue() && power < 0))
            extender.setPower(0);
        else
            extender.setPower(power);
    }

    public double getIntakeExtenderPosition() {
        return (getIntakeExtenderPositionTicks() - offset) * Constants.IntakeConstants.kIntakeTicksToInchRatio;
    }

    /**
     * @param position Position that the intake should go to. In Inches
     */
    public void setIntakeExtenderPosition(double position) {
        position = Range.clip(position, 0, maximumPosition);

        double currentPosition = getIntakeExtenderPosition();
        double power = extenderPID.update(currentPosition, position);

        if (offset == 0.0 && (getIntakeExtenderPosition() > maximumPosition && power > 0) || (intakeInSwitchValue() && power < 0))
            extender.setPower(0);
        else
            setIntakeExtenderPower(power);
    }

    private double getIntakeExtenderPositionTicks() {
        return extender.getCurrentPosition();
    }

    /**
     * @param wantedState Set the wanted state of the Intake
     */
    public void setWantedIntakeState(IntakeStates wantedState) {
        this.wantedIntakeState = wantedState;
    }

    /**
     * @return If the current state of the intake is equal to the wanted state
     */
    public boolean isCurrentStateWantedState() {
        return (currentIntakeState == wantedIntakeState);
    }

    public IntakeStates getCurrentIntakeState() {
        return currentIntakeState;
    }

    /**
     * @param position Set the position of the Intake
     */
    private void setIntakeRotatorPosition(double position) {
        intakeRotatorPosition = position;
        setIntakeRotatorPosition();
    }

    private void setIntakeRotatorPosition() {
        if (!CruiseLib.isBetween(intakeRotatorPosition, -1, 0.1))
            intakeRotator.setPosition(intakeRotatorPosition);
    }

    private void setIntakeGatePosition(double position) {
        intakeGate.setPosition(position);
    }

    public void setIntakeRotatorState(RotatorStates state) {
        switch (state) {
            case UP:
                if (auto)
                    setIntakeRotatorPosition(0.4);
                else
                    setIntakeRotatorPosition(0.46);
                currentIntakeRotatorState = RotatorStates.UP;
                break;

            case DOWN:
                setIntakeRotatorPosition(.9);
                currentIntakeRotatorState = RotatorStates.DOWN;
                break;
        }
    }

    public void setIntakeGateState(IntakeGateStates state){
        switch (state){
            case UP:
                setIntakeGatePosition(1);
                break;

            case DOWN:
                setIntakeGatePosition(0.5);
                break;
        }
    }

    /**
     * @return If the ScoringLift is pressing the Lower Limit Switch
     */
    private boolean intakeInSwitchValue() {
        boolean intakeInSwitch = !inSwitch.getState();
        if (intakeInSwitch)
            offset = getIntakeExtenderPositionTicks();

        return intakeInSwitch;
    }

    public boolean inPosition(){
        return Math.abs(extenderPID.getError()) < .5;
    }

    @Override
    public String toString() {
        return "Intake";
    }

    public enum IntakeStates {
        INTAKING, GRABBING, ZEROING, AUTONOMOUS, NULL, EXTENDED, DRIVER, TRANSITION
    }

    public enum RotatorStates {
        UP, DOWN
    }

    public enum IntakeGateStates {
        UP, DOWN
    }
}

