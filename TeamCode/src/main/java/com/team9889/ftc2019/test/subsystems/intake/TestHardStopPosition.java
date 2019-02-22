package com.team9889.ftc2019.test.subsystems.intake;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.team9889.ftc2019.Team9889Linear;

/**
 * Created by MannoMation on 2/22/2019.
 */

@TeleOp
public class TestHardStopPosition extends Team9889Linear {
    @Override
    public void runOpMode() throws InterruptedException {
       waitForStart(false);

       while (opModeIsActive()){
           Robot.getIntake().setIntakeHardStopPosition(Math.abs(gamepad1.right_stick_y));

           telemetry.addData("Hard Stop Position", Robot.getIntake().getIntakeHardStopPosition());
           telemetry.update();
       }
    }
}
