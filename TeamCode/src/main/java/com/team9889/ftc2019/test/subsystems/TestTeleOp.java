package com.team9889.ftc2019.test.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.team9889.ftc2019.Team9889Linear;
import com.team9889.ftc2019.subsystems.Intake;

/**
 * Created by MannoMation on 1/19/2019.
 */

@TeleOp
@Disabled
public class TestTeleOp extends Team9889Linear {

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart(false);

        while (opModeIsActive()){

            if (Robot.getLift().liftOperatorControl)
                Robot.getLift().setLiftPower(gamepad2.right_stick_y);

            if (Robot.getIntake().isIntakeOperatorControl()) {
                Robot.getIntake().setIntakeExtenderPower(-gamepad2.left_stick_y);
            }

//            if (Robot.getIntake().getIntakeExtenderPosition() < 5.5){
//                Robot.getIntake().setWantedIntakeState(Intake.IntakeStates.NULL);
//            }

            if (gamepad2.a){
                Robot.getIntake().setWantedIntakeState(Intake.IntakeStates.INTAKING);
            }

            telemetry.addData("Distance Sensor", Robot.getLift().getDistanceSensorRange());
            telemetry.addData("Intake Cruise Control", Robot.getIntake().isIntakeOperatorControl());
            telemetry.update();
        }


    }
}
