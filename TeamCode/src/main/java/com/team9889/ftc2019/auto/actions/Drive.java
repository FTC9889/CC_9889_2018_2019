package com.team9889.ftc2019.auto.actions;

import com.team9889.lib.control.math.cartesian.Rotation2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

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
        angle = new Rotation2d(array[1], AngleUnit.DEGREES);
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
        System.out.println("Drive " + String.valueOf(distance) + " at " + String.valueOf(angle));
    }

    @Override
    public void start() {

    }
}
