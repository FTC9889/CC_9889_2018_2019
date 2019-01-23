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
public class Teleop extends Team9889Linear {
    private Arms.ArmStates wanted = Arms.ArmStates.NULL;
    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() {
        DriverStation driverStation = new DriverStation(gamepad1, gamepad2);
        waitForStart(false);

        Robot.getIntake().isAutoIntakeDone = true;
        Robot.whichMineral = com.team9889.ftc2019.subsystems.Robot.MineralPositions.SILVERGOLD;

        Robot.getArms().setArmsStates(Arms.ArmStates.GRABGOLDGOLD);

        Robot.getIntake().setCurrentExtenderState(Intake.IntakeStates.NULL);

        Robot.getLift().setLiftState(LiftStates.READY);

        Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);

        while (opModeIsActive()) {
            if(Robot.first && Robot.getLift().isCurrentWantedState()) {
                Robot.getIntake().setWantedIntakeState(Intake.IntakeStates.GRABBING);
                Robot.first = false;
            }

            // Drivetrain (gamepad1)
            Robot.getDrive().setThrottleSteerPower(driverStation.getThrottle(),
                    driverStation.getSteer());

            // Lift (gamepad1/2)

            if(Robot.allowOperatorOfGrabbers) {
                if (driverStation.getReleaseLeftClaw())
                    Robot.getArms().setLeftClawOpen(true);


                if (driverStation.getReleaseRightClaw())
                    Robot.getArms().setRightClawOpen(true);
            }

            telemetry.addData("", Robot.allowOperatorOfGrabbers);

            if(Robot.getLift().liftCruiseControl){
                if (gamepad2.y) {
                    Robot.getArms().setArmsStates(Arms.ArmStates.GRABGOLDGOLD);
                    Robot.getLift().setLiftState(LiftStates.HOOKHEIGHT);
                    Robot.getIntake().setWantedIntakeState(Intake.IntakeStates.ZEROING);

                } else {
                    Robot.getLift().setLiftPower(-gamepad2.right_stick_y);
                }
            }

            // End of Bumper

            //Intake (gamepad2)
            if (Robot.intakeCruiseControl) {
                Robot.getIntake().setIntakeExtenderPower(driverStation.getIntakeExtenderPower());

                Robot.isAutoAlreadyDone = false;
//                if (Robot.getIntake().getIntakeExtenderPosition() < 5.5){
//                    Robot.getIntake().setCurrentExtenderState(Intake.IntakeStates.NULL);
//                }
            }

            if (driverStation.getStartIntaking() && !Robot.isAutoAlreadyDone){
                Robot.getIntake().setWantedIntakeState(Intake.IntakeStates.INTAKING);
            }

            // auto/manuel lift arm logic (gamepad2)
            if (driverStation.getAutomatedMode()) {
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
                telemetry.addData("Minerals in hopper",
                        Robot.getIntake().getMineralPositions());
                telemetry.addData("Hsv Back",
                        Arrays.toString(Robot.getIntake().revBackHopper.hsv()));
                telemetry.addData("Hsv Front",
                        Arrays.toString(Robot.getIntake().revFrontHopper.hsv()));
            }

//                        Robot.outputToTelemetry(telemetry);

//            telemetry.addData("Is Arms Lift Active", Robot.armsLiftActive);
//            telemetry.addData("Current Intake State", Robot.getIntake().getCurrentIntakeState());
//            telemetry.addData("Wanted Intake State", Robot.getIntake().getWantedIntakeState());
            telemetry.update();
            timer.reset();
        }

        finalAction();
    }
}
