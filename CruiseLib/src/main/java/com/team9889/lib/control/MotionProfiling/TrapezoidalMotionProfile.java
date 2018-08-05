package com.team9889.lib.control.MotionProfiling;

import com.team9889.lib.Logger;

/**
 * Created by joshua9889 on 7/16/2018.
 *
 * See https://www.desmos.com/calculator/y5dfaz9vlr for a graph of the equations being implemented.
 */

public class TrapezoidalMotionProfile implements MotionProfile {

    private double totalTime, timeToAcc, timeToCruise;
    private double setpoint, max_v, max_a;
    private double direction;


    public TrapezoidalMotionProfile(){}

    public TrapezoidalMotionProfile(double setpoint, double max_v, double max_a){
        calculate(setpoint, max_v, max_a);
    }

    public static void main(String... args){
        Logger log = new Logger("traProfile.csv");
        TrapezoidalMotionProfile profile = new TrapezoidalMotionProfile();
        profile.calculate(2.5, 1.5, 2);

        int step = 10000;
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

        this.setpoint = Math.abs(setpoint);
        this.max_v = max_v;
        this.max_a = max_a;

        // This is in absolute time
        timeToAcc = (max_v/max_a);
        totalTime = (max_v/max_a)+(this.setpoint/max_v);
        timeToCruise = totalTime-timeToAcc;
    }

    @Override
    public double[] getOutput(double t) {
        double Position, Velocity, Acceleration;

        if (t<=timeToAcc){
            Position = p1(t);
            Velocity = v1(t);
            Acceleration = a1(t);
        } else if(t<=timeToCruise){
            Position = p2(t);
            Velocity = v2(t);
            Acceleration = a2(t);
        } else if(t<=totalTime){
            Position = p3(t);
            Velocity = v3(t);
            Acceleration = a3(t);
        } else {
            Position = setpoint;
            Velocity = 0;
            Acceleration = 0;
        }

        Position *= direction;
        Velocity *= direction;
        Acceleration *= direction;

        return new double[]{Position, Velocity, Acceleration};
    }

    private double p1(double t){
        double A2 = (max_a/2.0);
        return A2 * t * t;
    }

    private double p2(double t){
        double Vx = max_v * t;
        double V2 = max_v * max_v;
        return  Vx - (V2/(2.0 * max_a));
    }

    private double p3(double t){
        double tv = totalTime * max_v;
        double tax = totalTime * max_a * t;
        double Vsquared_divByA = Math.pow(max_v, 2) / max_a;
        double lastPart = 0.5 * ((max_a * t*t) + (max_a * totalTime * totalTime));
        return tv + tax - Vsquared_divByA - lastPart;
    }

    private double v1(double t){
        return max_a * t;
    }

    private double v2(double t){
        return max_v;
    }

    private double v3(double t){
        return (max_a * totalTime) - (max_a * t);
    }

    private double a1(double t){
        return max_a;
    }

    private double a2(double t){
        return 0.0;
    }

    private double a3(double t){
        return -max_a;
    }
}
