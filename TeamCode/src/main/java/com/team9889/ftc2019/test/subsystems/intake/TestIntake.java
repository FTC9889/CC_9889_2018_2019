package com.team9889.ftc2019.test.subsystems.intake;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.states.LiftStates;
import com.team9889.ftc2019.subsystems.Intake;
import com.team9889.ftc2019.subsystems.ScoringLift;

/**
 * Created by joshua9889 on 1/24/2019.
 */

@Autonomous
@Disabled
public class TestIntake extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Intake mIntake = new Intake();
        mIntake.init(hardwareMap, false);

        ScoringLift mLift = new ScoringLift();
        mLift.init(hardwareMap, false);

        ElapsedTime time = new ElapsedTime();
        waitForStart();

        boolean first = true;
        while (opModeIsActive()) {
            if (gamepad1.a)
                mIntake.setWantedIntakeState(Intake.IntakeStates.INTAKING);
            else if (mIntake.isIntakeOperatorControl())
                mIntake.setIntakeExtenderPower(-gamepad1.left_stick_y);


            if(first && mLift.getCurrentState() == LiftStates.UP) {
                mLift.setLiftState(LiftStates.READY);
                first = false;
            } else if(mLift.liftOperatorControl){
                mLift.setLiftPower(-gamepad1.right_stick_y);
            }

            mIntake.update(time);
            mLift.update(time);

            mLift.outputToTelemetry(telemetry);
            mIntake.outputToTelemetry(telemetry);
            telemetry.update();
        }

        mIntake.stop();

    }
}
