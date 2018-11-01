package com.team9889.ftc2019;

import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;
import com.team9889.ftc2019.subsystems.Robot;

import org.firstinspires.ftc.robotcore.internal.camera.WebcamExample;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by joshua9889 on 3/28/2018.
 *
 * This class extends LinearOpMode and makes it
 * easier to make code for the robot and not copy
 * and pasting init code.
 */

public abstract class Team9889Linear extends LinearOpMode {

    // Robot Object
    protected Robot Robot = com.team9889.ftc2019.subsystems.Robot.getInstance();
    public GoldAlignDetector detector;

    // Match Timer
    protected ElapsedTime matchTime = new ElapsedTime();

    public void waitForStart(boolean autonomous) {
        Date currentData = new Date();
        SimpleDateFormat  format = new SimpleDateFormat("dd.M.yyyy hh:mm:ss");

        RobotLog.a("Robot Init Started at " + format.format(currentData));

        Robot.init(hardwareMap, autonomous);

        telemetry.setMsTransmissionInterval(autonomous ? 50:1000);

        if(autonomous){
            // Autonomous Init Loop code
            while(isInInitLoop()){
                telemetry.addData("Waiting for Start","");
                telemetry.update();
            }
        } else {
            // Teleop Init Loop code
            while(isInInitLoop()){
                telemetry.addData("Waiting for Start","");
                telemetry.update();
            }
        }

        matchTime.reset();
    }

    /**
     * Run this to update the Default Telemetry
     */
    protected void updateTelemetry(){
        if(telemetry.getMsTransmissionInterval()==50)
            telemetry.addData("Match Timer", this.matchTime.seconds()-30);
        else
            telemetry.addData("Match Timer", this.matchTime.seconds()-120);
        Robot.outputToTelemetry(telemetry);
        telemetry.update();
    }

    /**
     * Used to stop everything (Robot and OpMode).
     */
    protected void finalAction(){
        Robot.stop();
        requestOpModeStop();
    }

    /**
     * @return Is the robot waiting for start
     */
    private synchronized boolean isInInitLoop(){
        return !isStarted() && !isStopRequested();
    }
}
