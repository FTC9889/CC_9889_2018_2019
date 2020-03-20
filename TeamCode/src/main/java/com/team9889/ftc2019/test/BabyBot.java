package com.team9889.ftc2019.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by joshua9889 on 5/11/2019.
 */

@Autonomous
public class BabyBot extends OpMode {
    private DcMotor left, right;

    @Override
    public void init() {
        left = hardwareMap.get(DcMotor.class, "left");
        right = hardwareMap.get(DcMotor.class, "right");
    }

    @Override
    public void loop() {
        left.setPower(-gamepad1.left_stick_y);
        right.setPower(gamepad1.right_stick_y);
    }
}
