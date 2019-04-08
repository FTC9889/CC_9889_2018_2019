package com.team9889.ftc2019.auto.actions.Lift;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.states.LiftStates;
import com.team9889.ftc2019.subsystems.Dumper;
import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by MannoMation on 1/19/2019.
 */
public class Land2 extends Action {

    private ElapsedTime timer = new ElapsedTime();
    private boolean first = true;

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        Robot.getInstance().getHangingLift().setLiftState(LiftStates.HOOKHEIGHT);
    }

    @Override
    public void update() {
        Robot.getInstance().getHangingLift().update(timer);
        if (Robot.getInstance().getHangingLift().getCurrentState() == LiftStates.HOOKHEIGHT && first){
            Robot.getInstance().getHangingLift().setHookPower(1);
            timer.reset();
            first = false;

        }else if (!first){

        }else
            timer.reset();
    }

    @Override
    public boolean isFinished() {
        return timer.milliseconds() > 500;
    }

    @Override
    public void done() {
        Robot.getInstance().getHangingLift().setHookPower(0);
        Robot.getInstance().getHangingLift().setLiftPower(0);
    }
}
