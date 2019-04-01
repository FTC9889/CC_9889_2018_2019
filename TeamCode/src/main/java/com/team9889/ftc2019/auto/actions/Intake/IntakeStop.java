package com.team9889.ftc2019.auto.actions.Intake;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Intake;
import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by MannoMation on 11/2/2018.
 */
public class IntakeStop extends Action {

    private ElapsedTime hopperDumperTimer = new ElapsedTime();

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        Robot.getInstance().getIntake().setIntakeGateState(Intake.IntakeGateStates.DOWN);
        hopperDumperTimer.reset();
    }

    @Override
    public void update() {
        if (hopperDumperTimer.milliseconds() > 500)
            Robot.getInstance().getIntake().stop();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void done() {

    }
}
