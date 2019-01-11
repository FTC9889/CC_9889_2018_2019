package com.team9889.ftc2019.test.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.team9889.ftc2019.auto.AutoModeBase;
import com.team9889.ftc2019.auto.actions.Drive.DriveToDistanceAndAngle;

/**
 * Created by MannoMation on 1/9/2019.
 */

@Autonomous
@Disabled
public class TestDriveToDistanceAndAngle extends AutoModeBase {
    @Override
    public void run(AllianceColor allianceColor) {
        runAction(new DriveToDistanceAndAngle(50, 0, 7000));
    }
}
