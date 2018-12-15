package com.team9889.ftc2019.test.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.team9889.ftc2019.auto.AutoModeBase;
import com.team9889.ftc2019.auto.actions.RightArmToPosition;
import com.team9889.ftc2019.auto.actions.RightClawOpen;
import com.team9889.ftc2019.auto.actions.Wait;

/**
 * Created by MannoMation on 11/29/2018.
 */

@Autonomous
public class TestArms extends AutoModeBase {
    @Override
    public void run(AllianceColor allianceColor) {
        Robot.getArms().setLeftArm(1, 1);
        runAction(new Wait(3000));
        Robot.getArms().setLeftArm(0,0);
    }
}
