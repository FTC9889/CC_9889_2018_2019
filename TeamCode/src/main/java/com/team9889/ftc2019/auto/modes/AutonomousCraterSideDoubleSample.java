package com.team9889.ftc2019.auto.modes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.team9889.ftc2019.auto.AutoModeBase;
import com.team9889.ftc2019.auto.actions.DriveToPosition;
import com.team9889.ftc2019.auto.actions.DriveTurn;
import com.team9889.ftc2019.auto.actions.Outtake;
import com.team9889.ftc2019.auto.actions.Wait;
import com.team9889.ftc2019.subsystems.Camera;
import com.team9889.lib.control.math.cartesian.Rotation2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by MannoMation on 10/5/2018.
 */

@Autonomous
public class AutonomousCraterSideDoubleSample extends AutoModeBase {
    @Override
    public void run(AllianceColor allianceColor) {
//        Robot.getLift().setStopperPosition(0.3);
        runAction(new Wait(1000));
        Robot.getDrive().setLeftRightPower(.1, .1);
        runAction(new Wait(1000));
        Robot.getDrive().setLeftRightPower(0, 0);
//        Robot.getLift().setHookPosition(0);
        runAction(new Wait(1000));

        Robot.getCamera().setXYAxisPosition(0, .75);

        runAction(new Wait(1250));

        if (Robot.getCamera().isGoldInfront()){
            runAction(new DriveToPosition(23,23, 3000));
            Robot.getCamera().setGold(Camera.GoldPositions.CENTER);
        } else{
            Robot.getCamera().setXYAxisPosition(.175, .75);

            runAction(new Wait(1000));

            if (Robot.getCamera().isGoldInfront()){
                runAction(new DriveToPosition(5, 5, 500));
                runAction(new DriveTurn(new Rotation2d(45, AngleUnit.DEGREES), 3000));
                runAction(new DriveToPosition(20, 20, 1000));
                Robot.getCamera().setGold(Camera.GoldPositions.RIGHT);
            } else{
                runAction(new DriveToPosition(5, 5, 1500));
                runAction(new DriveTurn(new Rotation2d(-45, AngleUnit.DEGREES), 3000));
                runAction(new DriveToPosition(20, 20, 2750));
                Robot.getCamera().setGold(Camera.GoldPositions.LEFT);
            }
        }
        Robot.getCamera().setXYAxisPosition(0, 0.5);

        runAction(new DriveToPosition(-10, -10, 1500));

        switch (Robot.getCamera().getGold()){
            case LEFT:
                runAction(new DriveTurn(new Rotation2d(-55, AngleUnit.DEGREES), 2000));
                runAction(new DriveToPosition(39,39,2000));
                break;

            case RIGHT:
                runAction(new DriveTurn(new Rotation2d(-90, AngleUnit.DEGREES), 3000));
                runAction(new DriveToPosition(42, 42, 2000));
                break;

            case CENTER:
                runAction(new DriveTurn(new Rotation2d(-100, AngleUnit.DEGREES), 2000));
                runAction(new DriveToPosition(35, 35, 2000));
                break;
        }


        runAction(new Wait(250));
        runAction(new DriveTurn(new Rotation2d(Robot.getDrive().getAngle().getTheda(AngleUnit.DEGREES)-135,
                AngleUnit.DEGREES), 1500));
        runAction(new DriveTurn(new Rotation2d(Robot.getDrive().getAngle().getTheda(AngleUnit.DEGREES)-135,
                AngleUnit.DEGREES), 1500));

        switch (Robot.getCamera().getGold()){
            case LEFT:
                runAction(new DriveToPosition(20,20,1000));
                runAction(new DriveTurn(new Rotation2d(-45, AngleUnit.DEGREES), 1000));
                runAction(new DriveToPosition(35,35,1500));
                runAction(new DriveTurn(new Rotation2d(55, AngleUnit.DEGREES), 1000));
                runAction(new DriveToPosition(-12, -12, 750));
                runAction(new DriveToPosition(12, 12, 750));
                runAction(new DriveTurn(new Rotation2d(30, AngleUnit.DEGREES), 750));
                runAction(new Outtake());
                break;

            case CENTER:
                runAction(new DriveToPosition(30, 30, 1500));
                runAction(new DriveTurn(new Rotation2d(-45, AngleUnit.DEGREES), 1000));
                runAction(new DriveToPosition(12, 12, 750));
                runAction(new DriveTurn(new Rotation2d(-45, AngleUnit.DEGREES), 1000));
                runAction(new DriveToPosition(15,15,1500));
                runAction(new DriveToPosition(-15,-15,1500));
                runAction(new DriveTurn(new Rotation2d(90, AngleUnit.DEGREES), 2000));
                runAction(new Outtake());
                break;

            case RIGHT:
                runAction(new DriveToPosition(28, 28, 1500));
//                runAction(new DriveTurn(new Rotation2d(20, AngleUnit.DEGREES), 1000));
                runAction(new DriveToPosition(15, 15, 750));
                runAction(new Outtake());
                runAction(new DriveToPosition(-40, -40, 3000));
                runAction(new DriveTurn(new Rotation2d(-37.5, AngleUnit.DEGREES), 750));
                runAction(new DriveToPosition(-24,-24,750));
                break;
        }

//        switch (GoldSample){
//            case 1:
//                break;
//
//            case 2:
//                runAction(new DriveTurn(new Rotation2d(-45, AngleUnit.DEGREES), 1000));
//                runAction(new DriveToPosition(4,4,750));
//                runAction(new DriveTurn(new Rotation2d(-45, AngleUnit.DEGREES), 1000));
//                runAction(new DriveToPosition(25, 25, 1500));
//                break;
//
//            case 3:
//                break;
//        }
//        runAction(new DriveToPosition(-5, -5));
//        runAction(new DriveTurn(new Rotation2d(180, AngleUnit.DEGREES)));
//        runAction(new DriveToPosition(61, 61));
//        runAction(new IntakeToPosition(5));
    }
}
