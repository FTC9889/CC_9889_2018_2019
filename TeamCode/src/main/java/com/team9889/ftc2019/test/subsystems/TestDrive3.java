package com.team9889.ftc2019.test.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.team9889.ftc2019.auto.AutoModeBase;
import com.team9889.ftc2019.auto.actions.Drive;
import com.team9889.ftc2019.auto.actions.Intake;
import com.team9889.ftc2019.auto.actions.Turn;
import com.team9889.ftc2019.auto.actions.Wait;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by MannoMation on 10/5/2018.
 */

@Autonomous
public class TestDrive3 extends AutoModeBase {
    @Override
    public void run(AllianceColor allianceColor) {
        runAction(new Turn(90));

        telemetry.addData("Angle", Robot.getDrive().getAngle().getTheda(AngleUnit.DEGREES));
        telemetry.update();
        runAction(new Wait(4000));
    }
}
