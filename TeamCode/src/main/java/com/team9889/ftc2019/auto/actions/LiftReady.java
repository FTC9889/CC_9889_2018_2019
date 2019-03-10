package com.team9889.ftc2019.auto.actions;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.states.LiftStates;
import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by joshua9889 on 3/9/2019.
 */
public class LiftReady extends Action{

    ElapsedTime timer = new ElapsedTime();
    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        Robot.getInstance().getLift().setLiftState(LiftStates.UP);
        timer.reset();
    }

    @Override
    public void update() {
        Robot.getInstance().update(timer);
    }

    @Override
    public boolean isFinished() {
        return timer.milliseconds()> 2000;
    }

    @Override
    public void done() {
    }
}
