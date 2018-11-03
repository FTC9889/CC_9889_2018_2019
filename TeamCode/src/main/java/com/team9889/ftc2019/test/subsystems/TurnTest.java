package com.team9889.ftc2019.test.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.team9889.ftc2019.auto.AutoModeBase;
import com.team9889.ftc2019.auto.actions.DriveTurn;
import com.team9889.lib.control.math.cartesian.Rotation2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by MannoMation on 10/23/2018.
 */

@Autonomous
@Disabled
public class TurnTest extends AutoModeBase {
    @Override
    public void run(AllianceColor allianceColor) {
//        final double AngleToInches =  -90 * 0.13955556;
//        runAction(new Drive(AngleToInches, -AngleToInches));
        runAction(new DriveTurn(new Rotation2d(90, AngleUnit.DEGREES), 4000));
    }
}
