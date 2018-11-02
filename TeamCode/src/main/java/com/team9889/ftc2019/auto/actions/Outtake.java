package com.team9889.ftc2019.auto.actions;

import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by MannoMation on 11/1/2018.
 */
public class Outtake extends Action{
    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        Robot.getInstance().getIntake().outtake();
    }

    @Override
    public void update() {

    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void done() {

    }
}
