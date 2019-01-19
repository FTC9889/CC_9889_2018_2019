package com.team9889.ftc2019;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.states.LiftStates;
import com.team9889.ftc2019.subsystems.Arms;
import com.team9889.ftc2019.subsystems.Camera;
import com.team9889.ftc2019.subsystems.Intake;

import java.util.Arrays;

/**
 * Created by MannoMation on 1/14/2019.
 */

@TeleOp(name = "Teleop")
public class Teleop2 extends Team9889Linear {
    private boolean autoMode = true;
    private Arms.ArmStates wanted = Arms.ArmStates.NULL;
    private boolean lastChangeMode, lastLeftClawChangeMode, lastRightClawChangeMode;
    private boolean first = true;
    ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() {

        waitForStart(false);

        Robot.getIntake().isAutoIntakeDone = true;
        Robot.whichMineral = com.team9889.ftc2019.subsystems.Robot.MineralPositions.SILVERGOLD;

        Robot.getArms().setArmsStates(Arms.ArmStates.GRABGOLDGOLD);
        Robot.getIntake().setWantedIntakeState(Intake.States.GRABBING);

        Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
        first = true;

        while (opModeIsActive()) {
            if(first && Robot.getLift().isCurrentWantedState()) {
                Robot.getLift().setLiftState(LiftStates.READY);
                first = false;
            }

            // Drivetrain (gamepad1)
            Robot.getDrive().setThrottleSteerPower(-gamepad1.left_stick_y, gamepad1.right_stick_x);

            // Lift (gamepad1/2)

            if(Robot.allowOperatorOfGrabbers) {
                // Bumper
                boolean leftClawChangeMode = gamepad2.left_bumper || gamepad1.left_bumper;
                boolean rightClawChangeMode = gamepad2.right_bumper || gamepad1.right_bumper;

                if (rightClawChangeMode) {
                    Robot.getArms().setLeftClawOpen(true);
                }

                if (leftClawChangeMode) {
                    Robot.getArms().setRightClawOpen(true);
                }
            }

            telemetry.addData("", Robot.allowOperatorOfGrabbers);

            if(Robot.liftCruiseControl){
                if (gamepad2.y) {
                    Robot.getArms().setArmsStates(Arms.ArmStates.GRABGOLDGOLD);
                    Robot.getLift().setLiftState(LiftStates.HOOKHEIGHT);
                    Robot.getIntake().setWantedIntakeState(Intake.States.ZEROING);

                } else {
                    Robot.getLift().setLiftPower(-gamepad2.right_stick_y);
                }
            }

            // End of Bumper

            //Intake (gamepad2)
            if (Robot.intakeCruiseControl) {
                Robot.getIntake().setIntakeExtenderPower(-gamepad2.left_stick_y);
                Robot.isAutoAlreadyDone = false;
            }

            if (gamepad2.a && !Robot.isAutoAlreadyDone){
                Robot.getIntake().setWantedIntakeState(Intake.States.INTAKING);
            }


            // auto/manuel mode changer (gamepad2)
            boolean changeMode = gamepad2.left_stick_button && gamepad2.right_stick_button;
            if (changeMode && changeMode != lastChangeMode) {
                autoMode = !autoMode;
            }
            lastChangeMode = changeMode;

            // auto/manuel lift arm logic (gamepad2)
            if (autoMode) {
                setBackground(Color.GREEN);

                if (gamepad2.b) {
                    Robot.setWantedSuperStructure(Robot.getIntake().updateMineralVote());
                    Robot.resetTracker();
                }
            } else {
                setBackground(Color.RED);

                if (gamepad2.b) {
                    wanted = Arms.ArmStates.SILVERSILVER;
                }

                Robot.getArms().setArmsStates(wanted);
            }

            double startTime = timer.milliseconds();

            Robot.update(matchTime);

            telemetry.addData("Robot Update dt", timer.milliseconds() - startTime);

            //telemetry
            telemetry.addData("dt", timer.milliseconds());

            if (gamepad2.dpad_up) {
                Robot.getIntake().updateMineralVote();
                telemetry.addData("Minerals in hopper", Robot.getIntake().getMineralPositions());
                telemetry.addData("Hsv Back", Arrays.toString(Robot.getIntake().revBackHopper.hsv()));
                telemetry.addData("Hsv Front", Arrays.toString(Robot.getIntake().revFrontHopper.hsv()));
            }



            //            Robot.outputToTelemetry(telemetry);
            telemetry.update();
            timer.reset();
        }

        finalAction();
    }
}
