package com.team9889.ftc2019.test.subsystems;

import com.team9889.ftc2019.auto.actions.Drive.DriveRelative;
import com.team9889.lib.control.math.cartesian.Rotation2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by joshua9889 on 12/28/2018.
 *
 * Test the DriveRelative Action in a simulation.
 */
public class TestDriveRelative {
    public static void main(String... args) {
        DriveRelative driveRelative =
                new DriveRelative(30, new Rotation2d(-90, AngleUnit.DEGREES));

        driveRelative.start();
        driveRelative.done();
    }
}
