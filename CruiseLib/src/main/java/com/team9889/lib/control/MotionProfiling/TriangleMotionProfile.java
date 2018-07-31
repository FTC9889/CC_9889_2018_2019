package com.team9889.lib.control.MotionProfiling;

public class TriangleMotionProfile implements MotionProfile {

    private double totalTime;

    @Override
    public void calculate(double setpoint, double max_v, double max_a) {
        totalTime = (2 * setpoint)/max_v;

        System.out.println(totalTime);

        totalTime = 2.0 * Math.sqrt(setpoint/max_a);
        System.out.println(totalTime);
    }

    @Override
    public double[] getOutput(double t) {
        return new double[0];
    }
}
