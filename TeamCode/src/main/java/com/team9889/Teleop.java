package com.team9889;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by joshua9889 on 3/28/2018.
 */

@TeleOp
public class Teleop extends Team9889Linear{
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart(false);
    }
}