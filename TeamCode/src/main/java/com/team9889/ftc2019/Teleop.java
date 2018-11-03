package com.team9889.ftc2019;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by joshua9889 on 3/28/2018.
 */

@TeleOp
public class Teleop extends Team9889Linear{

    private double pos = 0.4;

    @Override
    public void runOpMode() {
        waitForStart(false);

        while (opModeIsActive()){
            Robot.getDrive().setThrottleSteerPower(-gamepad1.left_stick_y, gamepad1.right_stick_x);

            if (gamepad1.dpad_down)
                Robot.getLift().setLiftPower(-1);
            else if (gamepad1.dpad_up)
                Robot.getLift().setLiftPower(0.25);
            else
                Robot.getLift().setLiftPower(0.0);

            if(gamepad1.left_bumper)
                Robot.getLift().setHookPosition(0);
            else if (gamepad1.right_bumper)
                Robot.getLift().setHookPosition(180);

            if (gamepad2.a)
                Robot.getIntake().intake();
            else if (gamepad2.y)
                Robot.getIntake().outtake();
            else if (gamepad2.b)
                Robot.getIntake().stop();

            Robot.getIntake().setIntakeExtenderPower(-gamepad2.left_stick_y);

            if(gamepad2.right_bumper)
                pos  = 0.4;
            else if(gamepad2.left_bumper)
                pos = 0.9;

            Robot.getIntake().setIntakeRotatorPosition(pos);

            Robot.getCamera().setXYAxisPosition(0, 0.5);

            updateTelemetry();
        }
    }
}
