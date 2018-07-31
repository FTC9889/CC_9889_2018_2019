package com.team9889.lib.control.MotionProfiling;

/**
 * Created by joshua9889 on 7/16/2018.
 *
 * See https://www.desmos.com/calculator/ggyz14jvur for a graph of the equation being implementing.
 */

public class SinusoidalMotionProfile implements MotionProfile {

    private double K1, K2, K3, M;
    private double timeToComplete;
    private double direction;

    public SinusoidalMotionProfile(){}

    public SinusoidalMotionProfile(double setPoint, double max_v, double max_a){
        calculate(setPoint, max_v, max_a);
    }

    @Override
    public void calculate(double setpoint, double max_v, double max_a) {
        if(setpoint<0)
            direction = -1.0;
        else
            direction = 1.0;

        setpoint = Math.abs(setpoint);

        timeToComplete = (2.0 * setpoint * Math.PI) / max_a;
        timeToComplete = Math.pow(timeToComplete, 0.5);

        K1 = K1(timeToComplete);
        K2 = K2(max_a, timeToComplete);
        K3 = K3(timeToComplete);
        M = max_a;
    }

    @Override
    public double[] getOutput(double t) {
        double Position, Speed, Acceleration;

        if(t<getTimeToComplete()){
            Position = direction * K2 * (t - (K3 * Math.sin(K1 * t)));
            Speed = direction * K2 * (1 - Math.cos(K1 * t));
            Acceleration = direction * M * Math.sin(K1 * t);
        } else {
            Position = 0.0;
            Speed = 0.0;
            Acceleration = 0.0;
        }

        return new double[]{Position, Speed, Acceleration};
    }

    public double getTimeToComplete(){
        return timeToComplete;
    }


    private double K1(double T){
        return (2.0 * Math.PI) / T;
    }

    private double K2(double M, double T){
        return M / K1(T);
    }

    private double K3(double T){
        return 1.0 / K1(T);
    }
}
