package com.team9889.ftc2019.test.local;

import com.team9889.lib.CruiseLib;
import com.team9889.lib.control.math.cartesian.Rotation2d;
import com.team9889.lib.control.math.cartesian.Vector2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class TestPoseDistance {
    public static void main(String... args){
        System.out.println(CruiseLib.getDistanceBetweenTwoPosition2d(new Vector2d(),
                new Vector2d(1, 1)));

        System.out.println(CruiseLib.getAngleDifference(new Rotation2d(0, AngleUnit.DEGREES),
                new Rotation2d(90, AngleUnit.DEGREES)).getTheda(AngleUnit.DEGREES));


        System.out.println(new Vector2d(2, 0).mag());
    }
}
