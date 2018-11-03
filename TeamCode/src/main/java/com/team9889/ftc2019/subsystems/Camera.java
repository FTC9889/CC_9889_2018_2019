package com.team9889.ftc2019.subsystems;

import com.disnodeteam.dogecv.CameraViewDisplay;
import com.disnodeteam.dogecv.DogeCV;
import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.team9889.ftc2019.Constants;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by MannoMation on 10/27/2018.
 */

public class Camera extends Subsystem{

    private Servo xAxis;
    private Servo yAxis;

    private GoldAlignDetector detector;

    @Override
    public void init(HardwareMap hardwareMap, boolean auto) {
        xAxis = hardwareMap.get(Servo.class, Constants.kCameraXAxis);
        yAxis = hardwareMap.get(Servo.class, Constants.kCameraYAxis);

        if (auto) {
            setXYAxisPosition(0, 0.1);

            detector = new GoldAlignDetector();
            detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance());
            detector.useDefaults();

            // Optional Tuning
            detector.alignSize = 150; // How wide (in pixels) is the range in which the gold object will be aligned. (Represented by green bars in the preview)
            detector.alignPosOffset = 0; // How far from center frame to offset this alignment zone.
            detector.downscale = 0.4; // How much to downscale the input frames

            detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
            //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
            detector.maxAreaScorer.weight = 0.005;

            detector.ratioScorer.weight = 5;
            detector.ratioScorer.perfectRatio = 1.0;

            detector.enable();
        }
    }

    @Override
    public void zeroSensors() {

    }

    public void setXYAxisPosition(double xPos, double yPos) {
        xAxis.setPosition(xPos);
        yAxis.setPosition(yPos);
    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("X Axis", getXAxis());
        telemetry.addData("Y Axis", getYAxis());
    }

    @Override
    public void test(Telemetry telemetry) {

    }

    public double getXAxis(){
        return (xAxis.getPosition());
    }

    public double getYAxis(){
        return (yAxis.getPosition());
    }

    public boolean isGoldInfront() {
        return (detector.getAligned());
    }

    public double getGoldPosition(){
        return detector.getXPosition();
    }

    @Override
    public void stop() {
        detector.disable();
    }
}
