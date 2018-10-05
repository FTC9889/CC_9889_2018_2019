package com.team9889.lib.control.controllers;

import com.qualcomm.robotcore.util.RobotLog;

/**
 * Created by joshua9889 on 4/15/2018.
 */

public class PID extends FeedBackController {

    public PID(double kP, double kI, double kD){
        this.p = kP;
        this.i = kI;
        this.d = kD;
    }

    private double p, i, d;

    private double error_prior;
    private double integral;
    private double lastTime = 0;

    private boolean first = true;


    @Override
    public double update(double current, double wanted) {
        double error = wanted - current;

        double output;
        if(first){
            // P control first time
            output = p * error;
            first = false;
            RobotLog.a(String.valueOf(output));
        } else {
            double currentTime = System.currentTimeMillis() - lastTime;
            integral = integral + (error *currentTime);
            double derivative = (error - error_prior)/currentTime;
            output = (p * error) + (i * integral) + (d * derivative);
        }

        lastTime = System.currentTimeMillis();
        error_prior = error;
        return output;
    }
}
