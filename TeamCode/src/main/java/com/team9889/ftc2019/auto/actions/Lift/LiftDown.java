package com.team9889.ftc2019.auto.actions.Lift;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.states.LiftStates;
import com.team9889.ftc2019.subsystems.Robot;

public class LiftDown extends Action {

    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        Robot.getInstance().getHangingLift().setLiftState(LiftStates.DOWN);
    }

    @Override
    public void update() {
        Robot.getInstance().getHangingLift().update(timer);
    }

    @Override
    public boolean isFinished() {
        return Robot.getInstance().getHangingLift().isCurrentWantedState();
    }

    @Override
    public void done() {
        Robot.getInstance().getHangingLift().setLiftPower(0);
    }
}
