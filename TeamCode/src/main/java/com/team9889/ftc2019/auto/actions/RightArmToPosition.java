package com.team9889.ftc2019.auto.actions;

import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by MannoMation on 11/29/2018.
 */
public class RightArmToPosition extends Action{

    private double shoulder, elbow;

    public RightArmToPosition(double shoulder, double elbow){
        this.shoulder = shoulder;
        this.elbow = elbow;
    }

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        Robot.getInstance().getArms().setRightArm(shoulder, elbow);
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
