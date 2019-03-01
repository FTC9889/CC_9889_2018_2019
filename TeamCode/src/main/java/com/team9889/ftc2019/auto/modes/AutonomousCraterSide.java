package com.team9889.ftc2019.auto.modes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.team9889.ftc2019.auto.AutoModeBase;
import com.team9889.ftc2019.auto.actions.Drive.DriveToDistanceAndAngle;
import com.team9889.ftc2019.auto.actions.Drive.DriveTurn;
import com.team9889.ftc2019.auto.actions.Drive.Turn;
import com.team9889.ftc2019.auto.actions.Dumper.DumpMarker;
import com.team9889.ftc2019.auto.actions.Dumper.DumperCollecting;
import com.team9889.ftc2019.auto.actions.Dumper.DumperDump;
import com.team9889.ftc2019.auto.actions.Dumper.DumperInit;
import com.team9889.ftc2019.auto.actions.Dumper.DumperScoring;
import com.team9889.ftc2019.auto.actions.Intake.Intake;
import com.team9889.ftc2019.auto.actions.Intake.IntakeCollecting;
import com.team9889.ftc2019.auto.actions.Intake.IntakeGrabbing;
import com.team9889.ftc2019.auto.actions.Intake.IntakeInFront;
import com.team9889.ftc2019.auto.actions.Intake.IntakeStop;
import com.team9889.ftc2019.auto.actions.Intake.IntakeUp;
import com.team9889.ftc2019.auto.actions.Intake.IntakeZeroing;
import com.team9889.ftc2019.auto.actions.Intake.Outtake;
import com.team9889.ftc2019.auto.actions.Lift.Land2;
import com.team9889.ftc2019.auto.actions.Wait;
import com.team9889.ftc2019.subsystems.Camera;
import com.team9889.lib.control.math.cartesian.Rotation2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by MannoMation on 1/26/2019.
 */

@Autonomous
public class AutonomousCraterSide extends AutoModeBase {
    @Override
    public void run(AllianceColor allianceColor) {
//        Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTCENTER);
        Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
        runAction(new Land2(5000));


//        if (Robot.getCamera().isGoldInfront()){ // Middle
////            Grab Gold Block
//            ThreadAction(new DriveToDistanceAndAngle(10, 0, 1000));
//            runAction(new IntakeInFront());
//            runAction(new Intake());
//            runAction(new Wait(1000));
//            ThreadAction(new IntakeZeroing());
//
////            runAction(new DriveToDistanceAndAngle(5, 0, 1000));
//
////            Drive to Wall
//            runAction(new Turn(new Rotation2d(100, AngleUnit.DEGREES), 3000));
//            runAction(new DriveToDistanceAndAngle(-46, 100, 3000));
//            Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
//
////            Turn towards Depot
//            runAction(new Turn(new Rotation2d(45, AngleUnit.DEGREES), 1500));
//
//            ThreadAction(new DumperInit(2000));
//            runAction(new DriveToDistanceAndAngle(-25, 45, 2000));
//
//            runAction(new Wait(500));
//            runAction(new DumpMarker());
//
//            runAction(new DriveToDistanceAndAngle(50, 45, 4000));
//            runAction(new IntakeInFront());
//
//            ThreadAction(new DumperCollecting());
//            runAction(new IntakeCollecting());
//
//            ThreadAction(new IntakeGrabbing());
//            runAction(new DriveToDistanceAndAngle(-12, 45, 1000));
//
//            runAction(new Turn(new Rotation2d(105, AngleUnit.DEGREES), 1500));
//            runAction(new DriveToDistanceAndAngle(35, 105, 2000));
//            runAction(new Turn(new Rotation2d(0, AngleUnit.DEGREES), 2000));
//
//            runAction(new DumperScoring());
//            runAction(new DumperDump());
//        } else{
//            Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTRIGHT);
//
//            runAction(new Wait(1000));
//
//            if (Robot.getCamera().isGoldInfront()){ //Right
//                runAction(new DriveToDistanceAndAngle(10, 0, 3000));
//                runAction(new DriveTurn(new Rotation2d(45, AngleUnit.DEGREES), 2000));
//                runAction(new DriveToDistanceAndAngle(20, 45, 1500));
//                ThreadAction(new IntakeZeroing());
//                runAction(new DriveToDistanceAndAngle(-8, 45, 1500));
//
//                // Face Wall
//                runAction(new Turn(new Rotation2d(-90, AngleUnit.DEGREES), 3000));
//                runAction(new DriveToDistanceAndAngle(58, -90, 4000));
//
//                // Turn To Depot
//                runAction(new DriveTurn(new Rotation2d(-45, AngleUnit.DEGREES), 2000));
//
//                ThreadAction(new IntakeInFront());
//
//                // Drive closer to depot
//                runAction(new DriveToDistanceAndAngle(30, -135, 3000));
//
//                // Score Marker
//                runAction(new Outtake(-.5));
//
//                ThreadAction(new IntakeZeroing());
//
//                // Drive to crater
//                runAction(new DriveToDistanceAndAngle(-70, -137, 4000));
//
//            } else{ //Left
//
//                // Drive away from Lander
//                // Drive away from Lander
//                runAction(new DriveToDistanceAndAngle(8, 0, 3000));
//                runAction(new DriveTurn(new Rotation2d(-45, AngleUnit.DEGREES), 2000));
//                runAction(new DriveToDistanceAndAngle(30, -45, 3000));
//                ThreadAction(new IntakeZeroing());
//
//                // Turn to wall
//                runAction(new Turn(new Rotation2d(-90, AngleUnit.DEGREES), 3000));
//                runAction(new DriveToDistanceAndAngle(20, -90, 2500));
//
//                runAction(new DriveTurn(new Rotation2d(-45, AngleUnit.DEGREES), 1500));
//                ThreadAction(new IntakeInFront());
//                runAction(new DriveToDistanceAndAngle(43, -135, 3000)); // Distance to Depot TODO: Check this value on field
//                runAction(new Outtake());
//
//                runAction(new IntakeStop());
//                ThreadAction(new IntakeZeroing());
//                runAction(new DriveToDistanceAndAngle(-70, -135, 4000));
//            }
//        }
    }
}
