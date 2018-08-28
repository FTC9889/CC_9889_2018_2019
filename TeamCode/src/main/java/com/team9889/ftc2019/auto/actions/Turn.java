package com.team9889.ftc2019.auto.actions;

public class Turn extends Action {
    @Override
    public void setup(String args) {
        double[] array = getNumbersFromString(args);

        System.out.println("Turn to " + String.valueOf(array[0]));
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
