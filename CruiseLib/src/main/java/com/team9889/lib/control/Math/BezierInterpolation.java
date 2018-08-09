package com.team9889.lib.control.Math;

/**
 * Created by joshua9889 on 7/10/2018.
 */

public class BezierInterpolation {
    public static void main(String... args){
        BezierInterpolation bezierCurve = new BezierInterpolation();

        Position2d startPosition2d = new Position2d(0, 0, new Rotation2d(0, Rotation2d.Unit.DEGREES));
        Position2d endPosition2d = new Position2d(2, 5, new Rotation2d(0, Rotation2d.Unit.DEGREES));
        Position2d alpha = new Position2d(4, 2, new Rotation2d(0, Rotation2d.Unit.DEGREES));
        Position2d beta = new Position2d(2, 4, new Rotation2d(0, Rotation2d.Unit.DEGREES));

        int step = 1000;
        for (int i = 0; i < step; i++) {
            Position2d pose = bezierCurve.calculateAtAT(startPosition2d, endPosition2d, alpha, beta, ((double) i/(double) step));
            System.out.println(pose.getX() + "\t" + pose.getY());
        }

    }

    public Position2d calculateAtAT(Position2d startPosition2d, Position2d endPosition2d, Position2d alpha, Position2d beta, double t){
        double[] Point = calculateDoubleAtAT(startPosition2d, endPosition2d, alpha, beta, t);
        double[] PointBefore = calculateDoubleAtAT(startPosition2d, endPosition2d, alpha, beta, t-(t/10000.0));

        double deltaX = PointBefore[0] - Point[0];
        double deltaY = PointBefore[1] - Point[1];
        double radianAngle = Math.atan2(deltaX, deltaY);

        return new Position2d(Point[0], Point[1], new Rotation2d(radianAngle, Rotation2d.Unit.RADIANS));
    }

    private double[] calculateDoubleAtAT(Position2d startPosition2d, Position2d endPosition2d, Position2d alpha, Position2d beta, double t){
        double firstX = Math.pow(1-t, 3) * startPosition2d.getX();
        double secondX = 3.0 * Math.pow(1-t, 2) * t * alpha.getX();
        double thirdX = 3.0 * (1-t) * (t*t) * beta.getX();
        double fourthX = (t*t*t) * endPosition2d.getX();
        double X = firstX + secondX + thirdX + fourthX;

        double firstY = Math.pow(1-t, 3) *  startPosition2d.getY();
        double secondY = 3.0 * Math.pow(1-t, 2) * t * alpha.getY();
        double thirdY = 3.0 * (1-t) * (t*t) * beta.getY();
        double fourthY = (t*t*t) * endPosition2d.getY();
        double Y = firstY + secondY + thirdY + fourthY;


        return new double[]{X, Y};
    }
}
