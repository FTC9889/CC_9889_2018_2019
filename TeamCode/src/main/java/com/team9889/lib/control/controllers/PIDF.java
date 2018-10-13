package com.team9889.lib.control.controllers;

public class PIDF extends PID {

    private double kFF;

    public PIDF(double kP, double kI, double kD, double kF) {
        super(kP, kI, kD);
        this.kFF = kF;
    }

    @Override
    public double update(double current, double wanted) {
        double error = wanted - current;
        double sign = 1;

        if(error != 0)
            sign = error / Math.abs(error);

        return super.update(current, wanted) + (sign*kFF);
    }
}
