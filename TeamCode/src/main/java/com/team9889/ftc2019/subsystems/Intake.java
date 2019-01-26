package com.team9889.ftc2019.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.team9889.ftc2019.Constants;
import com.team9889.lib.CruiseLib;
import com.team9889.lib.control.controllers.PID;
import com.team9889.lib.hardware.RevColorDistance;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Arrays;

/**
 * Created by licorice17 on 9/14/2018.
 */

public class Intake extends Subsystem {

    // Hardware
    private DcMotor intakeMotor, extender;
    private Servo intakeRotator, hopperGate, hopperCover;
    private DigitalChannel scoringSwitch, inSwitch;
    public RevColorDistance revBackHopper, revFrontHopper;

    // PID for extending the intake
    private PID extenderPID = new PID(0.5, 0.0, 2);

    // Tracker
    private double maximumPosition = 24; // Inches
    private double offset = 0; // Ticks

    //
    private boolean intakeOperatorControl = true;
    private IntakeStates currentIntakeState = IntakeStates.NULL;
    private IntakeStates wantedIntakeState = IntakeStates.ZEROING;
    private boolean first = true;
    private double intakeRotatorPosition = 0;

    public boolean isIntakeOperatorControl() {
        return intakeOperatorControl;
    }

    @Override
    public void init(HardwareMap hardwareMap, boolean auto) {
        intakeMotor = hardwareMap.get(DcMotor.class, Constants.IntakeConstants.kIntakeMotorId);
        extender = hardwareMap.get(DcMotor.class, Constants.IntakeConstants.kIntakeExtenderId);

        extender.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        extender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        intakeRotator = hardwareMap.get(Servo.class, Constants.IntakeConstants.kIntakeRotatorId);
        hopperGate = hardwareMap.get(Servo.class, Constants.IntakeConstants.kHopperGateId);
        hopperCover = hardwareMap.get(Servo.class, Constants.IntakeConstants.kHopperCoverID);

        scoringSwitch = hardwareMap.get(DigitalChannel.class, Constants.IntakeConstants.kIntakeSwitchId);
        inSwitch = hardwareMap.get(DigitalChannel.class, Constants.IntakeConstants.kIntakeInSwitchId);

        revBackHopper = new RevColorDistance(Constants.IntakeConstants.kBackIntakeDetectorId, hardwareMap);
        revFrontHopper = new RevColorDistance(Constants.IntakeConstants.kFrontIntakeDetectorId, hardwareMap);

        offset = 0;
        currentIntakeState = IntakeStates.NULL;
        setWantedIntakeState(IntakeStates.ZEROING);
    }

    @Override
    public void zeroSensors() {
    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("PID Output", extenderPID.getOutput());
        telemetry.addData("Back Detected", backHopperDetector());
        telemetry.addData("Front Detected", frontHopperDetector());
        telemetry.addData("Hsv Back", Arrays.toString(revBackHopper.hsv()));
        telemetry.addData("Hsv Front", Arrays.toString(revFrontHopper.hsv()));

        telemetry.addData("IntakePower", intakeMotor.getPower());
        telemetry.addData("Intake Extender Real Position", getIntakeExtenderPosition());
        telemetry.addData("Intake Extender Position", extender.getCurrentPosition());
        telemetry.addData("Offset", offset);
        telemetry.addData("Offset is true", !CruiseLib.isBetween(offset, -0.1, 0.1));

        telemetry.addData("Angle of Intake", intakeRotator.getPosition());
        telemetry.addData("Fully In Intake Switch", intakeInSwitchValue());
        telemetry.addData("Grabbing Intake Switch", intakeGrabbingSwitchValue());

        telemetry.addData("Wanted State", wantedIntakeState);
        telemetry.addData("Current State", currentIntakeState);

        telemetry.addData("Intake Cruise Control", isIntakeOperatorControl());
    }

    @Override
    public void update(ElapsedTime time) {

        // This is used to try to prevent the intake cover from not being broken, but it's not that good.
        if ((getIntakeExtenderPosition() < 4.1 && !CruiseLib.isBetween(offset, -0.1, 0.1))) {
            setHopperCoverState(HopperCoverState.CLOSED);
        }

        switch (wantedIntakeState) {
            case INTAKING:
                if (twoMineralsDetected()) {
                    intakeOperatorControl = false;
                    setIntakePower(-0.8);

                    currentIntakeState = IntakeStates.INTAKING;
                    setWantedIntakeState(IntakeStates.GRABBING);
                    setHopperGateState(HopperGateState.DOWN);
                } else {
                    intakeOperatorControl = true;

                    intake();
                    setHopperGateState(HopperGateState.UP);
                    setHopperCoverState(HopperCoverState.CLOSED);
                    setIntakeRotatorState(RotatorStates.DOWN);
                }
                break;
            case GRABBING:
                if (currentIntakeState != wantedIntakeState) {
                    setIntakeRotatorState(RotatorStates.UP);

                    if (currentIntakeState == IntakeStates.ZEROING) {
                        if (intakeGrabbingSwitchValue()) {
                            currentIntakeState = IntakeStates.GRABBING;
                            intakeOperatorControl = true;

                            setIntakeExtenderPower(0);
                        } else {
                            intakeOperatorControl = false;
                            setIntakePower(0);
                            setHopperGateState(HopperGateState.UP);
                            setHopperCoverState(HopperCoverState.CLOSED);
                            setIntakeRotatorState(RotatorStates.UP);
                            setIntakeExtenderPower(.3);
                        }

                    } else {
                        if (intakeGrabbingSwitchValue()) {
                            setIntakeExtenderPower(0);
                            setIntakePower(0);
                            setHopperCoverState(HopperCoverState.OPEN);
                            currentIntakeState = IntakeStates.GRABBING;
                            intakeOperatorControl = true;
                            first = true;
                        } else {
                            intakeOperatorControl = false;

                            if (currentIntakeState == IntakeStates.INTAKING)
                                outtake();
                            else
                                setIntakePower(0);

                            if (first) {
                                if (getIntakeExtenderPosition() > 9 && !CruiseLib.isBetween(offset, -0.1, 0.1))
                                    setIntakeExtenderPower(-1);
                                else if (getIntakeExtenderPosition() > 4.75 && !CruiseLib.isBetween(offset, -0.1, 0.1))
                                    setIntakeExtenderPower(-0.3);
                                else
                                    first = false;
                            } else {
                                setIntakeExtenderPower(0.2);

                                if (getIntakeExtenderPosition() > 7)
                                    first = true;
                            }


                            setHopperGateState(HopperGateState.DOWN);
                            setHopperCoverState(HopperCoverState.CLOSED);
                        }

                    }
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
                        setHopperGateState(HopperGateState.UP);
                        setIntakeRotatorState(RotatorStates.UP);
                        setHopperCoverState(HopperCoverState.CLOSED);
                        setIntakePower(0);

                        setIntakeExtenderPower(-0.5);
                    }
                }
                break;
            case AUTONOMOUS:
                if (currentIntakeState != wantedIntakeState) {
                    if (intakeGrabbingSwitchValue()) {
                        setIntakeExtenderPower(0);
                        setIntakePower(0);
                        setIntakeRotatorPosition(0.9);

                        currentIntakeState = IntakeStates.AUTONOMOUS;
                        first = true;
                    } else {
                        if (first) {
                            if (getIntakeExtenderPosition() > 9 && !CruiseLib.isBetween(offset, -0.1, 0.1))
                                setIntakeExtenderPower(-1);
                            else if (getIntakeExtenderPosition() > 4.75 && !CruiseLib.isBetween(offset, -0.1, 0.1)) {
                                setIntakeExtenderPower(-0.3);
                                setIntakeRotatorPosition(0.8);
                            } else
                                first = false;
                        } else {
                            setIntakeExtenderPower(0.5);

                            if (getIntakeExtenderPosition() > 7)
                                first = true;
                        }


                        setHopperGateState(HopperGateState.UP);
                        setHopperCoverState(HopperCoverState.CLOSED);
                        setIntakeRotatorState(RotatorStates.UP);
                    }
                }
                break;
            case NULL:
                currentIntakeState = IntakeStates.NULL;
                intakeOperatorControl = true;
                break;
            case EXTENDED:
                setIntakeExtenderPosition(24);
                setHopperGateState(HopperGateState.UP);
                setHopperCoverState(HopperCoverState.CLOSED);
                setIntakeRotatorState(RotatorStates.UP);

                if(Math.abs(extenderPID.getError()) < 0.5) {
                    setIntakeExtenderPower(0);
                    currentIntakeState = IntakeStates.EXTENDED;
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

    private double getIntakeExtenderPosition() {
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

    public void setIntakeRotatorState(RotatorStates state) {
        switch (state) {
            case UP:
                setIntakeRotatorPosition(0.55);
                break;

            case DOWN:
                setIntakeRotatorPosition(1);
                break;
        }
    }

    private void setHopperGateState(HopperGateState state) {
        switch (state) {
            case UP:
                hopperGate.setPosition(0.5);
                break;

            case DOWN:
                hopperGate.setPosition(1);
                break;
        }
    }

    /**
     * @return If the Lift is pressing the Lower Limit Switch
     */
    private boolean intakeInSwitchValue() {
        boolean intakeInSwitch = !inSwitch.getState();
        if (intakeInSwitch)
            offset = getIntakeExtenderPositionTicks();

        return intakeInSwitch;
    }

    /**
     * @return If the Lift is pressing the Upper Limit Switch
     */
    private boolean intakeGrabbingSwitchValue() {
        boolean intakeGrabbingSwitch = !scoringSwitch.getState();
        if (intakeGrabbingSwitch)
            offset = getIntakeExtenderPositionTicks() - (594);

        return intakeGrabbingSwitch;
    }

    /**
     * @param state Set the state of the Hopper Cover
     */
    private void setHopperCoverState(HopperCoverState state) {
        switch (state) {
            case OPEN:
                hopperCover.setPosition(0.6);
                break;

            case CLOSED:
                hopperCover.setPosition(0.2);
                break;
        }
    }

    /**
     * @return If the front mineral detector sees a mineral
     */
    private boolean frontHopperDetector() {
        return revFrontHopper.getIN() < 2.2;
    }

    /**
     * @return If the back mineral detector sees a mineral
     */
    private boolean backHopperDetector() {
        return revBackHopper.getIN() < 2.2;
    }

    /**
     * @return If the both mineral detectors see a mineral
     */
    private boolean twoMineralsDetected() {
        return frontHopperDetector() && backHopperDetector();
    }

    /**
     * @return If the type of mineral the back mineral detector sees
     */
    private MineralType backColor() {
        double[] hueThresholdGold = {30, 39};
        double[] saturationThresholdGold = {0.4, 0.7};
        double[] valueThresholdGold = {0, 100};

        double[] hueThresholdSilver = {65, 74};
        double[] saturationThresholdSilver = {0.3, 0.4};
        double[] valueThresholdSilver = {30, 39};

        float[] revhopper = revBackHopper.hsv();

        if (backHopperDetector()) {
            if (CruiseLib.isBetween(revhopper[0], hueThresholdGold[0], hueThresholdGold[1])
                    && CruiseLib.isBetween(revhopper[1], saturationThresholdGold[0], saturationThresholdGold[1]))
                return MineralType.GOLD;
            else
                return MineralType.SILVER;
        } else
            return MineralType.UNKNOWN;
    }

    /**
     * @return If the type of mineral the front mineral detector sees
     */
    private MineralType frontColor() {
        double[] hueThresholdGold = {43, 70};
        double[] saturationThresholdGold = {0.4, 0.6};
        double[] valueThresholdGold = {0, 100};

        double[] hueThresholdSilver = {90, 105};
        double[] saturationThresholdSilver = {0.2, 0.35};
        double[] valueThresholdSilver = {30, 39};

        float[] revhopper = revFrontHopper.hsv();

        if (frontHopperDetector()) {
            if (CruiseLib.isBetween(revhopper[0], hueThresholdGold[0], hueThresholdGold[1])
                    && CruiseLib.isBetween(revhopper[1], saturationThresholdGold[0], saturationThresholdGold[1]))
                return MineralType.GOLD;
            else
                return MineralType.SILVER;
        } else
            return MineralType.UNKNOWN;
    }

    /**
     * @return What order the minerals are in, front to back
     */
    public Robot.MineralPositions updateMineralVote() {
        MineralType front = frontColor();
        MineralType back = backColor();

        Robot.MineralPositions mineralPositions;
        if (front == MineralType.SILVER && back == MineralType.GOLD)
            mineralPositions = Robot.MineralPositions.SILVERGOLD;
        else if (front == MineralType.SILVER && back == MineralType.SILVER)
            mineralPositions = (Robot.MineralPositions.SILVERSILVER);
        else if (front == MineralType.GOLD && back == MineralType.GOLD)
            mineralPositions = (Robot.MineralPositions.GOLDGOLD);
        else if (front == MineralType.GOLD && back == MineralType.SILVER)
            mineralPositions = (Robot.MineralPositions.GOLDSILVER);
        else
            mineralPositions = Robot.MineralPositions.SILVERSILVER;

        return mineralPositions;
    }

    @Override
    public String toString() {
        return "Intake";
    }

    public enum IntakeStates {
        INTAKING, GRABBING, ZEROING, AUTONOMOUS, NULL, EXTENDED, DRIVER
    }

    private enum RotatorStates {
        UP, DOWN
    }

    private enum HopperGateState {
        UP, DOWN
    }

    private enum HopperCoverState {
        OPEN, CLOSED
    }

    public enum MineralType {
        GOLD, SILVER, UNKNOWN
    }
}

