package com.team9889.ftc2019.auto.actions.Intake;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Intake;
import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by MannoMation on 2/27/2019.
 */
public class IntakeGrabbing extends Action {

    private ElapsedTime timer = new ElapsedTime();
    private boolean first = false;

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        Robot.getInstance().getIntake().setWantedIntakeState(Intake.IntakeStates.GRABBING);
        Robot.getInstance().getIntake().zeroingFirst = true;
        Robot.getInstance().getIntake().collectingTimer.reset();
    }

    @Override
    public void update() {
        Robot.getInstance().getIntake().update(timer);
        if (Robot.getInstance().getIntake().currentIntakeState == Intake.IntakeStates.GRABBING && first) {
            Robot.getInstance().getIntake().transitionTimer.reset();
            first = false;
        }
    }

    @Override
    public boolean isFinished() {
        return Robot.getInstance().getIntake().getCurrentIntakeState() == Intake.IntakeStates.TRANSITION;
    }

    @Override
    public void done() {

    }
}
