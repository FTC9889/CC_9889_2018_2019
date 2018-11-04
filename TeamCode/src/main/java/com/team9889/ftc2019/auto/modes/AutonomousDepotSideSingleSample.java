package com.team9889.ftc2019.auto.modes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.team9889.ftc2019.auto.AutoModeBase;
import com.team9889.ftc2019.auto.actions.DriveToPosition;
import com.team9889.ftc2019.auto.actions.DriveTurn;
import com.team9889.ftc2019.auto.actions.Outtake;
import com.team9889.ftc2019.auto.actions.Wait;
import com.team9889.lib.control.math.cartesian.Rotation2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by MannoMation on 11/3/2018.
 */

@Autonomous
public class AutonomousDepotSideSingleSample extends AutoModeBase {
    @Override
    public void run(AllianceColor allianceColor) {
        Robot.getLift().setStopperPosition(0.3);
        runAction(new Wait(1000));
        Robot.getDrive().setLeftRightPower(.1, .1);
        runAction(new Wait(1000));
        Robot.getDrive().setLeftRightPower(0, 0);
        Robot.getLift().setHookPosition(0);
        runAction(new Wait(1000));

        Robot.getCamera().setXYAxisPosition(0, .75);

        runAction(new Wait(1250));

        int GoldSample = 0;

        if (Robot.getCamera().isGoldInfront()){
            runAction(new DriveToPosition(50,50, 3000));
            runAction(new Outtake());
            GoldSample = 2;
        } else{
            Robot.getCamera().setXYAxisPosition(.175, .75);

            runAction(new Wait(1000));

            if (Robot.getCamera().isGoldInfront()){
                runAction(new DriveToPosition(5, 5, 500));
                runAction(new DriveTurn(new Rotation2d(45, AngleUnit.DEGREES), 3000));
                runAction(new DriveToPosition(20, 20, 1000));
                runAction(new DriveTurn(new Rotation2d(-60, AngleUnit.DEGREES), 3000));
                runAction(new DriveToPosition(40, 40, 1000));
                runAction(new Outtake());
                GoldSample = 3;
            } else{
                runAction(new DriveToPosition(5, 5, 1500));
                runAction(new DriveTurn(new Rotation2d(-45, AngleUnit.DEGREES), 3000));
                runAction(new DriveToPosition(30, 30, 2750));
                runAction(new DriveTurn(new Rotation2d(60, AngleUnit.DEGREES),2000));
                runAction(new DriveToPosition(40, 40, 1000));
                runAction(new Outtake());
                GoldSample = 1;
            }
        }


    }
}
