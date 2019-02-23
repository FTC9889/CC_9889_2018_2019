package com.team9889.ftc2019;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.RobotLog;
import com.team9889.ftc2019.states.LiftStates;
import com.team9889.ftc2019.subsystems.Camera;
import com.team9889.ftc2019.subsystems.Intake;
import com.team9889.ftc2019.subsystems.Robot;

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

            // Lift Controller
            if(firstRun && Robot.getLift().getCurrentState() == LiftStates.SCOREINGHEIGHT) {
                Robot.setScorerStates(com.team9889.ftc2019.subsystems.Robot.scorerStates.COLLECTING);
                firstRun = false;
            } else if(Robot.getLift().liftOperatorControl){
                Robot.getLift().setLiftPower(-gamepad2.right_stick_y);
            }

            RobotLog.a(String.valueOf(Robot.getLift().liftOperatorControl));

            //Intake (gamepad2)
            if(driverStation.getStartIntaking())
                Robot.getIntake().setWantedIntakeState(Intake.IntakeStates.INTAKING);
            else if(Robot.getIntake().isIntakeOperatorControl())
                Robot.getIntake().setIntakeExtenderPower(driverStation.getIntakeExtenderPower());

            //Dumper
            if (gamepad2.b){
                Robot.setScorerStates(com.team9889.ftc2019.subsystems.Robot.scorerStates.SCORING);
            }else if (gamepad1.right_bumper && Robot.getLift().getCurrentState() == LiftStates.SCOREINGHEIGHT){
                Robot.getDumper().collectingTimer.reset();
                Robot.setScorerStates(com.team9889.ftc2019.subsystems.Robot.scorerStates.DUMP);
            }else if (gamepad2.y){
                Robot.setScorerStates(com.team9889.ftc2019.subsystems.Robot.scorerStates.COLLECTING);
            }

            if (gamepad2.right_bumper){
                Robot.getIntake().setIntakeRotatorState(Intake.RotatorStates.UP);
            }else if (gamepad2.left_bumper){
                Robot.getIntake().setIntakeRotatorState(Intake.RotatorStates.DOWN);
            }

            if (Robot.getIntake().isIntakeOperatorControl())
                setBackground(Color.GREEN);
            else
                setBackground(Color.BLACK);

            // Set Camera Position
            Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);

            // Update All Robot Subsystems
            Robot.update(matchTime);

            telemetry.addData("x", gamepad2.x);
            telemetry.addData("Driver Lift Control", Robot.getLift().liftOperatorControl);

            if (gamepad2.dpad_up) {
                Robot.outputToTelemetry(telemetry);
                telemetry.update();
            }
        }

        finalAction();
    }
}
