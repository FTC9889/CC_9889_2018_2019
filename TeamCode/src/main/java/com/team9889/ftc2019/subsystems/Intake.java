package com.team9889.ftc2019.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.team9889.ftc2019.Constants;
import com.team9889.lib.CruiseLib;
import com.team9889.lib.control.controllers.PID;
import com.team9889.lib.hardware.ModernRoboticsUltrasonic;
import com.team9889.lib.hardware.RevColorDistance;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Arrays;

/**
 * Created by licorice17 on 9/14/2018.
 */

public class Intake extends Subsystem {
    private DcMotor intakeMotor, extender;
    private Servo intakeRotator, hopperGate, hopperCover;
    private DigitalChannel scoringSwitch, inSwitch;
    private ModernRoboticsUltrasonic craterDetector;
    public RevColorDistance revBackHopper, revFrontHopper;
    public boolean isAutoIntakeDone = true;

    public String backMinerals;
    public String frontMinerals;
    public String minerals;

    // PID for extending the intake
    private PID extenderPID = new PID(0.004, 0.0, 0.2);

    // Time that the counter should ignore for extraneous presses
    private double millisecondWatcher = 100;
    private boolean firstPress = true;
    private ElapsedTime mineralTimer = new ElapsedTime();
    public int numberOfMinerals = 0;

    public enum States {
        INTAKING, EXTENDING, GRABBING, ZEROING, NULL
    }

    public enum RotatorStates {
        UP, DOWN, NULL
    }

    private States currentExtenderState = States.ZEROING;
    private States wantedExtenderState = States.INTAKING;

    @Override
    public void init(HardwareMap hardwareMap, boolean auto) {
        intakeMotor = hardwareMap.get(DcMotor.class, Constants.IntakeConstants.kIntakeMotorId);
        extender = hardwareMap.get(DcMotor.class, Constants.IntakeConstants.kIntakeExtenderId);

        extender.setDirection(DcMotorSimple.Direction.REVERSE);
        extender.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        extender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intakeRotator = hardwareMap.get(Servo.class, Constants.IntakeConstants.kIntakeRotatorId);
        hopperGate = hardwareMap.get(Servo.class, Constants.IntakeConstants.kHopperGateId);
        hopperCover = hardwareMap.get(Servo.class, Constants.IntakeConstants.kHopperCoverID);

        scoringSwitch = hardwareMap.get(DigitalChannel.class, Constants.IntakeConstants.kIntakeSwitchId);
        inSwitch = hardwareMap.get(DigitalChannel.class, Constants.IntakeConstants.kIntakeInSwitchId);

        craterDetector = new ModernRoboticsUltrasonic(Constants.IntakeConstants.kCraterDetectorId, hardwareMap);

        revBackHopper = new RevColorDistance(Constants.IntakeConstants.kBackIntakeDetectorId, hardwareMap);
        revFrontHopper = new RevColorDistance(Constants.IntakeConstants.kFrontIntakeDetectorId, hardwareMap);

        if (auto) {
            setIntakeRotatorState(RotatorStates.UP);
            setWantedIntakeState(States.ZEROING);
            hopperCoverClosed();

            setHopperGateUp();
        }

        setWantedIntakeState(States.NULL);
        setIntakeRotatorState(RotatorStates.NULL);
    }

    @Override
    public void zeroSensors() {
        extender.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Thread.yield();
        extender.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("Back Detected", backHopperDetector());
        telemetry.addData("Front Detected", frontHopperDetector());
        telemetry.addData("Hsv Back", Arrays.toString(revBackHopper.hsv()));
        telemetry.addData("Hsv Front", Arrays.toString(revFrontHopper.hsv()));
        telemetry.addData("IntakePower", intakeMotor.getPower());
        telemetry.addData("Intake Extender", extender.getCurrentPosition());
        telemetry.addData("Angle of Intake", intakeRotator.getPosition());
        telemetry.addData("Fully In Intake Switch", intakeInSwitchValue());
        telemetry.addData("Grabbing Intake Switch", intakeGrabbingSwitchValue());
        telemetry.addData("Wanted State", wantedExtenderState);
        telemetry.addData("Current State", currentExtenderState);
        telemetry.addData("Intake Cruise Control", Robot.getInstance().intakeCruiseControl);
    }

    @Override
    public void update(ElapsedTime time) {
        if (!isAutoIntakeDone) {
            autoIntake();
        }

        if (currentExtenderState != wantedExtenderState) switch (wantedExtenderState) {
            case ZEROING:
                if (intakeInSwitchValue()) {
                    setIntakeExtenderPower(0);
                    zeroSensors();
                    setHopperGateUp();
                    Robot.getInstance().intakeCruiseControl = true;
                    currentExtenderState = States.ZEROING;
                } else {
                    Robot.getInstance().intakeCruiseControl = false;
                    setIntakeExtenderPower(-1);
                    setIntakeRotatorState(RotatorStates.UP);
                }
                break;
            case GRABBING:
                if (currentExtenderState == States.ZEROING) {
                    if (intakeGrabbingSwitchValue()) {
                        hopperCoverOpen();
                        setIntakeExtenderPower(0);
                        currentExtenderState = States.GRABBING;
                        Robot.getInstance().intakeCruiseControl = true;

                        setIntakeRotatorPosition(0.8);
                    } else {
                        Robot.getInstance().intakeCruiseControl = false;
                        setIntakeExtenderPower(.5);
                    }
                } else {
                    if (intakeGrabbingSwitchValue()) {
                        hopperCoverOpen();
                        setIntakeExtenderPower(0);
                        setHopperGateUp();
                        currentExtenderState = States.GRABBING;
                        Robot.getInstance().intakeCruiseControl = true;
                    } else {
                        Robot.getInstance().intakeCruiseControl = false;
                        setIntakeExtenderPower(-0.7);
                        setIntakeRotatorState(RotatorStates.UP);
                        setHopperGateDown();
                    }
                }
                break;
            case INTAKING:
                setHopperGateUp();
                setIntakeRotatorState(RotatorStates.DOWN);
                currentExtenderState = States.INTAKING;
                break;
            case EXTENDING:
                break;
            case NULL:
                currentExtenderState = States.NULL;
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
     * Intake
     */
    public void intake() {
        setIntakePower(1);
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
        extender.setPower(power);
    }


    /**
     * @param position Position that the intake should go to. In Inches
     */
    public void setIntakeExtenderPosition(double position) {
        position = Range.clip(position, 0, 30);

        double currentPosition = extender.getCurrentPosition();
        double power = extenderPID.update(currentPosition,
                position * Constants.IntakeConstants.kIntakeTicksToInchRatio);

        setIntakeExtenderPower(power);
    }

    /**
     * @param wantedState Set the wanted state of the Intake
     */
    public void setWantedIntakeState(States wantedState) {
        this.wantedExtenderState = wantedState;
    }

    /**
     * @return If the current state of the intake is equal to the wanted state
     */
    public boolean isCurrentStateWantedState() {
        return (currentExtenderState == wantedExtenderState);
    }

    /**
     * @param position Set the position of the Intae
     */
    private void setIntakeRotatorPosition(double position) {
        intakeRotator.setPosition(position);
    }

    public void setIntakeRotatorState(RotatorStates state) {
        switch (state) {
            case UP:
                setIntakeRotatorPosition(0.4);
                break;

            case DOWN:
                setIntakeRotatorPosition(1);
                break;
        }
    }

    public void setHopperGateDown() {
        hopperGate.setPosition(1);
    }

    public void setHopperGateUp() {
        hopperGate.setPosition(0.5);
    }

    public double getCraterDistance(DistanceUnit distanceUnit) {
        return craterDetector.getDistance(distanceUnit);
    }

    private boolean intakeInSwitchValue() {
        return !inSwitch.getState();
    }

    private boolean intakeGrabbingSwitchValue() {
        return !scoringSwitch.getState();
    }

    public void hopperCoverClosed(){
        hopperCover.setPosition(1);
    }

    public void hopperCoverOpen(){
        hopperCover.setPosition(0);
    }

    public boolean frontHopperDetector() {
        return revFrontHopper.getIN() < 2.2;
    }

    public boolean backHopperDetector() {
        return revBackHopper.getIN() < 2.2;
    }

    public boolean twoMineralsDetected() {
        return frontHopperDetector() && backHopperDetector();
    }

    public void backColor() {
        double[] hueThresholdGold = {30, 39};
        double[] saturationThresholdGold = {0.5, 0.7};
        double[] valueThresholdGold = {0, 100};

        double[] hueThresholdSilver = {65, 74};
        double[] saturationThresholdSilver = {0.3, 0.4};
        double[] valueThresholdSilver = {30, 39};

        if (CruiseLib.isBetween(revBackHopper.hsv()[0], hueThresholdGold[0], hueThresholdGold[1])
                && CruiseLib.isBetween(revBackHopper.hsv()[1], saturationThresholdGold[0], saturationThresholdGold[1]))
            backMinerals = "Gold";
        else if (CruiseLib.isBetween(revBackHopper.hsv()[0], hueThresholdSilver[0], hueThresholdSilver[1])
                && CruiseLib.isBetween(revBackHopper.hsv()[1], saturationThresholdSilver[0], saturationThresholdSilver[1]) || twoMineralsDetected())
            backMinerals = "Silver";
    }

    public void frontColor() {
        double[] hueThresholdGold = {40, 50};
        double[] saturationThresholdGold = {0.4, 0.6};
        double[] valueThresholdGold = {0, 100};

        double[] hueThresholdSilver = {90, 105};
        double[] saturationThresholdSilver = {0.2, 0.35};
        double[] valueThresholdSilver = {30, 39};

        if (CruiseLib.isBetween(revFrontHopper.hsv()[0], hueThresholdGold[0], hueThresholdGold[1])
                && CruiseLib.isBetween(revFrontHopper.hsv()[1], saturationThresholdGold[0], saturationThresholdGold[1]))
            frontMinerals = "Gold";
        else if (CruiseLib.isBetween(revFrontHopper.hsv()[0], hueThresholdSilver[0], hueThresholdSilver[1])
                && CruiseLib.isBetween(revFrontHopper.hsv()[1], saturationThresholdSilver[0], saturationThresholdSilver[1]) || twoMineralsDetected())
            frontMinerals = "Silver";
    }

    public void combineMinerals(){
        minerals =  frontMinerals + backMinerals;
    }

    public void autoIntake() {
        if (twoMineralsDetected()) {
            setWantedIntakeState(States.GRABBING);
            if (isCurrentStateWantedState()) {
                setIntakePower(0);
                hopperCoverOpen();
                setHopperGateDown();
                isAutoIntakeDone = true;
            } else {
                setIntakePower(-1);
            }
        } else {
            hopperCoverClosed();
            isAutoIntakeDone = false;
            setHopperGateUp();
            setIntakePower(1);
        }
    }

    @Override
    public String toString() {
        return "Intake";
    }
}

