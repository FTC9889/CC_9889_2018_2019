package com.team9889.ftc2019.test.subsystems.dumper;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.team9889.ftc2019.Team9889Linear;

/**
 * Created by MannoMation on 2/27/2019.
 */

@TeleOp
@Disabled
public class TestDumperPositionViaController extends Team9889Linear {
    @Override
    public void runOpMode() throws InterruptedException {
       waitForStart(false);

       while (opModeIsActive()){
           Robot.getDumper().setDumperRotatorPosition(gamepad1.right_stick_y);

           Robot.outputToTelemetry(telemetry);
           telemetry.update();
       }
    }
}
