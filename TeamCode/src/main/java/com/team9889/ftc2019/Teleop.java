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

    private ElapsedTime timer = new ElapsedTime();
    private ElapsedTime dt = new ElapsedTime();

    @Override
    public void runOpMode() {
        boolean firstRun = true;
        DriverStation driverStation = new DriverStation(gamepad1, gamepad2);
        waitForStart(false);

        while (opModeIsActive()) {
            dt.reset();

            // Drivetrain (gamepad1)
            Robot.getDrive().setThrottleSteerPower(driverStation.getThrottle(),
                    driverStation.getSteer());

            // ScoringLift Controller
            if(firstRun) {
                Robot.getDumper().dumperTimer.reset();
                Robot.setScorerStates(com.team9889.ftc2019.subsystems.Robot.scorerStates.COLLECTING);
            } else if(Robot.getLift().liftOperatorControl){
                Robot.getHangingLift().setLiftPower(-gamepad2.right_stick_y);
            }

            //Intake
            boolean gamepad1IntakeFowards = gamepad1.right_trigger > 0.1;
            boolean gamepad1IntakeBackwards = gamepad1.left_trigger > 0.1 && !gamepad1IntakeFowards;
            boolean gamepad2Intake = !gamepad1IntakeFowards && !gamepad1IntakeBackwards;

            if(driverStation.getStartIntaking()) {
                Robot.getIntake().setWantedIntakeState(Intake.IntakeStates.INTAKING);
                Robot.getIntake().setIntakeRotatorState(Intake.RotatorStates.DOWN);

            } else if(Robot.getIntake().isIntakeOperatorControl()){

                if(gamepad2Intake)
                    Robot.getIntake().setIntakeExtenderPower(driverStation.getIntakeExtenderPower());
                else if (!gamepad1IntakeBackwards)
                    Robot.getIntake().setIntakeExtenderPower(-gamepad1.right_trigger);
                else if (!gamepad1IntakeFowards)
                    Robot.getIntake().setIntakeExtenderPower(gamepad1.left_trigger);
            }

            //Dumper
            if (gamepad2.y){
                Robot.setScorerStates(com.team9889.ftc2019.subsystems.Robot.scorerStates.SCORING);
                Robot.transitionDone = false;
            }else if (gamepad1.right_bumper && Robot.getLift().getCurrentState() == LiftStates.UP){
                Robot.getDumper().collectingTimer.reset();
                Robot.setScorerStates(com.team9889.ftc2019.subsystems.Robot.scorerStates.DUMP);


            }else if (gamepad2.b){
                Robot.setScorerStates(com.team9889.ftc2019.subsystems.Robot.scorerStates.COLLECTING);
            }else if (gamepad1.left_bumper){
                timer.reset();
            }

            // Intake Rotator
            if (gamepad2.right_bumper || gamepad1.dpad_up)
                Robot.getIntake().setIntakeRotatorState(Intake.RotatorStates.UP);
            else if (gamepad2.left_bumper || gamepad1.dpad_down)
                Robot.getIntake().setIntakeRotatorState(Intake.RotatorStates.DOWN);

            if (gamepad2.left_bumper && Robot.getLift().getHeight() < 50){
                Robot.stopIntake = true;
            }

            // Set Camera Position
            Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);

            // Update All Robot Subsystems
            Robot.update(matchTime);

            if (Robot.getLift().liftOperatorControl){
                if(gamepad2.left_trigger < 0.1)
                    Robot.getLift().setLiftPower(-gamepad2.right_trigger);
                else if(Math.abs(gamepad2.right_trigger) < 0.1)
                    Robot.getLift().setLiftPower(gamepad2.left_trigger);
                else
                    Robot.getLift().setLiftPower(0);

            }

            firstRun = false;

            if (Robot.getIntake().isIntakeOperatorControl() && !Robot.transitionDone)
                setBackground(Color.GREEN);
            else if (Robot.getDrive().isRobotInScoringPosition())
                setBackground(Color.BLUE);
            else
                setBackground(Color.BLACK);

            double dtMilli = dt.milliseconds();
            telemetry.addData("dt", dtMilli);
            telemetry.addData("Cycles Per Second", 1000 / dtMilli);
            Robot.outputToTelemetry(telemetry);
            telemetry.update();
            dt.reset();

            Robot.stopIntake = false;
        }

        finalAction();
    }
}
