package com.team9889.ftc2019;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.team9889.ftc2019.subsystems.Camera;
import com.team9889.ftc2019.subsystems.Intake;
import com.team9889.ftc2019.subsystems.Lift;

/**
 * Created by joshua9889 on 3/28/2018.
 */

@TeleOp
public class Teleop extends Team9889Linear{

    private double leftClaw = 1;
    private double rightClaw = 1;
    private double leftArm = 1;
    private double rightArm = 1;
    private double liftStopper = 1;
    private double leftBumperState = 0;
    private double rightBumperState = 0;


    Intake.RotatorStates wantedRotatorState = Intake.RotatorStates.UP;

    @Override
    public void runOpMode() {
        waitForStart(false);

        Robot.getIntake().setIntakeRotatorPosition(0.75);

        while (opModeIsActive()){
            Robot.getDrive().setThrottleSteerPower(-gamepad1.left_stick_y, gamepad1.right_stick_x);

            if (gamepad1.left_bumper && leftClaw == 1 || leftBumperState == 1 && leftClaw == 1) {
                leftBumperState = 1;
                if (!gamepad1.left_bumper) {
                    Robot.getArms().setLeftClawClosed(true);
                    leftClaw = 0;
                    leftBumperState = 0;
                }
            }

            else if (gamepad1.left_bumper && leftClaw == 0 || leftBumperState == 1 && leftClaw == 0) {
                leftBumperState = 1;
                if (!gamepad1.left_bumper) {
                    Robot.getArms().setLeftClawOpen(true);
                    leftClaw = 1;
                    leftArm = 0;
                    leftBumperState = 0;
                }
            }

            if (gamepad1.right_bumper && rightClaw == 1 || rightBumperState == 1 && rightClaw == 1) {
                rightBumperState = 1;
                if (!gamepad1.right_bumper) {
                    Robot.getArms().setRightClawClosed(true);
                    rightClaw = 0;
                    rightBumperState = 0;
                }
            }

            else if (gamepad1.right_bumper && rightClaw == 0 || rightBumperState == 1 && rightClaw == 0) {
                rightBumperState = 1;
                if (!gamepad1.right_bumper) {
                    Robot.getArms().setRightClawOpen(true);
                    rightClaw = 1;
                    rightArm = 0;
                    rightBumperState = 0;
                }
            }


            if (gamepad1.dpad_down)
                Robot.getLift().setLiftPower(-1);
            else if (gamepad1.dpad_up)
                Robot.getLift().setLiftPower(0.25);
            else
                Robot.getLift().setLiftPower(0.0);

            if (gamepad1.dpad_left)
                Robot.getLift().setLiftState(Lift.LiftStates.DOWN);
            else if (gamepad1.dpad_right)
                Robot.getLift().setLiftState(Lift.LiftStates.HOOKHEIGHT);
            else if (gamepad2.x)
                Robot.getLift().setLiftState(Lift.LiftStates.SCOREINGHEIGHT);

            if(gamepad1.left_trigger > .1) {
//                Robot.getLift().setHookPosition(0);
            }
            else if (gamepad1.right_trigger > .1)
//                Robot.getLift().setHookPosition(180);

            if (gamepad2.left_bumper && liftStopper == 0) {
//                Robot.getLift().setStopperPosition(180);
                liftStopper = 1;
            }
            else if (gamepad2.left_bumper && liftStopper == 1) {
//                Robot.getLift().setStopperPosition(0);
                liftStopper = 0;
            }

            if (gamepad2.a)
                Robot.getIntake().intake();
            else if (gamepad2.y)
                Robot.getIntake().outtake();
            else if (gamepad2.b)
                Robot.getIntake().stop();

            Robot.getIntake().setIntakeExtenderPower(-gamepad2.left_stick_y);

            if(gamepad2.right_bumper) {
                wantedRotatorState = Intake.RotatorStates.UP;
            }
            else if(gamepad2.left_bumper) {
                wantedRotatorState = Intake.RotatorStates.DOWN;
            }

            if (gamepad1.a){
                Robot.getArms().setLeftArm(.35, .25);
                Robot.getArms().setLeftArm(.7, 0.25);
                Robot.getArms().setLeftArm(.7, 0.75);
                Robot.getArms().setRightArm(.25, 1);
                Robot.getArms().setRightArm(.25, 0.4);

            }

            if (gamepad1.b){
                Robot.getArms().setRightArm(.25, .1);
                Robot.getArms().setRightArm(.25, .2);
                Robot.getArms().setLeftArm(.35, .25);
                Robot.getArms().setLeftArm(.7, 0.75);
            }

            if (gamepad1.x){
                Robot.getIntake().setIntakeExtenderPosition(20);
            }

            if (gamepad1.x){
                Robot.getArms().setLeftClawOpen(true);
                Robot.getArms().setRightClawOpen(true);
            }

            if (rightArm == 0 && leftArm == 0){
                Robot.getArms().setRightArm(1, 1);
                Robot.getArms().setLeftArm(0, 0.25);
                leftArm = 1;
                rightArm = 1;
            }

            Robot.getLift().setLiftPower(-gamepad2.right_stick_y);

            Robot.getIntake().setIntakeRotatorState(wantedRotatorState);

            Robot.getCamera().setCameraPosition(Camera.CameraPositions.UPRIGHT);

            updateTelemetry();
        }
    }
}
