package com.team9889.test.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.team9889.subsystems.Drive;

/**
 * Created by joshua9889 on 3/29/2018.
 * Test Drivetrain OpMode
 */

@TeleOp
@Disabled
public class TestDrive extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Drive mDrive = new Drive();
        mDrive.init(hardwareMap, true);

        waitForStart();

        // Preform Drive test code here:
    }
}
