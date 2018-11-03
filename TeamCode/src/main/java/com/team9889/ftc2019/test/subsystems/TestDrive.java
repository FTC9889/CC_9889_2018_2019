package com.team9889.ftc2019.test.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.subsystems.Drive;
import com.team9889.lib.android.FileWriter;

/**
 * Created by joshua9889 on 3/29/2018.
 * Test Drivetrain OpMode
 */

@Autonomous
@Disabled
public class TestDrive extends LinearOpMode {
    private FileWriter logger = new FileWriter("FindVA.csv");

    private Drive mDrive = new Drive();

    @Override
    public void runOpMode() {

        mDrive.init(hardwareMap, true);
        mDrive.resetEncoders();

        waitForStart();
        test1();
    }

    private void test2() {
        while (opModeIsActive())
            mDrive.setThrottleSteerPower(0.012, 0);
    }

    private void test1(){
        ElapsedTime time = new ElapsedTime();
        ElapsedTime dt = new ElapsedTime();
        double lastPosition = 0;
        double speed = 0;
        double lastSpeed = 0;

        double testingSpeed = 0.0;
        double step = 0.001;

        while (opModeIsActive() && speed < 4000){
            testingSpeed += step;

            double currentPos = (mDrive.getLeftTicks() + mDrive.getRightTicks()) / 2;
            speed = (currentPos-lastPosition)/dt.seconds();

            mDrive.setThrottleSteerPower(testingSpeed, 0);
            sleep(100);

            lastPosition = currentPos;
            dt.reset();
            telemetry.addData("testing speed", testingSpeed);
            telemetry.addData("current speed", speed);
            telemetry.update();
        }

        sleep(1000);

        while (opModeIsActive() && speed < 4000){
            testingSpeed += step;

            double currentPos = (mDrive.getLeftTicks() + mDrive.getRightTicks()) / 2;
            speed = (currentPos-lastPosition)/dt.seconds();

            mDrive.setThrottleSteerPower(testingSpeed, 0);
            sleep(100);

            lastPosition = currentPos;
            lastSpeed = speed;
            dt.reset();
            telemetry.addData("testing speed", testingSpeed);
            telemetry.addData("current speed", speed);
            telemetry.update();
        }



        sleep(10000);


        mDrive.setLeftRightPower(1, 1);

        // Go for 3 seconds
        while (opModeIsActive() && time.seconds()<3){
            double currentPos = (mDrive.getLeftTicks() + mDrive.getRightTicks()) / 2;

            speed = (currentPos-lastPosition)/dt.seconds();

            double accl = (speed-lastSpeed)/dt.seconds();

            // Make sure the file is not 60k large with no data
            if(speed != 0 && accl != 0)
                logger.write(time.seconds() + "," + speed + "," + accl);

            lastPosition = currentPos;
            lastSpeed = speed;
            dt.reset();
        }

        logger.write(testingSpeed);

        // Close the logger
        logger.close();
    }
}
