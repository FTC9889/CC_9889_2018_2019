package com.team9889.ftc2019.auto.actions.Intake;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Intake;
import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by MannoMation on 1/12/2019.
 */
public class IntakeZeroing extends Action {
    private ElapsedTime intakeTimer = new ElapsedTime();

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        intakeTimer.reset();
        Robot.getInstance().getIntake().setWantedIntakeState(Intake.IntakeStates.ZEROING);
    }

    @Override
    public void update() {
        Robot.getInstance().getIntake().update(intakeTimer);
    }

    @Override
    public boolean isFinished() {
        return Robot.getInstance().getIntake().isCurrentStateWantedState() || intakeTimer.milliseconds() > 1750;
    }

    @Override
    public void done() {

    }
}
