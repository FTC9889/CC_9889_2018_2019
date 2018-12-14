package com.team9889.ftc2019.auto.actions;

import com.team9889.ftc2019.subsystems.Intake;
import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by MannoMation on 12/14/2018.
 */
public class SamplingIntake extends Action{
    public double sampled = 0;

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {

        Robot.getInstance().getIntake().setIntakeExtenderPosition(6);
        Robot.getInstance().getIntake().setIntakeRotatorState(Intake.RotatorStates.DOWN);
        Robot.getInstance().getIntake().intake();

    }

    @Override
    public void update() {

        if (sampled == 1){
            Robot.getInstance().getIntake().stop();
            Robot.getInstance().getIntake().setIntakeRotatorState(Intake.RotatorStates.UP);
            Robot.getInstance().getIntake().setIntakeExtenderPosition(0);
        }

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void done() {

    }
}
