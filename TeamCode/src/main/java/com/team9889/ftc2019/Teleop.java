package com.team9889.ftc2019;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DigitalChannel;

/**
 * Created by joshua9889 on 3/28/2018.
 */

@TeleOp
public class Teleop extends Team9889Linear{

    boolean intake = true;
    boolean goback = false;

    @Override
    public void runOpMode() {
        waitForStart(false);

        while (opModeIsActive()){
            Robot.getDrive().setThrottleSteerPower(-gamepad1.left_stick_y, gamepad1.right_stick_x);

            if (gamepad2.a)
                Robot.getIntake().intake();
            else if (gamepad2.y)
                Robot.getIntake().outtake();
            else if (gamepad2.b)
                Robot.getIntake().stop();

            if (gamepad2.dpad_down){
                if (intake){
                    goback = true;
                    intake = false;
                }
            } else
                intake = true;

            if (goback && Math.abs(gamepad2.left_stick_y) < 0.01){
                Robot.getIntake().setIntakeExtenderPower(-1);
            } else {
                Robot.getIntake().setIntakeExtenderPower(-gamepad2.left_stick_y);
                goback = false;
            }


            updateTelemetry();
        }
    }
}
