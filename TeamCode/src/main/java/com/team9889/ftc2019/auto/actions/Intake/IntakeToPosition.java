package com.team9889.ftc2019.auto.actions.Intake;

import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by MannoMation on 11/1/2018.
 */

public class IntakeToPosition extends Action {

    private double motor;

    public IntakeToPosition(double motor){
        this.motor = motor;
    }

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        Robot.getInstance().getIntake().setIntakeExtenderPosition(motor);
    }

    @Override
    public void update() {

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void done() {

    }
}
