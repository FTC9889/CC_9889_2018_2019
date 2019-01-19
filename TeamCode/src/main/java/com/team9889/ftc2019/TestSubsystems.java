package com.team9889.ftc2019;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by joshua9889 on 3/28/2018.
 */

@TeleOp
@Disabled
public class TestSubsystems extends Team9889Linear{
    @Override
    public void runOpMode() {
        Robot.init(hardwareMap, true);

        waitForStart();

        while (opModeIsActive()) {
            Robot.outputToTelemetry(telemetry);
            telemetry.update();
        }
    }
}
