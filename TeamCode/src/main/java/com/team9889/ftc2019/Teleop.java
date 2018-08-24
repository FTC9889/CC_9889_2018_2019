package com.team9889.ftc2019;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by joshua9889 on 3/28/2018.
 */

@TeleOp
public class Teleop extends Team9889Linear{
    @Override
    public void runOpMode() {
        waitForStart(false);

        while (opModeIsActive()){

            Robot.getDrive().setThrottleSteerPower(-gamepad1.left_stick_y, gamepad1.right_stick_x);
        }
    }
}
