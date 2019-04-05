package com.team9889.ftc2019.test.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.team9889.ftc2019.auto.AutoModeBase;
import com.team9889.ftc2019.auto.actions.Drive.DriveToPosition;
import com.team9889.ftc2019.auto.actions.Drive.DriveTurn;
import com.team9889.ftc2019.auto.actions.Wait;
import com.team9889.lib.control.math.cartesian.Rotation2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by MannoMation on 11/2/2018.
 */

@Autonomous
@Disabled
public class SamplingTest extends AutoModeBase {
    @Override
    public void run(AutoModeBase.Side side, boolean doubleSample, boolean scoreSample) {

        Robot.getCamera().setXYAxisPosition(0, .75);

        runAction(new Wait(1000));

        if (Robot.getCamera().isGoldInfront()){
            runAction(new DriveToPosition(24,24, 4000));
            double GoldSample = 2;
        } else{
            Robot.getCamera().setXYAxisPosition(.175, .75);

            runAction(new Wait(1000));

            if (Robot.getCamera().isGoldInfront()){
                runAction(new DriveToPosition(5, 5, 1500));
                runAction(new DriveTurn(new Rotation2d(45, AngleUnit.DEGREES), 2000));
                runAction(new DriveToPosition(20, 20, 3000));
                double GoldSample = 3;
            } else{
                runAction(new DriveToPosition(5, 5, 1500));
                runAction(new DriveTurn(new Rotation2d(-45, AngleUnit.DEGREES), 2000));
                runAction(new DriveToPosition(20, 20, 3000));
                double GoldSample = 1;
            }
        }
        Robot.getCamera().setXYAxisPosition(0, 5);
    }
}
