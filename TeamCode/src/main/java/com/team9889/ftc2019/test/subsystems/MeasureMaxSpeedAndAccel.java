package com.team9889.ftc2019.test.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.Team9889Linear;
import com.team9889.lib.android.FileWriter;

/**
 * Created by MannoMation on 1/5/2019.
 */

@Autonomous
public class MeasureMaxSpeedAndAccel extends Team9889Linear {

    double LastLPosition = 0;
    double LastRPosition = 0;
    double LastLSpeed = 0;
    double LastRSpeed = 0;

    ElapsedTime dt = new ElapsedTime();
    ElapsedTime timer = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart(true);

        FileWriter fileWriter = new FileWriter("speedData.csv");

        Robot.getDrive().setLeftRightPower(1, 1);

        timer.reset();
        while (opModeIsActive() && timer.seconds()<3){
            double leftPos = Robot.getDrive().getLeftTicks();
            double rightPos = Robot.getDrive().getRightTicks();
            double dr = dt.milliseconds();

            double leftSpeed = (leftPos - LastLPosition) / dr;
            double rightSpeed = (rightPos - LastRPosition) / dr;

            double leftAcc = (leftSpeed - LastLSpeed) / dr;
            double rightAcc = (rightSpeed - LastRSpeed) / dr;

            String output = String.valueOf(leftPos) + "," + String.valueOf(rightPos) + "," +
                    String .valueOf(leftSpeed) + "," + String.valueOf(rightSpeed) + "," +
                    String .valueOf(leftAcc) + "," + String .valueOf(rightAcc);
            fileWriter.write(output);

            LastRPosition = rightPos;
            LastLPosition = leftPos;
            LastLSpeed = leftSpeed;

            LastRSpeed = rightSpeed;

            dt.reset();
            sleep(1);
        }

        Robot.getDrive().setLeftRightPower(0,0);

        fileWriter.close();

    }
}
