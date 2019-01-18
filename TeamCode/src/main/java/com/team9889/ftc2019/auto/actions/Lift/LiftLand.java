package com.team9889.ftc2019.auto.actions.Lift;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.states.LiftStates;
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
        Robot.getInstance().getLift().setLiftState(LiftStates.SCOREINGHEIGHT);
        timer.reset();
    }

    @Override
    public void update() {
        if (Robot.getInstance().getLift().getHeight() > 10 && Robot.getInstance().getLift().getHeight() < 15){
            Robot.getInstance().getDrive().setThrottleSteerPower(0.1, 0);
        } else {
            Robot.getInstance().getDrive().setThrottleSteerPower(0, 0);
        }
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
