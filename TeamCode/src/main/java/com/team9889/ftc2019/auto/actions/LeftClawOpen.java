package com.team9889.ftc2019.auto.actions;

import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by MannoMation on 11/30/2018.
 */
public class LeftClawOpen extends Action{
    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        Robot.getInstance().getArms().setLeftClawOpen(true);
    }

    @Override
    public void update() {

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void done() {

    }
}
