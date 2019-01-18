package com.team9889.ftc2019.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.team9889.ftc2019.Constants;
import com.team9889.ftc2019.states.ExtenderStates;
import com.team9889.ftc2019.states.RotatorStates;
import com.team9889.lib.CruiseLib;
import com.team9889.lib.control.controllers.PID;
import com.team9889.lib.hardware.RevColorDistance;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Arrays;

/**
 * Created by licorice17 on 9/14/2018.
 */

public class Intake extends Subsystem {
    public boolean isAutoIntakeDone = true;
    public boolean operaterControl = false;
    public String backMinerals;
    public String frontMinerals;
    public String minerals;

    private DcMotor intakeMotor, extender;
    private Servo intakeRotator, hopperGate, hopperCover;
    private DigitalChannel scoringSwitch, inSwitch;
    public RevColorDistance revBackHopper, revFrontHopper;

    // PID for extending the intake
    private PID extenderPID = new PID(0.004, 0.0, 0.2);
    private double position = 0;

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
    }

    @Override
    public void zeroSensors() {
        extender.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Thread.yield();
        extender.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("Back Detected", backHopperDetected());
        telemetry.addData("Front Detected", frontHopperDetected());
        telemetry.addData("Hsv Back", Arrays.toString(revBackHopper.hsv()));
        telemetry.addData("Hsv Front", Arrays.toString(revFrontHopper.hsv()));
        telemetry.addData("IntakePower", intakeMotor.getPower());
        telemetry.addData("Intake Extender", extender.getCurrentPosition());
        telemetry.addData("Angle of Intake", intakeRotator.getPosition());
        telemetry.addData("Fully In Intake Switch", zeroingLimitSwitch());
        telemetry.addData("Grabbing Intake Switch", grabbingLimitSwitch());
        telemetry.addData("Intake Cruise Control", Robot.getInstance().intakeCruiseControl);
    }

    @Override
    public void update(ElapsedTime time) {
        if(!operaterControl) {
            position = Range.clip(position, 0, 30);


            double currentPosition = extender.getCurrentPosition();
            double power = extenderPID.update(currentPosition,
                    position * Constants.IntakeConstants.kIntakeTicksToInchRatio);

            setIntakeExtenderPower(power);
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
        this.position = position;
    }


    /**
     * @param position Set the position of the Intae
     */
    public void setIntakeRotatorPosition(double position) {
        intakeRotator.setPosition(position);
    }

    public void setHopperGateDown() {
        hopperGate.setPosition(1);
    }

    public void setHopperGateUp() {
        hopperGate.setPosition(0.5);
    }

    public void HopperCoverOpen() {
        hopperCover.setPosition(0.6);
    }

    public void HopperCoverClosed() {
        hopperCover.setPosition(0.3);

    }

    public boolean zeroingLimitSwitch() {
        return !inSwitch.getState();
    }

    public boolean grabbingLimitSwitch() {
        return !scoringSwitch.getState();
    }

    public boolean frontHopperDetected() {
        return revFrontHopper.getIN() < 2.2;
    }

    public boolean backHopperDetected() {
        return revBackHopper.getIN() < 2.2;
    }

    public boolean twoMineralsDetected() {
        return frontHopperDetected() && backHopperDetected();
    }

    public Robot.MineralPositions getMineralPositions() {
        backColor();
        frontColor();

        combineMinerals();
        if (minerals == "GoldGold") {
            return com.team9889.ftc2019.subsystems.Robot.MineralPositions.GOLDGOLD;
        } else if (minerals == "SilverSilver") {
            return com.team9889.ftc2019.subsystems.Robot.MineralPositions.SILVERSILVER;
        } else if (minerals == "SilverGold") {
            return com.team9889.ftc2019.subsystems.Robot.MineralPositions.SILVERGOLD;
        } else if (minerals == "GoldSilver") {
            return com.team9889.ftc2019.subsystems.Robot.MineralPositions.GOLDSILVER;
        } else {
            return Robot.MineralPositions.SILVERSILVER;
        }
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
        else if ((CruiseLib.isBetween(revBackHopper.hsv()[0], hueThresholdSilver[0], hueThresholdSilver[1])
                && CruiseLib.isBetween(revBackHopper.hsv()[1], saturationThresholdSilver[0], saturationThresholdSilver[1]))
                || backHopperDetected())
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
                && CruiseLib.isBetween(revFrontHopper.hsv()[1], saturationThresholdSilver[0], saturationThresholdSilver[1])
                || frontHopperDetected())
            frontMinerals = "Silver";
    }

    public void combineMinerals() {
        minerals = frontMinerals + backMinerals;
    }

    public void autoIntake() {
        if (twoMineralsDetected() || operaterControl) {
            setWantedIntakeState(ExtenderStates.GRABBING);
            if (isCurrentStateWantedState()) {
                setIntakePower(0);
                setHopperCoverState(HopperState.OPEN);
                isAutoIntakeDone = true;
                operaterControl = false;
            } else {
                setHopperGateDown();
                setIntakePower(-1);
            }
        } else {
            setIntakeRotatorState(RotatorStates.DOWN);
            setHopperCoverState(HopperState.CLOSED);
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

