package com.team9889.ftc2019.auto.actions.Intake;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by MannoMation on 11/1/2018.
 */
public class Outtake extends Action {
    private ElapsedTime time;
    private double power = -1;
    private double timeOut = 1;

    public Outtake(double setPower, double timeOut) {
        this.power = setPower;
        this.timeOut = timeOut;
    }
    public Outtake(){}

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        Robot.getInstance().getIntake().setIntakePower(power);
        time = new ElapsedTime();
    }

    @Override
    public void update() {

    }

    @Override
    public boolean isFinished() {
        return time.milliseconds()>timeOut;
    }

    @Override
    public void done() {
        Robot.getInstance().getIntake().setIntakePower(0);
    }
}
