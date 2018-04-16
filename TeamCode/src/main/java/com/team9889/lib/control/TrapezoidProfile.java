package com.team9889.lib.control;

/**
 * Created by joshua9889 on 4/3/2018.
 */

public class TrapezoidProfile extends Profile {

    public TrapezoidProfile(double max_v, double max_a, double distance, double t, int timeStep){
        this.max_v = max_v;
        this.max_a = max_a;
        this.distance = distance;
        this.t = t;
        this.timeStep = timeStep;
    }

    @Override
    public void calculate() {
        for (int dt = 0; dt < t; dt+=timeStep) {
            System.out.println(dt);
        }
    }
}
