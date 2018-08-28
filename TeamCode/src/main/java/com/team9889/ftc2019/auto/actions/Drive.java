package com.team9889.ftc2019.auto.actions;

import com.team9889.lib.control.math.cartesian.Rotation2d;

/**
 * Created by joshua9889 on 8/28/2018.
 */
public class Drive extends Action {

    private double distance;
    private Rotation2d angle;

    public Drive(){}

    @Override
    public void setup(String args) {
        double[] array = getNumbersFromString(args);

        distance = array[0];
        angle = new Rotation2d(array[1], Rotation2d.Unit.DEGREES);
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void update() {

    }

    @Override
    public void done() {

    }

    @Override
    public void start() {

    }
}
