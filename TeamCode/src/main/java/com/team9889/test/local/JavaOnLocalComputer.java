package com.team9889.test.local;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;

/**
 * Created by joshua9889 on 3/28/2018.
 *
 * Used to test java code on a computer without a phone.
 * Good for testing logic, correct outputs for functions, etc.
 */

public class JavaOnLocalComputer {
    public static void main(String[] args) throws InterruptedException {
        // Test Code here
        Position position = new Position(DistanceUnit.INCH, 0, 0, 0, 0);
        Position position1 = new Position(DistanceUnit.INCH, 0, 5, 0, 1000000);
        System.out.println(position.x);
        System.out.println(position.y);
        System.out.println(position.z);
        System.out.println(position1.acquisitionTime-position.acquisitionTime);

    }
}
