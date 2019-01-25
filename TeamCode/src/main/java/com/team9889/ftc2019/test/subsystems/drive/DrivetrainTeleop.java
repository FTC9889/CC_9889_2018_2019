package com.team9889.ftc2019.test.subsystems.drive;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.team9889.ftc2019.DriverStation;
import com.team9889.ftc2019.Team9889Linear;

/**
 * Created by MannoMation on 12/14/2018.
 */

@Autonomous(group = "Tests")
public class DrivetrainTeleop extends Team9889Linear {
    private DriverStation driverStation;

    @Override
    public void runOpMode() {
        driverStation = new DriverStation(gamepad1, gamepad2);
        waitForStart(false);

        while (opModeIsActive()){

            if(Math.abs(gamepad2.left_stick_y) < 0.02 && Math.abs(gamepad2.right_stick_y) < 0.02) {
                Robot.getDrive().setThrottleSteerPower(driverStation.getThrottle(),
                        driverStation.getSteer());
            } else {
                Robot.getDrive().setLeftRightPower(-gamepad2.left_stick_y, -gamepad2.right_stick_y);
            }

            updateTelemetry();
        }

    }
}
