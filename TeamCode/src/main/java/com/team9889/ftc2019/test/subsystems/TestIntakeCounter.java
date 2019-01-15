package com.team9889.ftc2019.test.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.team9889.ftc2019.Team9889Linear;
import com.team9889.ftc2019.subsystems.Intake;

/**
 * Created by joshua9889 on 1/14/2019.
 */
@Autonomous
public class TestIntakeCounter extends Team9889Linear {
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart(false);

        while (opModeIsActive()){
            Robot.getIntake().updateTestCounter();

            Robot.getIntake().setIntakeRotatorState(Intake.RotatorStates.DOWN);

            if(gamepad1.a)
                Robot.getIntake().intake();
            else
                Robot.getIntake().setIntakePower(0);

            telemetry.addData("Counted", Robot.getIntake().numberOfMinerals);
            telemetry.update();
        }
    }
}
