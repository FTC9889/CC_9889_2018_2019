package com.team9889.ftc2019;

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
    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void runOpMode() {
        DriverStation driverStation = new DriverStation(gamepad1, gamepad2);
        waitForStart(false);

        Robot.getArms().setArmsStates(Arms.ArmStates.GRABGOLDGOLD);
        Robot.getLift().setLiftState(LiftStates.READY);

        while (opModeIsActive()) {
            if(Robot.first && Robot.getLift().isCurrentWantedState()) {
                Robot.getIntake().setWantedIntakeState(Intake.IntakeStates.ZEROING);
                Robot.first = false;
            }

            // Drivetrain (gamepad1)
            Robot.getDrive().setThrottleSteerPower(driverStation.getThrottle(),
                    driverStation.getSteer());

            if(Robot.allowOperatorOfGrabbers) {
                if (driverStation.getReleaseLeftClaw())
                    Robot.getArms().setLeftClawOpen(true);


                if (driverStation.getReleaseRightClaw())
                    Robot.getArms().setRightClawOpen(true);
            }

            if(Robot.getLift().liftCruiseControl){
                if (gamepad2.y) {
                    Robot.getArms().setArmsStates(Arms.ArmStates.GRABGOLDGOLD);
                    Robot.getLift().setLiftState(LiftStates.HOOKHEIGHT);
                    Robot.getIntake().setWantedIntakeState(Intake.IntakeStates.ZEROING);
                } else {
                    Robot.getLift().setLiftPower(-gamepad2.right_stick_y);
                }
            }

            //Intake (gamepad2)
            if(gamepad1.a)
                Robot.getIntake().setWantedIntakeState(Intake.IntakeStates.INTAKING);
            else if(Robot.getIntake().isIntakeOperatorControl())
                Robot.getIntake().setIntakeExtenderPower(-gamepad1.right_stick_y);

            if (gamepad2.b) {
                Robot.setWantedSuperStructure(Robot.getIntake().updateMineralVote());
                Robot.resetTracker();
            }


            // Set Camera Position
            Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);

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

            if(timer.milliseconds() < 15)
                Robot.outputToTelemetry(telemetry);
            telemetry.update();
            timer.reset();
        }

        finalAction();
    }
}
