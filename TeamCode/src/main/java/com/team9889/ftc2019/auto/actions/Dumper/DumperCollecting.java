package com.team9889.ftc2019.auto.actions.Dumper;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.states.LiftStates;
import com.team9889.ftc2019.subsystems.Dumper;
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
        Robot.getInstance().getDumper().setDumperStates(Dumper.dumperStates.COLLECTING);
        timer.reset();
    }

    @Override
    public void update() {
        if (timer.milliseconds() > 1000)
            Robot.getInstance().getLift().setLiftState(LiftStates.DOWN);

        Robot.getInstance().getLift().update(timer);
        Robot.getInstance().getDumper().update(timer);
    }

    @Override
    public boolean isFinished() {
        return timer.milliseconds() > 3000 && Robot.getInstance().getLift().getCurrentState() == LiftStates.DOWN;
    }

    @Override
    public void done() {

    }
}
