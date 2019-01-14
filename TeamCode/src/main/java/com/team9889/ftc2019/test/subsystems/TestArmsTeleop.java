package com.team9889.ftc2019.test.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.team9889.ftc2019.Team9889Linear;

/**
 * Created by MannoMation on 12/14/2018.
 */
@TeleOp
public class TestArmsTeleop extends Team9889Linear {

    double leftShoulder = 0;
    double leftElbow = .131;

    double rightShoulder = .998;
    double rightElbow = .844;

    double dp = 0.001;

    @Override
    public void runOpMode() {
        waitForStart(false);

        while (opModeIsActive()) {
            if(gamepad1.dpad_down)
                leftShoulder -= dp;
            else if(gamepad1.dpad_up)
                leftShoulder += dp;

            if (gamepad1.dpad_right)
                leftElbow -= dp;
            else if(gamepad1.dpad_left)
                leftElbow += dp;

            if(gamepad1.a)
                rightShoulder -= dp;
            else if(gamepad1.y)
                rightShoulder += dp;

            if (gamepad1.b)
                rightElbow -= dp;
            else if(gamepad1.x)
                rightElbow += dp;

            Robot.getArms().setLeftArm(leftShoulder, leftElbow);
            Robot.getArms().setRightArm(rightShoulder, rightElbow);

            telemetry.addData("Left Shoulder Position", leftShoulder);
            telemetry.addData("Left Elbow Position", leftElbow);
            telemetry.addData("Right Shoulder Position", rightShoulder);
            telemetry.addData("Right Elbow Position", rightElbow);
            telemetry.update();
        }
    }
}
