package com.team9889.ftc2019.auto.actions.Dumper;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by MannoMation on 2/27/2019.
 */
public class DumperCollecting extends Action {

    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        Robot.getInstance().setScorerStates(Robot.scorerStates.COLLECTING);
        timer.reset();
    }

    @Override
    public void update() {
        Robot.getInstance().update(timer);
    }

    @Override
    public boolean isFinished() {
        return timer.milliseconds() > 2000;
    }

    @Override
    public void done() {

    }
}
