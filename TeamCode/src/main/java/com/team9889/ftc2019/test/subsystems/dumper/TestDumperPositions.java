package com.team9889.ftc2019.test.subsystems.dumper;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.Team9889Linear;
import com.team9889.ftc2019.states.LiftStates;
import com.team9889.ftc2019.subsystems.Camera;
import com.team9889.ftc2019.subsystems.Dumper;
import com.team9889.ftc2019.subsystems.Intake;

/**
 * Created by MannoMation on 2/15/2019.
 */

@TeleOp
@Disabled
public class TestDumperPositions extends Team9889Linear {

    private ElapsedTime loopTimer = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart(false);

        Robot.getLift().setLiftState(LiftStates.UP);

        while (opModeIsActive()){
            loopTimer.reset();

            Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTCENTER);

            if (gamepad1.a)
                Robot.setScorerStates(com.team9889.ftc2019.subsystems.Robot.scorerStates.COLLECTING);
            else if (gamepad1.b)
                Robot.setScorerStates(com.team9889.ftc2019.subsystems.Robot.scorerStates.SCORING);
            else if (gamepad1.y)
                Robot.setScorerStates(com.team9889.ftc2019.subsystems.Robot.scorerStates.STORED);
            else if (gamepad1.x){
                Robot.setScorerStates(com.team9889.ftc2019.subsystems.Robot.scorerStates.DUMP);
                Robot.getDumper().collectingTimer.reset();
            }

            Robot.getIntake().setIntakeExtenderPower(-gamepad1.right_stick_y);

            if (gamepad1.right_bumper){
                Robot.getIntake().setWantedIntakeState(Intake.IntakeStates.INTAKING);
            }else if (gamepad1.left_bumper){
            }else if (gamepad1.dpad_down){
                Robot.getIntake().transitionTimer.reset();
                Robot.getIntake().setWantedIntakeState(Intake.IntakeStates.TRANSITION);
            }

            Robot.update(matchTime);
            Robot.outputToTelemetry(telemetry);
            telemetry.addData("Loop Timer", loopTimer.milliseconds());
            telemetry.update();
        }
    }
}
