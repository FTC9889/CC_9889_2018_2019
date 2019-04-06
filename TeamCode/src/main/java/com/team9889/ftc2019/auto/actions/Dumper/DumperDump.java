package com.team9889.ftc2019.auto.actions.Dumper;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by MannoMation on 2/28/2019.
 */
public class DumperDump extends Action {

    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        Robot.getInstance().getDumper().collectingTimer.reset();
        Robot.getInstance().setScorerStates(Robot.scorerStates.DUMP);
        timer.reset();
    }

    @Override
    public void update() {
        Robot.getInstance().update(timer);
    }

    @Override
    public boolean isFinished() {
        return timer.milliseconds() > 1400;
    }

    @Override
    public void done() {

    }
}
