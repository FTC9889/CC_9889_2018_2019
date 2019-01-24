package com.team9889.ftc2019.test.subsystems.arms;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.team9889.ftc2019.auto.AutoModeBase;

/**
 * Created by MannoMation on 11/29/2018.
 */

@Autonomous
@Disabled
public class TestArms extends AutoModeBase {
    @Override
    public void run(AllianceColor allianceColor) {
//        Robot.getArms().setLeftArm(1, 1);
//        runAction(new Wait(3000));
//        Robot.getArms().setLeftArm(0,0);

        while (opModeIsActive()) {
            Robot.getArms().setLeftClawOpen(gamepad2.a);
            Robot.getArms().setRightClawOpen(gamepad2.b);

//            Robot.getArms().leftClaw.setPosition(gamepad1.left_stick_y);
//            Robot.getArms().rightClaw.setPosition(gamepad1.right_stick_y);

            telemetry.addData("Left", gamepad1.left_stick_y);
            telemetry.addData("Right", gamepad1.right_stick_y);
            telemetry.update();
        }
    }
}
