package com.team9889.ftc2019.test.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.team9889.ftc2019.auto.AutoModeBase;
import com.team9889.ftc2019.auto.actions.DriveToPosition;

/**
 * Created by MannoMation on 11/2/2018.
 */

@Autonomous
public class Test extends AutoModeBase {
    @Override
    public void run(AllianceColor allianceColor) {
        runAction(new DriveToPosition(10, 10));
    }
}
