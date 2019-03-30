package com.team9889.ftc2019.test.subsystems.lift;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.team9889.ftc2019.subsystems.ScoringLift;

/**
 * Created by joshua9889 on 1/24/2019.
 */
@Autonomous
@Disabled
public class TestLift extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ScoringLift mLift = new ScoringLift();
        mLift.init(hardwareMap, false);

        waitForStart();

        while (opModeIsActive()) {

            mLift.setLiftPower(-gamepad1.right_stick_y);

            mLift.outputToTelemetry(telemetry);
            telemetry.update();
        }
    }
}
