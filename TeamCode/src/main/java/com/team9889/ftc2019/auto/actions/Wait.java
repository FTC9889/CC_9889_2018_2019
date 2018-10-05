package com.team9889.ftc2019.auto.actions;

import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by joshua9889 on 8/28/2018.
 */

public class Wait extends Action {
    private ElapsedTime t;
    private int timeToWaitMilli;

    public Wait(int waitTime){
        timeToWaitMilli = waitTime;
    }

    @Override
    public void setup(String args) {
        double[] array = getNumbersFromString(args);

        timeToWaitMilli = (int)array[0];
    }

    @Override
    public void start() {
        t = new ElapsedTime();
    }

    @Override
    public void update() {}

    @Override
    public boolean isFinished() {
        return t.milliseconds()>timeToWaitMilli;
    }

    @Override
    public void done() {}
}
