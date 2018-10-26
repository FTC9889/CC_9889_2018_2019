package com.team9889.ftc2019.auto.modes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.team9889.ftc2019.auto.AutoModeBase;
import com.team9889.ftc2019.auto.actions.Drive;
import com.team9889.ftc2019.auto.actions.DriveTurn;
import com.team9889.lib.control.math.cartesian.Rotation2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by MannoMation on 10/5/2018.
 */

@Autonomous
public class AutonomousCraterSide extends AutoModeBase {
    @Override
    public void run(AllianceColor allianceColor) {
//      land
        runAction(new Drive(29, 29));
//      Detect and intake
        runAction(new DriveTurn(new Rotation2d(90, AngleUnit.DEGREES)));
        runAction(new Drive(29, 29));
        runAction(new DriveTurn(new Rotation2d(45, AngleUnit.DEGREES)));
        runAction(new Drive(60, 60));
//      Drop team marker
        runAction(new Drive(-5, -5));
        runAction(new DriveTurn(new Rotation2d(180, AngleUnit.DEGREES)));
        runAction(new Drive(61, 61));
//      Stick arm out to park
    }
}
