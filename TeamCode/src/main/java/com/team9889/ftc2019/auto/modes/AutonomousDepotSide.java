package com.team9889.ftc2019.auto.modes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.team9889.ftc2019.auto.AutoModeBase;
import com.team9889.ftc2019.auto.actions.DriveToPosition;
import com.team9889.ftc2019.auto.actions.DriveTurn;
import com.team9889.ftc2019.auto.actions.IntakeToPosition;
import com.team9889.ftc2019.auto.actions.LiftLand;
import com.team9889.ftc2019.auto.actions.Outtake;
import com.team9889.lib.control.math.cartesian.Rotation2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by MannoMation on 10/26/2018.
 */

@Autonomous
public class AutonomousDepotSide extends AutoModeBase {

    @Override
    public void run(AllianceColor allianceColor) {
        runAction(new LiftLand());
        runAction(new DriveToPosition(29, 29));
//      Detect and intake
        runAction(new DriveToPosition(15, 15));
        runAction(new Outtake());
        runAction(new DriveTurn(new Rotation2d(-135, AngleUnit.DEGREES)));
        runAction(new DriveToPosition(66, 66));
        runAction(new IntakeToPosition(5));
    }
}