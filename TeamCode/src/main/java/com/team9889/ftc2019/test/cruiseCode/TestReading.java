package com.team9889.ftc2019.test.cruiseCode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.team9889.lib.android.FileReader;

@Autonomous
@Disabled
public class TestReading extends LinearOpMode {
    @Override
    public void runOpMode() {
        FileReader reader = new FileReader("log.txt");
        sleep(1000);
        telemetry.addData("", reader.read());
        telemetry.update();
        sleep(1000);
        reader.close();
    }
}
