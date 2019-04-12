package com.team9889.ftc2019;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.states.LiftStates;
import com.team9889.ftc2019.subsystems.Camera;
import com.team9889.ftc2019.subsystems.Intake;

/**
 * Created by MannoMation on 1/14/2019.
 */

@TeleOp(name = "Teleop")
public class Teleop extends Team9889Linear {

    private ElapsedTime dt = new ElapsedTime();

    @Override
    public void runOpMode() {
        DriverStation driverStation = new DriverStation(gamepad1, gamepad2);
        waitForStart(false);

        Robot.getDumper().dumperTimer.reset();
        Robot.setScorerStates(com.team9889.ftc2019.subsystems.Robot.scorerStates.COLLECTING);

        while (opModeIsActive()) {
            dt.reset();

            // Drivetrain (gamepad1)
            Robot.getDrive().setThrottleSteerPower(driverStation.getThrottle(),
                    driverStation.getSteer());

            // Hanging Lift Controller
            if(Robot.getLift().liftOperatorControl){
                Robot.getHangingLift().setLiftPower(-gamepad2.right_stick_y);
            }

            // Scoring Lift Control
            if (Robot.getLift().liftOperatorControl){
                if(gamepad2.left_trigger < 0.1)
                    Robot.getLift().setLiftPower(-gamepad2.right_trigger);
                else if(Math.abs(gamepad2.right_trigger) < 0.1)
                    Robot.getLift().setLiftPower(gamepad2.left_trigger);
                else
                    Robot.getLift().setLiftPower(0);
            }

            //Intake
            boolean gamepad1IntakeForwards = gamepad1.right_trigger > 0.1;
            boolean gamepad1IntakeBackwards = gamepad1.left_trigger > 0.1 && !gamepad1IntakeForwards;
            boolean gamepad2Intake = !gamepad1IntakeForwards && !gamepad1IntakeBackwards;

            if(driverStation.getStartIntaking()) {
                Robot.getIntake().setWantedIntakeState(Intake.IntakeStates.INTAKING);
            } else if(Robot.getIntake().isIntakeOperatorControl()){
                if(gamepad2Intake)
                    Robot.getIntake().setIntakeExtenderPower(driverStation.getIntakeExtenderPower());
                else if (!gamepad1IntakeBackwards)
                    Robot.getIntake().setIntakeExtenderPower(-gamepad1.right_trigger);
                else if (!gamepad1IntakeForwards)
                    Robot.getIntake().setIntakeExtenderPower(gamepad1.left_trigger);
            }

            //Dumper
            if (gamepad2.y || gamepad1.y){
                Robot.setScorerStates(com.team9889.ftc2019.subsystems.Robot.scorerStates.SCORING);
                Robot.transitionDone = false;
            }else if (gamepad1.right_bumper && Robot.getLift().getCurrentState() == LiftStates.UP){
                Robot.getDumper().collectingTimer.reset();
                Robot.setScorerStates(com.team9889.ftc2019.subsystems.Robot.scorerStates.DUMP);
            }else if (gamepad2.b || gamepad1.b){
                Robot.setScorerStates(com.team9889.ftc2019.subsystems.Robot.scorerStates.COLLECTING);
            }

            // Intake Rotator
            if (gamepad2.right_bumper || gamepad1.dpad_up)
                Robot.getIntake().setIntakeRotatorState(Intake.RotatorStates.UP);
            else if (gamepad2.left_bumper || gamepad1.dpad_down)
                Robot.getIntake().setIntakeRotatorState(Intake.RotatorStates.DOWN);

            if (gamepad2.left_bumper || gamepad1.b){
                Robot.getIntake().setWantedIntakeState(Intake.IntakeStates.GRABBING);
            }

            if (gamepad1.dpad_left || gamepad2.dpad_left)
                Robot.getHangingLift().setHookPower(1);
            else if (gamepad1.dpad_right || gamepad2.dpad_right)
                Robot.getHangingLift().setHookPower(-1);
            else
                Robot.getHangingLift().setHookPower(0);

            // Set Camera Position
            Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);

            // Update All Robot Subsystems
            Robot.update(matchTime);

            if (Robot.getIntake().isIntakeOperatorControl() && !Robot.transitionDone)
                setBackground(Color.GREEN);
            else if (Robot.getLift().getCurrentState() == LiftStates.UP)
                setBackground(Color.BLUE);
            else
                setBackground(Color.BLACK);

            double dtMilli = dt.milliseconds();
            telemetry.addData("dt", dtMilli);
            telemetry.addData("Cycles Per Second", 1000 / dtMilli);
            Robot.outputToTelemetry(telemetry);
            telemetry.update();
            dt.reset();
        }

        finalAction();
    }
}
