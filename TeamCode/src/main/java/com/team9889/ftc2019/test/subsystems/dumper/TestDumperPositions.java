package com.team9889.ftc2019.test.subsystems.dumper;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.Team9889Linear;
import com.team9889.ftc2019.states.LiftStates;
import com.team9889.ftc2019.subsystems.Camera;
import com.team9889.ftc2019.subsystems.Dumper;
import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by MannoMation on 2/15/2019.
 */

@TeleOp
public class TestDumperPositions extends Team9889Linear {

    private ElapsedTime loopTimer = new ElapsedTime();

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart(false);

        Robot.getLift().setLiftState(LiftStates.SCOREINGHEIGHT);
        Robot.getDumper().setDumperStates(Dumper.dumperStates.STORED);

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
            }

            Robot.getIntake().setIntakeExtenderPower(gamepad1.right_stick_y);

            Robot.update(matchTime);
            Robot.outputToTelemetry(telemetry);
            telemetry.addData("Loop Timer", loopTimer.milliseconds());
            telemetry.update();
        }
    }
}
