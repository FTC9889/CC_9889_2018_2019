package com.team9889.ftc2019.auto.actions;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by MannoMation on 3/7/2019.
 */
public class RobotUpdate extends Action{

    private ElapsedTime timer = new ElapsedTime();
    private double timeOut;

    public RobotUpdate(double timeOut){
        this.timeOut = timeOut;
    }

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        timer.reset();
    }

    @Override
    public void update() {
        Robot.getInstance().update(timer);
    }

    @Override
    public boolean isFinished() {
        return timer.milliseconds() > timeOut;
    }

    @Override
    public void done() {

    }
}
