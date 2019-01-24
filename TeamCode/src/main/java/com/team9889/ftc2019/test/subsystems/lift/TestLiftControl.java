package com.team9889.ftc2019.test.subsystems.lift;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.AutoModeBase;
import com.team9889.ftc2019.states.LiftStates;

/**
 * Created by MannoMation on 1/14/2019.
 */
@Autonomous
@Disabled
public class TestLiftControl extends AutoModeBase {
    @Override
    public void run(AllianceColor allianceColor) {

        Robot.getLift().setLiftState(LiftStates.DOWN);
        while (opModeIsActive()){
            Robot.getLift().update(new ElapsedTime());

            Robot.getLift().outputToTelemetry(telemetry);
            telemetry.update();
        }
    }
}
