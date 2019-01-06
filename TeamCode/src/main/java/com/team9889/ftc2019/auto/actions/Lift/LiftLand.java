package com.team9889.ftc2019.auto.actions.Lift;

import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Lift;
import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by MannoMation on 10/26/2018.
 */
public class LiftLand extends Action {
    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        Robot.getInstance().getLift().setLiftState(Lift.LiftStates.SCOREINGHEIGHT);
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
