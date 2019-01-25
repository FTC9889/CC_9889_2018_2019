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

    @Override
    public void runOpMode() {
        boolean firstRun = true;
        DriverStation driverStation = new DriverStation(gamepad1, gamepad2);
        waitForStart(false);

        while (opModeIsActive()) {
            // Drivetrain (gamepad1)
            Robot.getDrive().setThrottleSteerPower(driverStation.getThrottle(),
                    driverStation.getSteer());

            if(Robot.getArms().isAllowOperatorOfGrabbers()) {
                if (driverStation.getReleaseLeftClaw())
                    Robot.getArms().setLeftClawOpen(true);

                if (driverStation.getReleaseRightClaw())
                    Robot.getArms().setRightClawOpen(true);
            }

            // Lift Controller
            if(firstRun && Robot.getLift().getCurrentState() == LiftStates.SCOREINGHEIGHT) {
                Robot.getLift().setLiftState(LiftStates.READY);
                firstRun = false;
            } else if(Robot.getLift().liftOperatorControl){
                if (gamepad2.y) {
                    Robot.getArms().setArmsStates(Arms.ArmStates.GRABGOLDGOLD);
                    Robot.getLift().setLiftState(LiftStates.HOOKHEIGHT);
                    Robot.getIntake().setWantedIntakeState(Intake.IntakeStates.ZEROING);
                } else {
                    Robot.getLift().setLiftPower(-gamepad2.right_stick_y);
                }
            }

            //Intake (gamepad2)
            if(driverStation.getStartIntaking())
                Robot.getIntake().setWantedIntakeState(Intake.IntakeStates.INTAKING);
            else if(Robot.getIntake().isIntakeOperatorControl())
                Robot.getIntake().setIntakeExtenderPower(driverStation.getIntakeExtenderPower());

            if (driverStation.getStartScoringSquence() && Robot.getIntake().getCurrentIntakeState() == Intake.IntakeStates.GRABBING) {
                Robot.setWantedSuperStructure(Robot.getIntake().updateMineralVote());
                Robot.resetTracker();
            }

            // Set Camera Position
            Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);


            if (Robot.getIntake().isIntakeOperatorControl())
                setBackground(Color.GREEN);
            else
                setBackground(Color.BLACK);

            // Update All Robot Subsystems
            Robot.update(matchTime);

            if(false) {
                Robot.outputToTelemetry(telemetry);
                telemetry.update();
            }
        }

        finalAction();
    }
}
