package com.team9889.lib.control.motion;

public class ProfileParameters {
    private double max_v, max_a;

    public ProfileParameters(double max_v, double max_a){
        this.max_v = max_v;
        this.max_a = max_a;
    }

    public double getMaxV() {
        return max_v;
    }


    public double getMaxA() {
        return max_a;
    }
}
