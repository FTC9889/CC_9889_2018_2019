package com.team9889.ftc2019;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by joshua9889 on 3/28/2018.
 */

@TeleOp
public class Teleop extends Team9889Linear{

    boolean intake = true;
    boolean gobackintake = false;
    boolean lift = true;
    boolean gobacklift = false;

    @Override
    public void runOpMode() {
        waitForStart(false);

//        Robot.getCamera().setXYAxisPosition();

        while (opModeIsActive()){
            Robot.getDrive().setThrottleSteerPower(-gamepad1.left_stick_y, gamepad1.right_stick_x);
/*
            if (gamepad1.a){
                if (lift) {
                    gobacklift = true;
                }
            }

            if (gobacklift){
                Robot.getLift().setLiftPower(-1);
            } else {
                gobacklift = false;
            }
*/
            if (gamepad2.a)
                Robot.getIntake().intake();
            else if (gamepad2.y)
                Robot.getIntake().outtake();
            else if (gamepad2.b)
                Robot.getIntake().stop();

            if (gamepad2.dpad_down){
                if (intake){
                    gobackintake = true;
                    intake = false;
                }
            } else
                intake = true;

            if (gobackintake && Math.abs(gamepad2.left_stick_y) < 0.01){
                Robot.getIntake().setIntakeExtenderPower(-1);
            } else if (Robot.getIntake().intakeSwitchValue() == true){
                Robot.getIntake().setIntakeExtenderPower(-gamepad2.left_stick_y);
                gobackintake = false;
            }


            updateTelemetry();
        }
    }
}
