package com.team9889.lib.control;

import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by joshua9889 on 4/15/2018.
 */

public class PID {
    private double p, i, d;

    private double error;
    private double error_prior;
    private double integral;
    private ElapsedTime t = new ElapsedTime();

    private double output;

    private boolean first = true;

    public PID(double kP, double kI, double kD){
        this.p = kP;
        this.i = kI;
        this.d = kD;
    }

    public synchronized double update(double wanted, double current){
        error = wanted - current;

        if(first){
            // P control first time
            output = p * error;
            first = false;
        } else {
            integral = integral + (error*t.milliseconds());
            double derivative = (error - error_prior)/t.milliseconds();
            output = (p * error) + (i * integral) + (d * derivative);
        }

        t.reset();
        error_prior = error;
        return output;
    }
}
