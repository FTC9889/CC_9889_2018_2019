package com.team9889.ftc2019.test.subsystems.drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.Team9889Linear;
import com.team9889.lib.android.FileWriter;

/**
 * Created by MannoMation on 1/5/2019.
 */

@Autonomous
@Disabled
public class MeasureMaxSpeedAndAccel extends Team9889Linear {

    private double LastLPosition = 0;
    private double LastRPosition = 0;
    private double LastLSpeed = 0;
    private double LastRSpeed = 0;

    private ElapsedTime dt = new ElapsedTime();
    private ElapsedTime timer = new ElapsedTime();
    @Override
    public void runOpMode() {
        waitForStart(true);

        FileWriter fileWriter = new FileWriter("speedData.csv");

        Robot.getDrive().setLeftRightPower(1, 1);

        timer.reset();
        while (opModeIsActive() && timer.seconds()<3 && Robot.getDrive().getRightDistance() < 50){
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
