package com.team9889.ftc2019.test.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.team9889.ftc2019.Team9889Linear;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.auto.actions.Lift.Land2;
import com.team9889.ftc2019.states.LiftStates;
import com.team9889.ftc2019.subsystems.Intake;

/**
 * Created by MannoMation on 1/19/2019.
 */

@TeleOp
public class TestTeleOp extends Team9889Linear {

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart(false);

        while (opModeIsActive()){

            if (gamepad2.b) {
                Robot.setWantedSuperStructure(Robot.getIntake().updateMineralVote());
                Robot.resetTracker();
            }

            if (Robot.getLift().liftCruiseControl)
                Robot.getLift().setLiftPower(gamepad2.right_stick_y);

            if (Robot.intakeCruiseControl) {
                Robot.getIntake().setIntakeExtenderPower(-gamepad2.left_stick_y);
                Robot.isAutoAlreadyDone = false;
            }

//            if (Robot.getIntake().getIntakeExtenderPosition() < 5.5){
//                Robot.getIntake().setWantedIntakeState(Intake.IntakeStates.NULL);
//            }

            if (gamepad2.a && !Robot.isAutoAlreadyDone){
                Robot.getIntake().setWantedIntakeState(Intake.IntakeStates.INTAKING);
            }

            telemetry.addData("Distance Sensor", Robot.getLift().getDistanceSensorRange());
            telemetry.addData("Intake Cruise Control", Robot.intakeCruiseControl);
            telemetry.update();
        }


    }
}
