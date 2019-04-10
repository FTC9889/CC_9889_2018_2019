package com.team9889.ftc2019.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.team9889.ftc2019.Constants;
import com.team9889.lib.CruiseLib;
import com.team9889.lib.control.controllers.PID;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by licorice17 on 9/14/2018.
 */

public class Intake extends Subsystem {

    // Hardware
    private DcMotorEx intakeMotor, extender;
    private Servo intakeRotator, intakeGate;
    private Servo markerDumper;
    private DigitalChannel inSwitch;

    // PID for extending the intake
    private PID extenderPID = new PID(0.5, 0.0, 2);

    // Tracker
    private double maximumPosition = 35; // Inches
    private double offset = 0; // Ticks

    private boolean intakeOperatorControl = true;
    public IntakeStates currentIntakeState = IntakeStates.NULL;
    private IntakeStates wantedIntakeState = IntakeStates.ZEROING;
    private double intakeRotatorPosition = 0;

    public boolean intakeZeroingHardstop;

    public double autoIntakeOut;
    private boolean auto;

    public ElapsedTime transitionTimer = new ElapsedTime();
    public ElapsedTime collectingTimer = new ElapsedTime();

    private RotatorStates currentIntakeRotatorState;

    public boolean isIntakeOperatorControl() {
        return intakeOperatorControl;
    }

    @Override
    public void init(HardwareMap hardwareMap, boolean auto) {
        intakeMotor = hardwareMap.get(DcMotorEx.class, Constants.IntakeConstants.kIntakeMotorId);
        extender = hardwareMap.get(DcMotorEx.class, Constants.IntakeConstants.kIntakeExtenderId);

        intakeMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        extender.setDirection(DcMotorSimple.Direction.REVERSE);
        extender.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        extender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intakeRotator = hardwareMap.get(Servo.class, Constants.IntakeConstants.kIntakeRotatorId);
        intakeGate = hardwareMap.get(Servo.class, Constants.IntakeConstants.kIntakeGate);
        markerDumper = hardwareMap.get(Servo.class, Constants.IntakeConstants.kMarkerDumper);

        inSwitch = hardwareMap.get(DigitalChannel.class, Constants.IntakeConstants.kIntakeInSwitchId);

        offset = 0;
        this.auto = auto;
        if (auto){
            zeroSensors();
            setIntakeRotatorState(RotatorStates.UP);
            setIntakeGateState(IntakeGateStates.HOLDINGMARKER);
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

        telemetry.addData("IntakePower", intakeMotor.getPower());
        telemetry.addData("Intake Extender Real Position", getIntakeExtenderPosition());
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
                intakeOperatorControl = true;
                setIntakeGateState(IntakeGateStates.DOWN);
                setIntakeRotatorState(Intake.RotatorStates.DOWN);
                intake();
                currentIntakeState = IntakeStates.INTAKING;
                break;
            case GRABBING:
                if (intakeInSwitchValue()){
                    setIntakeExtenderPower(0);
                    intakeOperatorControl = true;
                    transitionTimer.reset();
                    setIntakeRotatorState(RotatorStates.DUMPING);
                    currentIntakeState = IntakeStates.GRABBING;
                    wantedIntakeState = IntakeStates.TRANSITION;
                }else{
                    intakeOperatorControl = false;
                    setIntakeRotatorState(RotatorStates.UP);
                    if (auto)
                        setIntakePower(-.25);
                    else
                        setIntakePower(-.5);

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
                        setIntakeExtenderPower(-1);
                    }
                }
                break;

            case AUTONOMOUS:
                if (getIntakeExtenderPosition() >= autoIntakeOut){
                    setIntakeExtenderPower(0);
                    currentIntakeState = IntakeStates.AUTONOMOUS;
                }else {
                    setIntakeExtenderPower(1);
                }
                break;

            case NULL:
                currentIntakeState = IntakeStates.NULL;
                intakeOperatorControl = true;
                break;

            case TRANSITION:
                setIntakeGateState(IntakeGateStates.UP);

                if(transitionTimer.milliseconds() > 800 && transitionTimer.milliseconds() < 900) {
                    intakeOperatorControl = false;
                    setIntakeRotatorState(RotatorStates.UP);
                    setIntakeExtenderPower(1);
                    Robot.getInstance().transitionDone = true;
                } else if(transitionTimer.milliseconds() > 900 && transitionTimer.milliseconds() < 1200) {
                    setIntakeExtenderPower(0);
                    setIntakePower(0);
                    setIntakeRotatorState(RotatorStates.UP);
                    currentIntakeState = IntakeStates.TRANSITION;
                    intakeOperatorControl = true;
                }
                break;

            case DRIVER:
                break;
        }

        setIntakeGatePosition();
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
        setIntakePower(-1);
        if (auto)
            setIntakeRotatorState(RotatorStates.DOWN);
    }

    /**
     * Outtake
     */
    public void outtake() {
        setIntakePower(1);
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

    private double getIntakeExtenderPositionTicks() {
        return extender.getCurrentPosition();
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

    public void setIntakeRotatorState(RotatorStates state) {
        switch (state) {
            case UP:
                if (auto)
                    setIntakeRotatorPosition(0.7);
                else
                    setIntakeRotatorPosition(0.7);
                currentIntakeRotatorState = RotatorStates.UP;
                break;

            case DOWN:
                setIntakeRotatorPosition(1);
                currentIntakeRotatorState = RotatorStates.DOWN;
                break;

            case DUMPING:
                setIntakeRotatorPosition(0.5);
                setIntakeGateState(IntakeGateStates.UP);
                currentIntakeRotatorState = RotatorStates.DUMPING;
                break;
        }
    }

    private double intakeGatePosition = 0;

    private void setIntakeGatePosition(double position) {
        intakeGatePosition = position;
    }

    private void setIntakeGatePosition() {
        intakeGate.setPosition(intakeGatePosition);
    }

    public void setIntakeGateState(IntakeGateStates state){
        switch (state){
            case UP:
                setIntakeGatePosition(0.4);
                break;

            case DOWN:
                setIntakeGatePosition(0.1);
                break;

            case HOLDINGMARKER:
                setIntakeGatePosition(0.05);
                break;
        }
    }

    /**
     * @return If the ScoringLift is pressing the Lower Limit Switch
     */
    private boolean intakeInSwitchValue() {
        boolean intakeInSwitch = !inSwitch.getState();
        if (intakeInSwitch)
            zeroSensors();
        return intakeInSwitch;
    }

    @Override
    public String toString() {
        return "Intake";
    }

    public enum IntakeStates {
        INTAKING, GRABBING, ZEROING, AUTONOMOUS, NULL, EXTENDED, DRIVER, TRANSITION
    }

    public enum RotatorStates {
        UP, DOWN, DUMPING
    }

    public enum IntakeGateStates {
        UP, DOWN, HOLDINGMARKER
    }
}

