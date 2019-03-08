package com.team9889.ftc2019.test.subsystems.drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.team9889.ftc2019.auto.AutoModeBase;
import com.team9889.ftc2019.auto.actions.Drive.DriveMotionProfile;
import com.team9889.lib.control.math.cartesian.Rotation2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by MannoMation on 1/5/2019.
 */
@Autonomous
//@Disabled
public class TunePVDAController extends AutoModeBase {
    @Override
    public void run(AllianceColor allianceColor) {
        runAction(new DriveMotionProfile(10, new Rotation2d(0, AngleUnit.DEGREES)));
    }
}
