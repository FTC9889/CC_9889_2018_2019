package com.team9889.lib.control.Math;

public class RobotPose {
    private double[] q = new double[3];

    public final int X = 0;
    public final int Y = 1;
    public final int THEDA = 2;

    public RobotPose(double x, double y, double theda){
        q[0] = x;
        q[1] = y;
        q[2] = theda;
    }

    public double[] getPose() {
        return q;
    }
}
