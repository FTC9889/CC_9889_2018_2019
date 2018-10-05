package com.team9889.ftc2019.test.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.team9889.ftc2019.auto.AutoModeBase;
import com.team9889.ftc2019.auto.actions.Drive;
import com.team9889.ftc2019.auto.actions.Intake;
import com.team9889.ftc2019.auto.actions.Turn;

/**
 * Created by MannoMation on 10/5/2018.
 */

@Autonomous
public class TestDrive3 extends AutoModeBase {
    @Override
    public void run(AllianceColor allianceColor) {
//      ThreadAction(new Intake());
 //       runAction(new Drive(25, 25));
        runAction(new Turn(-90));
    }
}
