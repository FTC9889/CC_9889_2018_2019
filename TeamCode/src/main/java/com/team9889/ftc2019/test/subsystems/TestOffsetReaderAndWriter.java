package com.team9889.ftc2019.test.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.team9889.lib.android.FileReader;
import com.team9889.lib.android.FileWriter;

/**
 * Created by joshua9889 on 1/24/2019.
 */

@Autonomous
@Disabled
public class TestOffsetReaderAndWriter extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        FileReader offsetReader;
        FileWriter offsetSaver;
        String fileName = "testReaderAndWriter.csv";

        offsetReader = new FileReader(fileName);
        offsetSaver = new FileWriter(fileName);

        offsetSaver.write("123.2");
        offsetSaver.close();


        telemetry.addData("", Double.parseDouble(offsetReader.read()) + 3);
        telemetry.update();
        sleep(5000);


        offsetReader.close();
    }
}
