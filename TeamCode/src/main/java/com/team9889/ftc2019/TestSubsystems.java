package com.team9889.ftc2019;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by joshua9889 on 3/28/2018.
 */

@TeleOp
public class TestSubsystems extends Team9889Linear{
    @Override
    public void runOpMode() {
        Robot.init(hardwareMap, true);

        Robot.test(telemetry);
    }
}