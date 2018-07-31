package com.team9889.lib.control.Math;

public class BezierCurve {
    public double[] calculateAtAT(Pose startPose, Pose endPose, Pose alpha, Pose beta, double t){
        double firstX = Math.pow(1-t, 3) * startPose.getY();
        double secondX = 3.0 * Math.pow(1-t, 2) * t * alpha.getY();
        double thirdX = 3.0 * (1-t) * (t*t) * beta.getY();
        double fourthX = (t*t*t) * endPose.getY();
        double X = firstX + secondX + thirdX + fourthX;

        double firstY = Math.pow(1-t, 3) * startPose.getY();
        double secondY = 3.0 * Math.pow(1-t, 2) * t * alpha.getY();
        double thirdY = 3.0 * (1-t) * (t*t) * beta.getY();
        double fourthY = (t*t*t) * endPose.getY();
        double Y = firstY + secondY + thirdY + fourthY;

        double[] output = new double[2];
        output[0] = X;
        output[1] = Y;

        return output;
    }
}
