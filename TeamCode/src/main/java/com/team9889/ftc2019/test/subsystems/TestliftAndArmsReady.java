package com.team9889.ftc2019.test.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.team9889.ftc2019.Team9889Linear;
import com.team9889.ftc2019.subsystems.Lift;

/**
 * Created by MannoMation on 12/14/2018.
 */

@TeleOp
public class TestliftAndArmsReady extends Team9889Linear {
    @Override
    public void runOpMode(){
        waitForStart(false);
        while (opModeIsActive()){
            Robot.getLift().setLiftState(Lift.LiftStates.READY);
//            Robot.getArms().setArmsStates(Arms.SuperstructureStates.GRAB);

            updateTelemetry();
        }

    }
}
