package com.team9889.ftc2019.auto.actions.Arms;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Arms;
import com.team9889.ftc2019.subsystems.Robot;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by MannoMation on 1/14/2019.
 */
public class ArmsToPark extends Action {
    private ElapsedTime armTimer = new ElapsedTime();

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        double angle = Robot.getInstance().getDrive().getAngle().getTheda(AngleUnit.DEGREES);
        if (angle < 50&& angle > 0)
            Robot.getInstance().getArms().setArmsStates(Arms.ArmStates.PARK);
        armTimer.reset();
    }

    @Override
    public void update() {
        Robot.getInstance().getArms().update(armTimer);
    }

    @Override
    public boolean isFinished() {
        return armTimer.milliseconds() > 2000;
    }

    @Override
    public void done() {

    }
}
