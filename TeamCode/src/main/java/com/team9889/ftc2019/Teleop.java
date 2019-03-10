package com.team9889.ftc2019;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.states.LiftStates;
import com.team9889.ftc2019.subsystems.Camera;
import com.team9889.ftc2019.subsystems.Intake;
import com.team9889.lib.android.FileWriter;

/**
 * Created by MannoMation on 1/14/2019.
 */

@TeleOp(name = "Teleop")
public class Teleop extends Team9889Linear {

    private ElapsedTime timer = new ElapsedTime();
    private boolean shaker = false;
    private boolean shakerFirst = false;

    private ElapsedTime cycleTime = new ElapsedTime();
    private FileWriter writer;
    private boolean first = true;

    private boolean gamepad2Intake = false;
    private boolean gamepad1IntakeFowards = false;
    private boolean gamepad1IntakeBackwards = false;

    @Override
    public void runOpMode() {
        boolean firstRun = true;
        DriverStation driverStation = new DriverStation(gamepad1, gamepad2);
        writer = new FileWriter("CycleTime.csv");
        cycleTime.reset();
        waitForStart(false);

        while (opModeIsActive()) {
            // Drivetrain (gamepad1)
            Robot.getDrive().setThrottleSteerPower(driverStation.getThrottle(),
                    driverStation.getSteer());

            // Lift Controller
            if(firstRun && Robot.getLift().getCurrentState() == LiftStates.UP) {
                Robot.getDumper().dumperTimer.reset();
                Robot.setScorerStates(com.team9889.ftc2019.subsystems.Robot.scorerStates.COLLECTING);
                firstRun = false;
            } else if(Robot.getLift().liftOperatorControl){
                Robot.getLift().setLiftPower(-gamepad2.right_stick_y);
            }

            //Intake (gamepad2)
            if(driverStation.getStartIntaking()) {
                Robot.getIntake().setWantedIntakeState(Intake.IntakeStates.INTAKING);
                Robot.getIntake().setIntakeRotatorState(Intake.RotatorStates.DOWN);
            }
            else if(Robot.getIntake().isIntakeOperatorControl() && !gamepad1IntakeFowards && !gamepad1IntakeBackwards)
                Robot.getIntake().setIntakeExtenderPower(driverStation.getIntakeExtenderPower());
            else if (Robot.getIntake().isIntakeOperatorControl() && !gamepad1IntakeBackwards)
                Robot.getIntake().setIntakeExtenderPower(-gamepad1.right_trigger);
            else if (Robot.getIntake().isIntakeOperatorControl() && !gamepad1IntakeFowards)
                Robot.getIntake().setIntakeExtenderPower(gamepad1.left_trigger);

            if (gamepad1.right_trigger > .1) {
                gamepad1IntakeFowards = true;
                gamepad1IntakeBackwards = false;
                gamepad2Intake = false;
            }
            else if (gamepad1.left_trigger > .1) {
                gamepad1IntakeBackwards = true;
                gamepad1IntakeFowards = false;
                gamepad2Intake = false;
            }
            else if (gamepad2.left_stick_y > .1 || gamepad2.left_stick_y < -.1) {
                gamepad2Intake = true;
                gamepad1IntakeFowards = false;
                gamepad1IntakeBackwards = false;
            }

            telemetry.addData("right", gamepad1.right_trigger);
            telemetry.addData("left", gamepad1.left_trigger);
            telemetry.addData("2", gamepad2.left_stick_y);
            telemetry.addData("fowards", gamepad1IntakeFowards);
            telemetry.addData("back", gamepad1IntakeBackwards);
            telemetry.addData("2", gamepad2Intake);
            telemetry.update();


            //Dumper
            if (gamepad2.y && Robot.getIntake().currentHopperDumperState != Intake.HopperDumperStates.PUSHING || gamepad1.y && Robot.getIntake().currentHopperDumperState != Intake.HopperDumperStates.PUSHING){
                Robot.setScorerStates(com.team9889.ftc2019.subsystems.Robot.scorerStates.SCORING);
                first = true;
                Robot.transitionDone = false;
            }else if (gamepad1.right_bumper && Robot.getLift().getCurrentState() == LiftStates.SCOREINGHEIGHT){
                Robot.getDumper().collectingTimer.reset();
                Robot.setScorerStates(com.team9889.ftc2019.subsystems.Robot.scorerStates.DUMP);

                if(first) {
                    writer.write(String.valueOf(cycleTime.seconds()));
                    cycleTime.reset();
                    first = false;
                }
            }else if (gamepad2.b){
                Robot.setScorerStates(com.team9889.ftc2019.subsystems.Robot.scorerStates.COLLECTING);
                first = true;
            }else if (gamepad1.left_bumper){
                shaker = true;
                shakerFirst = true;
                timer.reset();
                first = true;
            }

            if (gamepad2.right_bumper || gamepad1.dpad_up){
                Robot.getIntake().setIntakeRotatorState(Intake.RotatorStates.UP);
            }else if (gamepad2.left_bumper || gamepad1.dpad_down){
                Robot.getIntake().setIntakeRotatorState(Intake.RotatorStates.DOWN);
            }

            if (Robot.transitionDone){
                setBackground(Color.YELLOW);
                setBackground(Color.WHITE);
                setBackground(Color.YELLOW);
                setBackground(Color.WHITE);
                setBackground(Color.YELLOW);
                setBackground(Color.WHITE);
                setBackground(Color.YELLOW);
                setBackground(Color.WHITE);
            }else if (Robot.getIntake().isIntakeOperatorControl() && !Robot.transitionDone)
                setBackground(Color.GREEN);
            else
                setBackground(Color.BLACK);




            if (shaker == true) {
                if(shakerFirst){
                    Robot.setScorerStates(com.team9889.ftc2019.subsystems.Robot.scorerStates.SCORING);
                    shakerFirst = false;
                }else if (timer.milliseconds() > 200){
                    Robot.getDumper().collectingTimer.reset();
                    Robot.setScorerStates(com.team9889.ftc2019.subsystems.Robot.scorerStates.DUMP);
                    shakerFirst = true;
                    shaker = false;
                }
            }

            if (gamepad2.x){
                Robot.overrideIntake = true;
            }

            if(gamepad2.right_trigger > 0.3)
                Robot.getIntake().setIntakeHardStopState(Intake.IntakeHardStop.DOWN);

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
        writer.close();
    }
}
