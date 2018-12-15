package com.team9889.ftc2019.test.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.team9889.ftc2019.Team9889Linear;

/**
 * Created by MannoMation on 12/14/2018.
 */
@TeleOp
public class TestArmsTeleop extends Team9889Linear {
    @Override
    public void runOpMode() {
        waitForStart(false);

        while (opModeIsActive()) {
            Robot.getArms().setRightArm(-gamepad1.left_stick_y, -gamepad1.right_stick_y);

            Robot.getArms().setLeftArm(-gamepad2.left_stick_y, gamepad2.right_stick_y);

            updateTelemetry();
        }
    }
}
