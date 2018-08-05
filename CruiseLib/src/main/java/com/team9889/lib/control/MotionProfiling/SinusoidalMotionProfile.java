package com.team9889.lib.control.MotionProfiling;

import com.team9889.lib.Logger;

/**
 * Created by joshua9889 on 7/16/2018.
 *
 * See https://www.desmos.com/calculator/ggyz14jvur for a graph of the equation being implementing.
 */

public class SinusoidalMotionProfile implements MotionProfile {

    private double K1, K2, K3, M;
    private double totalTime;
    private double direction;

    public SinusoidalMotionProfile(){}

    public SinusoidalMotionProfile(double setPoint, double max_v, double max_a){
        calculate(setPoint, max_v, max_a);
    }

    // Demo
    public static void main(String... args){
        Logger log = new Logger("sinProfile.csv");
        SinusoidalMotionProfile profile = new SinusoidalMotionProfile(7, 3, 1);
        int step = 100;
        for (int i = 0; i < step; i++) {
            double[] segment = profile.getOutput((double) i/(step/profile.totalTime));
            log.write(segment[0] + "," + segment[1] + "," + segment[2]);
        }
        log.close();
    }

    @Override
    public void calculate(double setpoint, double max_v, double max_a) {
        if(setpoint<0)
            direction = -1.0;
        else
            direction = 1.0;

        setpoint = Math.abs(setpoint);

        totalTime = (2.0 * setpoint * Math.PI) / max_a;
        totalTime = Math.pow(totalTime, 0.5);

        K1 = K1(totalTime);
        K2 = K2(max_a, totalTime);
        K3 = K3(totalTime);
        M = max_a;
    }

    @Override
    public double[] getOutput(double t) {
        double Position, Velocity, Acceleration;

        if(t< getTotalTime()){
            Position = direction * K2 * (t - (K3 * Math.sin(K1 * t)));
            Velocity = direction * K2 * (1 - Math.cos(K1 * t));
            Acceleration = direction * M * Math.sin(K1 * t);
        } else {
            Position = direction * K2 * (totalTime - (K3 * Math.sin(K1 * totalTime)));
            Velocity = 0.0;
            Acceleration = 0.0;
        }

        return new double[]{Position, Velocity, Acceleration};
    }

    public double getTotalTime(){
        return totalTime;
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
