package com.team9889.ftc2019.test.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.AutoModeBase;
import com.team9889.ftc2019.subsystems.Lift;

/**
 * Created by MannoMation on 1/14/2019.
 */
@Autonomous
public class TestLiftControl extends AutoModeBase {
    @Override
    public void run(AllianceColor allianceColor) {

        Robot.getLift().setLiftState(Lift.LiftStates.DOWN);
        while (opModeIsActive()){
            Robot.getLift().update(new ElapsedTime());

            Robot.getLift().outputToTelemetry(telemetry);
            telemetry.update();
        }
    }
}
