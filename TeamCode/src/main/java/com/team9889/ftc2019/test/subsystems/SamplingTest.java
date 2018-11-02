package com.team9889.ftc2019.test.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.team9889.ftc2019.auto.AutoModeBase;
import com.team9889.ftc2019.auto.actions.DriveToPosition;
import com.team9889.ftc2019.auto.actions.Intake;
import com.team9889.ftc2019.auto.actions.IntakeStop;
import com.team9889.ftc2019.auto.actions.Wait;

/**
 * Created by MannoMation on 11/2/2018.
 */

@Autonomous
public class SamplingTest extends AutoModeBase {
    @Override
    public void run(AllianceColor allianceColor) {

        runAction(new Wait(3000));

        if (Robot.getCamera().isGoldInfront()){
            runAction(new Intake());
            runAction(new DriveToPosition(5,5));
            runAction(new IntakeStop());
        } else{
            telemetry.addData("Gold Position", Robot.getCamera().getGoldPosition());
            telemetry.update();
            Robot.getCamera().setXYAxisPosition(.1, .6);

            telemetry.addData("Pos", ((0.003125*Robot.getCamera().getGoldPosition())));
            telemetry.update();

            runAction(new Wait(3000));
            
            if (Robot.getCamera().isGoldInfront()){
                runAction(new Intake());
//                runAction(new DriveToPosition(5,5));
                runAction(new IntakeStop());
            } else{
//                runAction(new DriveTurn(new Rotation2d(-90, AngleUnit.DEGREES)));
            }
        }
        telemetry.update();
        runAction(new Wait(3000));
    }
}
