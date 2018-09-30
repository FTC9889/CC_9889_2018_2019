package com.team9889.ftc2019.test.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.team9889.ftc2019.Team9889Linear;

/**
 * Created by MannoMation and licorice17 on 9/29/2018.
 */

@Autonomous
public class TestDrive2 extends Team9889Linear{

    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart(true);

        telemetry.addData("left", Robot.getDrive().getLeftDistance() );
        telemetry.addData("right", Robot.getDrive().getRightDistance() );
        telemetry.update();

        Robot.getDrive().setLeftRightPosition(10, 10);

        /*double degrees = -90;
        Robot.getDrive().setLeftRightPosition( -(2 * Constants.WheelbaseWidth * Math.toRadians(degrees))/Math.PI,
                (2 * Constants.WheelbaseWidth * Math.toRadians(degrees))/Math.PI);*/

        Robot.getDrive().setLeftRightPosition(21.75,-21.75);
        sleep(500);
        Robot.getDrive().setLeftRightPosition(-10,-10);
        sleep(500);
        Robot.getDrive().setLeftRightPosition(52,-52);
        sleep(500);
        Robot.getDrive().setLeftRightPosition(10,10);

        telemetry.addData("left", Robot.getDrive().getLeftDistance() );
        telemetry.addData("right", Robot.getDrive().getRightDistance() );
        telemetry.update();

        sleep(500);
        Robot.getDrive().setLeftRightPosition(-300,300);

        sleep(5000);
    }
}
