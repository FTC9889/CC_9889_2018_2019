package com.team9889.ftc2019.auto.actions.Dumper;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Dumper;
import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by MannoMation on 2/23/2019.
 */
public class DumpMarker extends Action {

    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        Robot.getInstance().getDumper().setDumperStates(Dumper.dumperStates.DUMPTEAMMARKER);
        timer.reset();
    }

    @Override
    public void update() {
        Robot.getInstance().getDumper().update(timer);
    }

    @Override
    public boolean isFinished() {
        return timer.milliseconds() > 500;
    }

    @Override
    public void done() {

    }
}
