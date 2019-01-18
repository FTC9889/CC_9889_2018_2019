package com.team9889.ftc2019;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.team9889.ftc2019.states.ExtenderStates;
import com.team9889.ftc2019.states.LiftStates;
import com.team9889.ftc2019.states.SuperStructureStates;
import com.team9889.ftc2019.subsystems.Arms;
import com.team9889.ftc2019.subsystems.Camera;

/**
 * Created by MannoMation on 1/14/2019.
 */

@TeleOp
public class Teleop2 extends Team9889Linear {
    private boolean autoMode = true;
    private boolean clawLeftOpenMode = true;
    private boolean clawRightOpenMode = true;
    private boolean lastChangeMode, lastLeftClawChangeMode, lastRightClawChangeMode;

    private int colorCounter = 0;

    @Override
    public void runOpMode() {

        waitForStart(false);

        Robot.getIntake().isAutoIntakeDone = true;

        Robot.getSuperStructure().resetTracker();
        Robot.getSuperStructure().setWantedState(SuperStructureStates.WAIT_FOR_MINERALS);
        Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);

        while (opModeIsActive()) {

            // Drivetrain (gamepad1)
            Robot.getDrive().setThrottleSteerPower(-gamepad1.left_stick_y, gamepad1.right_stick_x);

            // Lift (gamepad1/2)

            // Bumper
            boolean leftClawChangeMode = gamepad2.left_bumper || gamepad1.left_bumper;
            if (leftClawChangeMode && leftClawChangeMode != lastLeftClawChangeMode) {
                clawLeftOpenMode = !clawLeftOpenMode;
            }

            lastLeftClawChangeMode = leftClawChangeMode;

            boolean rightClawChangeMode = gamepad2.right_bumper || gamepad1.right_bumper;
            if (rightClawChangeMode && rightClawChangeMode != lastRightClawChangeMode) {
                clawRightOpenMode = !clawRightOpenMode;
            }

            lastRightClawChangeMode = rightClawChangeMode;


            if (!clawRightOpenMode) {
                Robot.getArms().setLeftClawOpen(true);
            } else{
                Robot.getArms().setLeftClawOpen(false);
            }

            if (!clawLeftOpenMode) {
                Robot.getArms().setRightClawOpen(true);
            } else{
                Robot.getArms().setRightClawOpen(false);
            }

            if(Robot.liftCruiseControl){
                if (gamepad2.y) {
                    Robot.liftCruiseControl = false;
                    Robot.getLift().setLiftState(LiftStates.HOOKHEIGHT);
                } else {
                    Robot.getLift().setLiftPower(-gamepad2.right_stick_y);
                }
            } else {
                if(Robot.getLift().isCurrentWantedState() &&
                        Robot.getLift().getWantedState() == LiftStates.HOOKHEIGHT)
                    Robot.liftCruiseControl = true;
            }

            // End of Bumper

            //Intake (gamepad2)
            if (Robot.intakeCruiseControl) {
                Robot.getIntake().setWantedIntakeState(ExtenderStates.INTAKING);
                Robot.getIntake().setIntakeExtenderPower(-gamepad2.left_stick_y);
            }
            if (gamepad2.x) {
                Robot.getIntake().setWantedIntakeState(ExtenderStates.ZEROING);
            }else if (gamepad2.a){
                Robot.getIntake().autoIntake();
            }

            if (gamepad2.dpad_down){
                Robot.getIntake().operaterControl = true;
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
                    Robot.setWantedSuperStructure(Robot.getIntake().getMineralPositions());
                }

                Robot.update(matchTime);
            } else {
                setBackground(Color.RED);

                if (gamepad2.b) {
                    wanted = Arms.ArmStates.SILVERSILVER;
                }

                Robot.getArms().setArmsStates(wanted);
                Robot.getArms().update(matchTime);
                Robot.getLift().update(matchTime);
            }


            Robot.getIntake().update(matchTime);

            //telemetry
            this.updateTelemetry(telemetry);
        }

        finalAction();
    }
}
