package com.team9889.ftc2019.test.subsystems.drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.team9889.ftc2019.auto.AutoModeBase;
import com.team9889.ftc2019.auto.actions.Drive.DriveMotionProfile;
import com.team9889.ftc2019.auto.actions.Wait;

/**
 * Created by MannoMation on 1/5/2019.
 */
@Autonomous
//@Disabled
public class TunePVDAController extends AutoModeBase {
    @Override
    public void run(AutoModeBase.Side side, boolean doubleSample, boolean scoreSample) {
        runAction(new DriveMotionProfile(30, 0));
        runAction(new Wait(1000));
//        runAction(new Turn(new Rotation2d(-45, AngleUnit.DEGREES), 5000));
//        runAction(new Wait(1000));
//        runAction(new DriveMotionProfile(15, -45));
//        runAction(new DriveMotionProfile(-50, 0));
//        runAction(new Wait(1000));
//        runAction(new DriveMotionProfile(15, 0));
    }
}
