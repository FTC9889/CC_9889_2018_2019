package com.team9889.ftc2019;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by joshua9889 on 3/28/2018.
 */

@TeleOp
public class Teleop extends Team9889Linear{

    private double pos = 0.4;
    private double leftClaw = 1;
    private double rightClaw = 1;
    private double leftArm = 1;
    private double rightArm = 1;
    private double liftStopper = 1;
    private double intakeRotator = 1;

    ElapsedTime liftTimer = new ElapsedTime();

    @Override
    public void runOpMode() {
        waitForStart(false);

        while (opModeIsActive()){
            Robot.getDrive().setThrottleSteerPower(-gamepad1.left_stick_y, gamepad1.right_stick_x);

            if (gamepad1.left_bumper && leftClaw == 1) {
                Robot.getArms().setLeftClawClosed(true);
                leftClaw = 0;
            }
            else if (gamepad1.left_bumper && leftClaw == 0) {
                Robot.getArms().setLeftClawOpen(true);
                Robot.getArms().setLeftArm(.5, .5);
                leftClaw = 1;
                leftArm = 1;
            }

            if (gamepad1.right_bumper && rightClaw == 1) {
                Robot.getArms().setRightClawClosed(true);
                rightClaw = 0;
            }
            else if (gamepad1.right_bumper && rightClaw == 0) {
                Robot.getArms().setRightClawOpen(true);
//                Robot.getArms().setRightArm(.5, .5);
                rightClaw = 1;
//                rightArm = 1;
            }

            if (gamepad1.dpad_down)
                Robot.getLift().setLiftPower(-1);
            else if (gamepad1.dpad_up)
                Robot.getLift().setLiftPower(0.25);
            else
                Robot.getLift().setLiftPower(0.0);

            if(gamepad1.left_trigger > .1)
                Robot.getLift().setHookPosition(0);
            else if (gamepad1.right_trigger > .1)
                Robot.getLift().setHookPosition(180);

            if (gamepad2.left_bumper && liftStopper == 0) {
                Robot.getLift().setStopperPosition(180);
                liftStopper = 1;
            }
            else if (gamepad2.left_bumper && liftStopper == 1) {
                Robot.getLift().setStopperPosition(0);
                liftStopper = 0;
            }

            if (gamepad2.a)
                Robot.getIntake().intake();
            else if (gamepad2.y)
                Robot.getIntake().outtake();
            else if (gamepad2.b)
                Robot.getIntake().stop();

            Robot.getIntake().setIntakeExtenderPower(-gamepad2.left_stick_y);

            if(gamepad2.right_bumper && intakeRotator == 1) {
                pos = 0.4;
                intakeRotator = 0;
            }
            else if(gamepad2.right_bumper && intakeRotator == 0) {
                pos = 0.9;
                intakeRotator = 1;
            }

            Robot.getLift().setLiftPower(-gamepad2.right_stick_y);

//            if (gamepad1.start)
//                Robot.getLift().setLiftPosition(12);

            if (gamepad1.a) {
                Robot.getArms().setRightArm(.5, .5);
                Robot.getArms().setLeftArm(.5, .5);
            }
            else if (gamepad1.b) {
                Robot.getArms().setRightArm(.25, .25);
                Robot.getArms().setLeftArm(.25, .25);
            }
            else if (gamepad1.y){
                Robot.getArms().setRightArm(0,0);
                Robot.getArms().setLeftArm(0, 0);
            }
            else if (gamepad1.x){
                Robot.getArms().setRightArm(1, 1);
                Robot.getArms().setLeftArm(1, 1);
            }

            if (gamepad2.left_trigger > .1 && leftClaw == 1) {
                Robot.getArms().setLeftClawClosed(true);
                leftClaw = 0;
            }
            else if (gamepad2.left_trigger > .1 && leftClaw == 0) {
                Robot.getArms().setLeftClawOpen(true);
                Robot.getArms().setLeftArm(.5, .5);
                leftClaw = 1;
                leftArm = 1;
            }

            if (gamepad2.right_trigger > .1 && rightClaw == 1) {
                Robot.getArms().setRightClawClosed(true);
                rightClaw = 0;
            }
            else if (gamepad2.right_trigger > .1 && rightClaw == 0) {
                Robot.getArms().setRightClawOpen(true);
//                Robot.getArms().setRightArm(.5, .5);
                rightClaw = 1;
//                rightArm = 1;
            }

            if (gamepad2.dpad_down){
                Robot.getArms().setRightArm(.5, .5);
                Robot.getArms().setLeftArm(.5, .5);
            }



            Robot.getIntake().setIntakeRotatorPosition(pos);

            Robot.getCamera().setXYAxisPosition(0, 0.5);

            updateTelemetry();
        }
    }
}
