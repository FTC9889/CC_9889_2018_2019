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

    private Servo dumperRotator;

    private ElapsedTime timer = new ElapsedTime();
    private boolean timerReset = true;
    public ElapsedTime collectingTimer = new ElapsedTime();
    public ElapsedTime dumperTimer = new ElapsedTime();

    public enum dumperStates{
        COLLECTING, SCORING, DUMP, DUMPAUTO, NULL
    }

    public dumperStates wantedDumperState = dumperStates.NULL;

    @Override
    public void init(HardwareMap hardwareMap, boolean auto) {
        dumperRotator = hardwareMap.get(Servo.class, Constants.DumperConstants.kDumperRotatorId);

        if (auto){
            setDumperStates(dumperStates.COLLECTING);
        }
    }

    @Override
    public void zeroSensors() {

    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("Dumper Rotator Position", getDumperRotatorPosition());
        telemetry.addData("Wanted Dumper Position", wantedDumperState);
    }

    @Override
    public void update(ElapsedTime time) {
        switch (wantedDumperState) {
            case SCORING:
                    setDumperRotatorPosition(.25);
                break;

            case DUMPAUTO:
                setDumperRotatorPosition(.15);

                if (timerReset) {
                    timer.reset();
                    timerReset = false;
                }
                break;

            case COLLECTING:
                setDumperRotatorPosition(.525);
                timerReset = true;
                break;

            case DUMP:
                setDumperRotatorPosition(0.05);
                if (collectingTimer.milliseconds() > 1500) {
                    Robot.getInstance().setScorerStates(Robot.scorerStates.COLLECTING);
                    dumperTimer.reset();
                }
                break;

            case NULL:
                timerReset = true;
                break;
        }
    }

    public void setDumperRotatorPosition(double dumperPosition){
        dumperRotator.setPosition(dumperPosition);
    }

    public void setDumperStates(dumperStates states){
        this.wantedDumperState = states;
    }

    private double getDumperRotatorPosition() {
        return dumperRotator.getPosition();
    }

    @Override
    public void stop() {}
}
