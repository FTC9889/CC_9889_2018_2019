package com.team9889.lib.control.math;

import com.team9889.lib.control.math.cartesian.Vector2d;

/**
 * Created by joshua9889 on 7/10/2018.
 */

public class BezierInterpolation {
    public static void main(String... args){
        BezierInterpolation bezierCurve = new BezierInterpolation();

        Vector2d startVector2D = new Vector2d(0, 0);
        Vector2d endVector2D = new Vector2d(2, 5);
        Vector2d alpha = new Vector2d(4, 2);
        Vector2d beta = new Vector2d(2, 4);

        int step = 1000;
        for (int i = 0; i < step; i++) {
            Vector2d pose = bezierCurve.calculateAtAT(startVector2D, endVector2D, alpha, beta, ((double) i/(double) step));
            System.out.println(pose.getX() + "\t" + pose.getY());
        }

    }

    public Vector2d calculateAtAT(Vector2d startVector2D, Vector2d endVector2D, Vector2d alpha, Vector2d beta, double t){
        double[] Point = calculateDoubleAtAT(startVector2D, endVector2D, alpha, beta, t);
        double[] PointBefore = calculateDoubleAtAT(startVector2D, endVector2D, alpha, beta, t-(t/10000.0));

        double deltaX = PointBefore[0] - Point[0];
        double deltaY = PointBefore[1] - Point[1];
        double radianAngle = Math.atan2(deltaX, deltaY);

        return new Vector2d(Point[0], Point[1]);
    }

    private double[] calculateDoubleAtAT(Vector2d startVector2D, Vector2d endVector2D, Vector2d alpha, Vector2d beta, double t){
        double firstX = Math.pow(1-t, 3) * startVector2D.getX();
        double secondX = 3.0 * Math.pow(1-t, 2) * t * alpha.getX();
        double thirdX = 3.0 * (1-t) * (t*t) * beta.getX();
        double fourthX = (t*t*t) * endVector2D.getX();
        double X = firstX + secondX + thirdX + fourthX;

        double firstY = Math.pow(1-t, 3) *  startVector2D.getY();
        double secondY = 3.0 * Math.pow(1-t, 2) * t * alpha.getY();
        double thirdY = 3.0 * (1-t) * (t*t) * beta.getY();
        double fourthY = (t*t*t) * endVector2D.getY();
        double Y = firstY + secondY + thirdY + fourthY;


        return new double[]{X, Y};
    }
}
