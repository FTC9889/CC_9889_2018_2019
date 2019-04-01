package com.team9889.ftc2019.test.subsystems.intake;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.team9889.ftc2019.Team9889Linear;

/**
 * Created by MannoMation on 2/22/2019.
 */

@TeleOp
@Disabled
public class TestHopperDumperPosition extends Team9889Linear {
    @Override
    public void runOpMode() throws InterruptedException {
       waitForStart(false);

       while (opModeIsActive()){

           telemetry.update();
       }
    }
}
