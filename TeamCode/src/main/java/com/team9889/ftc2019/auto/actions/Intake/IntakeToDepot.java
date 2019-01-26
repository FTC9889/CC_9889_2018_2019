package com.team9889.ftc2019.auto.actions.Intake;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Intake;
import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by MannoMation on 1/25/2019.
 */
public class IntakeToDepot extends Action {
    private int step = 0;
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
        switch (step) {
            case 0:
                Robot.getInstance().getIntake().setWantedIntakeState(Intake.IntakeStates.EXTENDED);
                if(Robot.getInstance().getIntake().isCurrentStateWantedState()) {
                    step++;
                    timer.reset();
                } else
                    Robot.getInstance().getIntake().update(timer);
                break;
            case 1:
                Robot.getInstance().getIntake().outtake();
                if(timer.milliseconds() > 1000)
                    step++;
                break;
            case 2:
                Robot.getInstance().getIntake().setIntakePower(0);
                step++;
                break;
            case 3:
                Robot.getInstance().getIntake().setWantedIntakeState(Intake.IntakeStates.GRABBING);
                if(Robot.getInstance().getIntake().isCurrentStateWantedState()) {
                    step++;
                } else
                    Robot.getInstance().getIntake().update(timer);
                break;
        }
    }

    @Override
    public boolean isFinished() {
        return Robot.getInstance().getIntake().isCurrentStateWantedState() && step == 4;
    }

    @Override
    public void done() {

    }
}
