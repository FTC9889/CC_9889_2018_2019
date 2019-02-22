package com.team9889.ftc2019.test.subsystems.intake;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.team9889.ftc2019.Team9889Linear;
import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by MannoMation on 2/22/2019.
 */

@TeleOp
public class TestHopperDumperPosition extends Team9889Linear {
    @Override
    public void runOpMode() throws InterruptedException {
       waitForStart(false);

       while (opModeIsActive()){
           Robot.getIntake().setHopperDumperPosition(gamepad1.right_stick_y);

           telemetry.addData("Intake Dumper Position", Robot.getIntake().getHopperDumperPosition());
           telemetry.update();
       }
    }
}
