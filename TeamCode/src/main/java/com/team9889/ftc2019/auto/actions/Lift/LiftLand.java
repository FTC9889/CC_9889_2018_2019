package com.team9889.ftc2019.auto.actions.Lift;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Lift;
import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by MannoMation on 10/26/2018.
 */
public class LiftLand extends Action {

    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        Robot.getInstance().getLift().setLiftState(Lift.LiftStates.SCOREINGHEIGHT);
        timer.reset();
    }

    @Override
    public void update() {
        Robot.getInstance().getLift().update(timer);
    }

    @Override
    public boolean isFinished() {
        return Robot.getInstance().getLift().isCurrentWantedState();
    }

    @Override
    public void done() {

    }
}
