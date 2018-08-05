package com.team9889.lib.control.Math;

/**
 * Created by joshua9889 on 7/10/2018.
 */

public class BezierInterpolation {
    public static void main(String... args){
        BezierInterpolation bezierCurve = new BezierInterpolation();

        Pose startPose = new Pose(100, 1, new Rotation2d(0, Rotation2d.Unit.DEGREES));
        Pose endPose = new Pose(-110, 613.4, new Rotation2d(0, Rotation2d.Unit.DEGREES));
        Pose alpha = new Pose(207, 544, new Rotation2d(0, Rotation2d.Unit.DEGREES));
        Pose beta = new Pose(-212, -144, new Rotation2d(0, Rotation2d.Unit.DEGREES));

        Pose pose = bezierCurve.calculateAtAT(startPose, endPose, alpha, beta, 0);
        System.out.println(pose.getX() + "\t" + pose.getY());
    }

    public Pose calculateAtAT(Pose startPose, Pose endPose, Pose alpha, Pose beta, double t){
        double[] Point = calculateDoubleAtAT(startPose, endPose, alpha, beta, t);
        double[] PointBefore = calculateDoubleAtAT(startPose, endPose, alpha, beta, t-(t/10000.0));

        double deltaX = PointBefore[0] - Point[0];
        double deltaY = PointBefore[1] - Point[1];
        double radianAngle = Math.atan2(deltaX, deltaY);

        return new Pose(Point[0], Point[1], new Rotation2d(radianAngle, Rotation2d.Unit.RADIANS));
    }

    private double[] calculateDoubleAtAT(Pose startPose, Pose endPose, Pose alpha, Pose beta, double t){
        double firstX = Math.pow(1-t, 3) * startPose.getY();
        double secondX = 3.0 * Math.pow(1-t, 2) * t * alpha.getY();
        double thirdX = 3.0 * (1-t) * (t*t) * beta.getY();
        double fourthX = (t*t*t) * endPose.getY();
        double X = firstX + secondX + thirdX + fourthX;

        double firstY = Math.pow(1-t, 3) *  startPose.getY();
        double secondY = 3.0 * Math.pow(1-t, 2) * t * alpha.getY();
        double thirdY = 3.0 * (1-t) * (t*t) * beta.getY();
        double fourthY = (t*t*t) * endPose.getY();
        double Y = firstY + secondY + thirdY + fourthY;


        return new double[]{X, Y};
    }
}
