package com.team9889.ftc2019;

import android.graphics.Color;

import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.subsystems.Camera;
import com.team9889.ftc2019.subsystems.Intake;
import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by MannoMation on 1/25/2019.
 */

@TeleOp
public class JudgingRoomTeleop extends Team9889Linear {

    private ElapsedTime phoneTimer = new ElapsedTime();
    private boolean phone = true;

    private boolean goldFound;      // Sound file present flags


    @Override
    public void runOpMode() throws InterruptedException {

        int goldSoundID   = hardwareMap.appContext.getResources().getIdentifier("gold",   "raw", hardwareMap.appContext.getPackageName());

        if (goldSoundID != 0)
            goldFound   = SoundPlayer.getInstance().preload(hardwareMap.appContext, goldSoundID);

        while (opModeIsActive()){

//            Auto Intake
            if(gamepad1.a)
                Robot.getIntake().setWantedIntakeState(Intake.IntakeStates.INTAKING);
            else if(Robot.getIntake().isIntakeOperatorControl())
                Robot.getIntake().setIntakeExtenderPower(-gamepad1.right_stick_y);

//            Lift Arms Logic
            if (gamepad2.b) {
                Robot.setWantedSuperStructure(Robot.getIntake().updateMineralVote());
                Robot.resetTracker();
            }

            if (gamepad2.y){
                if (phone = true){
                    phoneTimer.reset();
                    phone = false;
                }
                else if (phoneTimer.milliseconds() < 2000){
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTCENTER);
                }
                else if (phoneTimer.milliseconds() > 2000 && phoneTimer.milliseconds() < 4000){
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTRIGHT);
                }
                else if (phoneTimer.milliseconds() > 4000 && phoneTimer.milliseconds() < 6000){
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTLEFT);
                }
                else if (phoneTimer.milliseconds() > 6000 && phoneTimer.milliseconds() < 9000){
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.GREEN);
                }
                else if (phoneTimer.milliseconds() > 9000 && phoneTimer.milliseconds() < 12000){
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.RED);
                }
                else if (phoneTimer.milliseconds() > 6000){
                    Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                    setBackground(Color.TRANSPARENT);

                    if (goldFound) {
                        SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, goldSoundID);
                    }

                }
            }

        }
    }
}
