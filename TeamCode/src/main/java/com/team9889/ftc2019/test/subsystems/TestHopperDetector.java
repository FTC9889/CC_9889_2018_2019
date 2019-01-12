package com.team9889.ftc2019.test.subsystems;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.team9889.lib.detectors.HopperDetector;

/**
 * Created by MannoMation on 1/5/2019.
 */

@Autonomous
@Disabled
public class TestHopperDetector extends LinearOpMode {
    HopperDetector detector;
    @Override
    public void runOpMode() throws InterruptedException {

            telemetry.addData("Status", "DogeCV 2018.0 - Gold Align Example");
            detector = new HopperDetector();

            detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());

            detector.useDefaults();

            detector.enable();

            waitForStart();

            while (opModeIsActive()){
                idle();
            }

            detector.disable();
    }
}
