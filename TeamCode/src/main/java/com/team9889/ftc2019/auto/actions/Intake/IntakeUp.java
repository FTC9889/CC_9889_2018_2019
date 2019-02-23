package com.team9889.ftc2019.auto.actions.Intake;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Intake;
import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by MannoMation on 2/23/2019.
 */

public class IntakeUp extends Action {

    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        timer.reset();
        Robot.getInstance().getIntake().setIntakeRotatorState(Intake.RotatorStates.UP);
    }

    @Override
    public void update() {

    }

    @Override
    public boolean isFinished() {
        return timer.milliseconds() > 200;
    }

    @Override
    public void done() {

    }
}
