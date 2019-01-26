package com.team9889.ftc2019;

import android.graphics.Color;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.states.LiftStates;
import com.team9889.ftc2019.subsystems.Arms;
import com.team9889.ftc2019.subsystems.Camera;
import com.team9889.ftc2019.subsystems.Intake;
import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by MannoMation on 1/25/2019.
 */

@TeleOp
public class JudgingRoomTeleop extends Team9889Linear {

    private int gradient = 0;
    private boolean gradientLimit = false;

    private ElapsedTime phoneTimer = new ElapsedTime();
    private boolean phone = true;
    private boolean wasYPressed = false;

    private boolean helloFound;      // Sound file present flags

    @Override
    public void runOpMode() throws InterruptedException {

        DriverStation driverStation = new DriverStation(gamepad1, gamepad2);
        waitForStart(false);

        int helloSoundID   = hardwareMap.appContext.getResources().getIdentifier("hello",   "raw", hardwareMap.appContext.getPackageName());

        if (helloSoundID != 0)
            helloFound   = SoundPlayer.getInstance().preload(hardwareMap.appContext, helloSoundID);

        Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTCENTER);

        while (opModeIsActive()){

//            Auto Intake
            if(gamepad1.a)
                Robot.getIntake().setWantedIntakeState(Intake.IntakeStates.INTAKING);
            else if(Robot.getIntake().isIntakeOperatorControl())
                Robot.getIntake().setIntakeExtenderPower(-gamepad1.right_stick_y);

//            Lift Arms Logic
            if (gamepad2.y) {
                Robot.setWantedSuperStructure(Robot.getIntake().updateMineralVote());
                Robot.resetTracker();
            }

            else if(Robot.getIntake().isIntakeOperatorControl())
                Robot.getIntake().setIntakeExtenderPower(driverStation.getIntakeExtenderPower());

            if(Robot.getLift().liftOperatorControl){
                    Robot.getLift().setLiftPower(-gamepad2.right_stick_y);
            }

            if (gamepad2.x){
                phoneTimer.reset();
            }

            if (gamepad2.x || wasYPressed){

                wasYPressed = true;

                if (phoneTimer.milliseconds() < 2000){
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTCENTER);
                }
                else if (phoneTimer.milliseconds() > 2000 && phoneTimer.milliseconds() < 4000){
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTRIGHT);
                }
                else if (phoneTimer.milliseconds() > 4000 && phoneTimer.milliseconds() < 6000){
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                }

//                Disco Phone Start
                else if (phoneTimer.milliseconds() > 6000 && phoneTimer.milliseconds() < 6100){
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.GREEN);
                }
                else if (phoneTimer.milliseconds() > 6100 && phoneTimer.milliseconds() < 6200){
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.RED);
                }
                else if (phoneTimer.milliseconds() > 6200 && phoneTimer.milliseconds() < 6300) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.BLUE);
                }
                else if (phoneTimer.milliseconds() > 6300 && phoneTimer.milliseconds() < 6400) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.YELLOW);
                }
                else if (phoneTimer.milliseconds() > 6400 && phoneTimer.milliseconds() < 6500) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.WHITE);
                }
                else if (phoneTimer.milliseconds() > 6500 && phoneTimer.milliseconds() < 6600) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.BLACK);
                }
                else if (phoneTimer.milliseconds() > 6600 && phoneTimer.milliseconds() < 6700) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.MAGENTA);
                }
                else if (phoneTimer.milliseconds() > 6700 && phoneTimer.milliseconds() < 6800) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.CYAN);
                }
                else if (phoneTimer.milliseconds() > 6800 && phoneTimer.milliseconds() < 6900) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.rgb(155, 0, 240));
                }
                else if (phoneTimer.milliseconds() > 6900 && phoneTimer.milliseconds() < 7000) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.rgb(0, 255, 240));
                }
                else if (phoneTimer.milliseconds() > 7000 && phoneTimer.milliseconds() < 7100){
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.GREEN);
                }
                else if (phoneTimer.milliseconds() > 7100 && phoneTimer.milliseconds() < 7200){
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.RED);
                }
                else if (phoneTimer.milliseconds() > 7200 && phoneTimer.milliseconds() < 7300) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.BLUE);
                }
                else if (phoneTimer.milliseconds() > 7300 && phoneTimer.milliseconds() < 7400) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.YELLOW);
                }
                else if (phoneTimer.milliseconds() > 7400 && phoneTimer.milliseconds() < 7500) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.WHITE);
                }
                else if (phoneTimer.milliseconds() > 7500 && phoneTimer.milliseconds() < 7600) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.BLACK);
                }
                else if (phoneTimer.milliseconds() > 7600 && phoneTimer.milliseconds() < 7700) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.MAGENTA);
                }
                else if (phoneTimer.milliseconds() > 7700 && phoneTimer.milliseconds() < 7800) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.CYAN);
                }
                else if (phoneTimer.milliseconds() > 7800 && phoneTimer.milliseconds() < 7900) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.rgb(155, 0, 240));
                }
                else if (phoneTimer.milliseconds() > 7900 && phoneTimer.milliseconds() < 8000) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.rgb(0, 255, 240));
                }
                else if (phoneTimer.milliseconds() > 8000 && phoneTimer.milliseconds() < 8100){
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.GREEN);
                }
                else if (phoneTimer.milliseconds() > 8100 && phoneTimer.milliseconds() < 8200){
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.RED);
                }
                else if (phoneTimer.milliseconds() > 8200 && phoneTimer.milliseconds() < 8300) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.BLUE);
                }
                else if (phoneTimer.milliseconds() > 8300 && phoneTimer.milliseconds() < 8400) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.YELLOW);
                }
                else if (phoneTimer.milliseconds() > 8400 && phoneTimer.milliseconds() < 8500) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.WHITE);
                }
                else if (phoneTimer.milliseconds() > 8500 && phoneTimer.milliseconds() < 8600) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.BLACK);
                }
                else if (phoneTimer.milliseconds() > 8600 && phoneTimer.milliseconds() < 8700) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.MAGENTA);
                }
                else if (phoneTimer.milliseconds() > 8700 && phoneTimer.milliseconds() < 8800) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.CYAN);
                }
                else if (phoneTimer.milliseconds() > 8800 && phoneTimer.milliseconds() < 8900) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.rgb(155, 0, 240));
                }
                else if (phoneTimer.milliseconds() > 8900 && phoneTimer.milliseconds() < 9000) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.rgb(0, 255, 240));
                }
                else if (phoneTimer.milliseconds() > 9000 && phoneTimer.milliseconds() < 9100){
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.GREEN);
                }
                else if (phoneTimer.milliseconds() > 9100 && phoneTimer.milliseconds() < 9200){
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.RED);
                }
                else if (phoneTimer.milliseconds() > 9200 && phoneTimer.milliseconds() < 9300) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.BLUE);
                }
                else if (phoneTimer.milliseconds() > 9300 && phoneTimer.milliseconds() < 9400) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.YELLOW);
                }
                else if (phoneTimer.milliseconds() > 9400 && phoneTimer.milliseconds() < 9500) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.WHITE);
                }
                else if (phoneTimer.milliseconds() > 9500 && phoneTimer.milliseconds() < 9600) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.BLACK);
                }
                else if (phoneTimer.milliseconds() > 9600 && phoneTimer.milliseconds() < 9700) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.MAGENTA);
                }
                else if (phoneTimer.milliseconds() > 9700 && phoneTimer.milliseconds() < 9800) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.CYAN);
                }
                else if (phoneTimer.milliseconds() > 9800 && phoneTimer.milliseconds() < 9900) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.rgb(155, 0, 240));
                }
                else if (phoneTimer.milliseconds() > 9900 && phoneTimer.milliseconds() < 10000) {
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.rgb(0, 255, 240));
                }
//                Disco Phone End

                else if (phoneTimer.milliseconds() > 10000){
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.TRANSPARENT);

                    if (helloFound) {
                        SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, helloSoundID);
                        wasYPressed = false;
                    }

                }
            }

            if (gamepad2.dpad_down) {
                setBackground(Color.rgb(gradient, 0, 0));
                if (gradientLimit) {
                    gradient -= 10;
                } else {
                    gradient += 10;
                }
                if (gradient == 250){
                    gradientLimit = true;
                }else if (gradient == 0){
                    gradientLimit = false;
                }
            }

            Robot.update(matchTime);

            telemetry.addData("Is hello found?", helloFound);
            telemetry.addData("phone", phone);
            telemetry.addData("timer", phoneTimer);
            telemetry.addData("grandient", gradient);
            telemetry.update();

        }
    }
}
