package com.team9889.lib.testCode;

import com.team9889.lib.CruiseLib;
import com.team9889.lib.control.Math.Pose;
import com.team9889.lib.control.Math.Rotation2d;

public class TestPoseDistance {
    public static void main(String... args){
        System.out.println(CruiseLib.getDistanceBetweenTwoPoses(new Pose(),
                new Pose(1, 1, new Rotation2d(45, Rotation2d.Unit.DEGREES))));

        System.out.println(CruiseLib.getAngleDifference(new Rotation2d(0, Rotation2d.Unit.DEGREES),
                new Rotation2d(90, Rotation2d.Unit.DEGREES)).getTheda(Rotation2d.Unit.DEGREES));
    }
}
