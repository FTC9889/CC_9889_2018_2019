package com.team9889.ftc2019.test.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.team9889.lib.hardware.ModernRoboticsUltrasonic;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by joshua9889 on 10/5/2018.
 */

@Autonomous
public class TestMRPing extends LinearOpMode{
    @Override
    public void runOpMode() {
        ModernRoboticsUltrasonic ping = new ModernRoboticsUltrasonic("ping", hardwareMap);

        while (!isStarted()){
            telemetry.addData("Ping", ping.getDistance(DistanceUnit.INCH));
            telemetry.update();
        }
    }
}
