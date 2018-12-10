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
    private


    Intake.RotatorStates wantedRotatorState = Intake.RotatorStates.UP;

    @Override
    public void runOpMode() {
        waitForStart(false);

        Robot.getIntake().setIntakeRotatorPosition(0.75);

        while (opModeIsActive()){
            Robot.getDrive().setThrottleSteerPower(-gamepad1.left_stick_y, gamepad1.right_stick_x);

            if (gamepad1.left_bumper && leftClaw == 1) {
//                Robot.getArms().setLeftClawClosed(true);
                leftClaw = 0;
            }
            else if (gamepad1.left_bumper && leftClaw == 0) {
//                Robot.getArms().setLeftClawOpen(true);
//                Robot.getArms().setLeftArm(.5, .5);
                leftClaw = 1;
                leftArm = 1;
            }

            if (gamepad1.right_bumper && rightClaw == 1) {
//                Robot.getArms().setRightClawClosed(true);
                rightClaw = 0;
            }
            else if (gamepad1.right_bumper && rightClaw == 0) {
//                Robot.getArms().setRightClawOpen(true);
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

            Robot.getLift().setLiftPower(-gamepad2.right_stick_y);

            Robot.getIntake().setIntakeRotatorState(wantedRotatorState);

            Robot.getCamera().setCameraPosition(Camera.CameraPositions.UPRIGHT);

            updateTelemetry();
        }
    }
}
