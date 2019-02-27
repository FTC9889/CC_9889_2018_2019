package com.team9889.ftc2019.auto.actions.Dumper;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by MannoMation on 2/27/2019.
 */
public class DumperInit extends Action{

    private ElapsedTime timer = new ElapsedTime();
    private double timeOut;

    public DumperInit(double timeOut){
        this.timeOut = timeOut;
    }

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        Robot.getInstance().setScorerStates(Robot.scorerStates.AUTONOMOUS);
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
        Robot.getInstance().getLift().setLiftPower(0);
    }
}
