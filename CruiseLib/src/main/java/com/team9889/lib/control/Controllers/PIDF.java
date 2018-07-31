package com.team9889.lib.control.Controllers;

public class PIDF extends PID {

    private double kFF;

    public PIDF(double kP, double kI, double kD, double kF) {
        super(kP, kI, kD);
        this.kFF = kF;
    }

    @Override
    public double update(double current, double wanted) {
        return super.update(current, wanted) + kFF;
    }
}
