package com.team9889.lib.testCode;

import com.team9889.lib.control.Math.BezierInterpolation;
import com.team9889.lib.control.Math.Pose;
import com.team9889.lib.control.Math.Rotation2d;

public class TestBezierCurve {
    public static void main(String... args){
        BezierInterpolation bezierCurve = new BezierInterpolation();

        Pose startPose = new Pose(100, 1, new Rotation2d(0, Rotation2d.Unit.DEGREES));
        Pose endPose = new Pose(-110, 613.4, new Rotation2d(0, Rotation2d.Unit.DEGREES));
        Pose alpha = new Pose(207, 544, new Rotation2d(0, Rotation2d.Unit.DEGREES));
        Pose beta = new Pose(-212, -144, new Rotation2d(0, Rotation2d.Unit.DEGREES));

        Pose pose = bezierCurve.calculateAtAT(startPose, endPose, alpha, beta, 0);
        System.out.println(pose.getX() + "\t" + pose.getY());
    }
}
