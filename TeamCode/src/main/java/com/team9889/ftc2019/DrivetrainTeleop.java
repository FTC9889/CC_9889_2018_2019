package com.team9889.ftc2019;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by MannoMation on 12/14/2018.
 */

@TeleOp
public class DrivetrainTeleop extends Team9889Linear{
    DriverStation driverStation;

    private boolean directLiftControl = false;

    @Override
    public void runOpMode() {
        driverStation = new DriverStation(gamepad1, gamepad2);
        waitForStart(false);

        while (opModeIsActive()){
            Robot.getDrive().setThrottleSteerPower(driverStation.getThrottle(),
                    driverStation.getSteer());
        }

    }
}
