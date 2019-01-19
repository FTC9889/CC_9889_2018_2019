package com.team9889.ftc2019.test.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.team9889.ftc2019.Team9889Linear;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.auto.actions.Lift.Land2;
import com.team9889.ftc2019.states.LiftStates;

/**
 * Created by MannoMation on 1/19/2019.
 */

@TeleOp
public class TestTeleOp extends Team9889Linear {

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart(false);

        Robot.getLift().setLiftState(LiftStates.HANGING);


        while (opModeIsActive()){
            if (gamepad1.a){
                Land2 land2 = new Land2();
                land2.start();

                while (!land2.isFinished() && opModeIsActive())
                    land2.update();

                land2.done();
            }

            telemetry.addData("Distance Sensor", Robot.getLift().getDistanceSensorRange());
            telemetry.update();
        }
    }
}
