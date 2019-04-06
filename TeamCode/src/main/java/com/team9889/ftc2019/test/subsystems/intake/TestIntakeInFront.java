package com.team9889.ftc2019.test.subsystems.intake;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.team9889.ftc2019.auto.AutoModeBase;
import com.team9889.ftc2019.auto.actions.Intake.IntakeInFront;

/**
 * Created by MannoMation on 1/11/2019.
 */

@Autonomous
@Disabled
public class TestIntakeInFront extends AutoModeBase {
    @Override
    public void run(AutoModeBase.Side side, boolean doubleSample, boolean scoreSample) {
        runAction(new IntakeInFront(20, 2000, true));
    }
}
