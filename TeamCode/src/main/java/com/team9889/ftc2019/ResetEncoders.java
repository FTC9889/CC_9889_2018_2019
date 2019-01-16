package com.team9889;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.team9889.ftc2019.Team9889Linear;

/**
 * Created by joshua9889 on 12/15/2018.
 */
@TeleOp
public class ResetEncoders extends Team9889Linear{
    @Override
    public void runOpMode() {
        waitForStart(false);

        Robot.zeroSensors();
    }
}
