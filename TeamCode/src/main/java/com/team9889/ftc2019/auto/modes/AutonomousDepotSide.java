package com.team9889.ftc2019.auto.modes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.team9889.ftc2019.auto.AutoModeBase;
import com.team9889.ftc2019.auto.actions.Drive.DriveLeftMotor;
import com.team9889.ftc2019.auto.actions.Drive.DriveToDistanceAndAngle;
import com.team9889.ftc2019.auto.actions.Drive.DriveTurn;
import com.team9889.ftc2019.auto.actions.Drive.Turn;
import com.team9889.ftc2019.auto.actions.Dumper.DumperCollecting;
import com.team9889.ftc2019.auto.actions.Dumper.DumperDump;
import com.team9889.ftc2019.auto.actions.Dumper.DumperScoring;
import com.team9889.ftc2019.auto.actions.Intake.Intake;
import com.team9889.ftc2019.auto.actions.Intake.IntakeCollecting;
import com.team9889.ftc2019.auto.actions.Intake.IntakeGrabbing;
import com.team9889.ftc2019.auto.actions.Intake.IntakeInFront;
import com.team9889.ftc2019.auto.actions.Intake.IntakeUp;
import com.team9889.ftc2019.auto.actions.Intake.IntakeZeroing;
import com.team9889.ftc2019.auto.actions.Intake.Outtake;
import com.team9889.ftc2019.auto.actions.Lift.Land2;
import com.team9889.ftc2019.auto.actions.Wait;
import com.team9889.ftc2019.subsystems.Camera;
import com.team9889.lib.control.math.cartesian.Rotation2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by MannoMation on 1/5/2019.
 */

@Autonomous(group = "Competition Autonomous")
public class AutonomousDepotSide extends AutoModeBase {
    @Override
    public void run(AllianceColor allianceColor) {
        Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTCENTER);
        runAction(new Land2(2500));


        ThreadAction(new IntakeInFront(25));
        runAction(new DriveToDistanceAndAngle(18, 0, 1000));
        Robot.getIntake().setIntakeHardStopState(com.team9889.ftc2019.subsystems.Intake.IntakeHardStop.UP);
        ThreadAction(new DumperCollecting());
        runAction(new Outtake(-.5, 500));
        runAction(new IntakeUp());

        runAction(new Wait(1000));

        ThreadAction(new IntakeZeroing(false));

        if (Robot.getCamera().isGoldInfront()){ // Middle

            runAction(new Wait(1000));

            runAction(new IntakeUp());
            runAction(new Intake());
        } else{

//            Scan Right Mineral
            Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTRIGHT);
            runAction(new Wait(1000));

            if (Robot.getCamera().isGoldInfront()){ //Right
                runAction(new Turn(new Rotation2d(45, AngleUnit.DEGREES), 1000));
                runAction(new IntakeInFront(15));
                runAction(new Intake());
                runAction(new Turn(new Rotation2d(0, AngleUnit.DEGREES), 1000));

            } else{ //Left
                runAction(new Turn(new Rotation2d(-45, AngleUnit.DEGREES), 1000));
                runAction(new IntakeInFront(15));
                runAction(new Intake());
                runAction(new Turn(new Rotation2d(45, AngleUnit.DEGREES), 1000));

            }
        }

//        Score Sample
        runAction(new DriveToDistanceAndAngle(-12, 0, 1000));
        runAction(new IntakeGrabbing());
        runAction(new Wait(500));
        runAction(new DumperScoring());
        runAction(new DumperDump());


//            Drive to Crater
        runAction(new IntakeZeroing(false));
        runAction(new DriveToDistanceAndAngle(8, 0, 1000));
        runAction(new Turn(new Rotation2d(-90, AngleUnit.DEGREES), 2000));
        ThreadAction(new DumperCollecting());
        runAction(new DriveToDistanceAndAngle(35, -90, 3000));
        runAction(new Turn(new Rotation2d(-135, AngleUnit.DEGREES), 2000));

//            Intake in Crater
        runAction(new IntakeInFront(15));
        runAction(new IntakeCollecting());

//        Go to Scoring Location
        ThreadAction(new IntakeGrabbing());
        runAction(new Turn(new Rotation2d(-90, AngleUnit.DEGREES), 2000));
        runAction(new DriveToDistanceAndAngle(-50, -90, 3000));
        runAction(new Turn(new Rotation2d(-90, AngleUnit.DEGREES), 2000));
        runAction(new DumperDump());
    }
}
