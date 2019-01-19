package com.team9889.ftc2019.auto.actions.Lift;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Drive;
import com.team9889.ftc2019.subsystems.Lift;
import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by MannoMation on 10/26/2018.
 */
public class LiftLand extends Action {

    private Lift mLift = Robot.getInstance().getLift();
    private Drive mDrive = Robot.getInstance().getDrive();
    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        timer.reset();
    }

    @Override
    public void update() {
        if(mLift.getHeight() < 16)
            mLift.setLiftPower(0.6);
        else
            mLift.setLiftPower(0);

            mDrive.setThrottleSteerPower(-0.2, 0);
    }

    @Override
    public boolean isFinished() {
        return mLift.getHeight() > 15.8;
    }

    @Override
    public void done() {
        mLift.setLiftPower(0);
        mDrive.setThrottleSteerPower(0,0);
    }
}
