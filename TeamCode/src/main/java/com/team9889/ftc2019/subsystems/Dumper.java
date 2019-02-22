package com.team9889.ftc2019.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.Constants;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by MannoMation on 2/15/2019.
 */
public class Dumper extends Subsystem{

    private Servo rightShoulder, leftShoulder;
    private Servo dumperRotator;

    private ElapsedTime timer = new ElapsedTime();
    private boolean timerReset = true;
    public ElapsedTime collectingTimer = new ElapsedTime();

    public enum dumperStates{
        COLLECTING, STORED, SCORING, DUMP, NULL
    }

    public dumperStates wantedDumperState = dumperStates.NULL;

    @Override
    public void init(HardwareMap hardwareMap, boolean auto) {
        rightShoulder = hardwareMap.get(Servo.class, Constants.DumperConstants.kRightDumperShoulderId);
        leftShoulder = hardwareMap.get(Servo.class, Constants.DumperConstants.kLeftDumperShoulderId);
        dumperRotator = hardwareMap.get(Servo.class, Constants.DumperConstants.kDumperRotatorId);

        rightShoulder.setDirection(Servo.Direction.REVERSE);

        if (auto){
            setDumperStates(dumperStates.STORED);
        }
    }

    @Override
    public void zeroSensors() {

    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("Arm Position", getArmPosition());
        telemetry.addData("Dumper Rotator Position", getDumperRotatorPosition());
        telemetry.addData("Wanted Dumper Position", wantedDumperState);
    }

    @Override
    public void update(ElapsedTime time) {

        switch (wantedDumperState) {
            case STORED:
                setArmPosition(.2);
                setDumperRotatorPosition(.1);
                timerReset = true;
                break;

            case SCORING:
                setArmPosition(.7);

                if (timerReset) {
                    timer.reset();
                    timerReset = false;
                } else if (timer.milliseconds() > 400) {
                    setDumperRotatorPosition(.65);
                }
                break;

            case COLLECTING:
                setArmPosition(0);
                setDumperRotatorPosition(.15);
                timerReset = true;
                break;

            case DUMP:
                setArmPosition(.7);
                setDumperRotatorPosition(.45);
                if (collectingTimer.milliseconds() > 1500)
                    Robot.getInstance().setScorerStates(Robot.scorerStates.COLLECTING);
                timerReset = true;
                break;

            case NULL:
                timerReset = true;
                break;
        }
    }

    @Override
    public void test(Telemetry telemetry) {

    }

    public void setArmPosition(double armPosition){
        leftShoulder.setPosition(armPosition - .01);
        rightShoulder.setPosition(armPosition);
    }

    public void setDumperRotatorPosition(double dumperPosition){
        dumperRotator.setPosition(dumperPosition);
    }

    public void setDumperStates(dumperStates states){
        this.wantedDumperState = states;
    }

    public double getArmPosition(){
        return (leftShoulder.getPosition() + rightShoulder.getPosition())/2;
    }

    public double getDumperRotatorPosition() {
        return dumperRotator.getPosition();
    }

    @Override
    public void stop() {

    }
}
