package com.team9889.ftc2019.test.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.team9889.ftc2019.Team9889Linear;
import com.team9889.ftc2019.auto.AutoModeBase;
import com.team9889.ftc2019.auto.Autonomous;
import com.team9889.ftc2019.auto.actions.Drive.DriveMotionProfile;

/**
 * Created by MannoMation on 1/5/2019.
 */
@com.qualcomm.robotcore.eventloop.opmode.Autonomous
@Disabled
public class TunePVDAController extends AutoModeBase {

    @Override
    public void run(AllianceColor allianceColor) {
        runAction(new DriveMotionProfile(10));
    }
}
