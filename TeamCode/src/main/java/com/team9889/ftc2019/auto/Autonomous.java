package com.team9889.ftc2019.auto;

import com.team9889.ftc2019.auto.actions.Drive.DriveMotionProfile;
import com.team9889.ftc2019.auto.actions.Drive.DriveToDistanceAndAngle;
import com.team9889.ftc2019.auto.actions.Drive.Turn;
import com.team9889.ftc2019.auto.actions.Dumper.DumperCollecting;
import com.team9889.ftc2019.auto.actions.Dumper.DumperDump;
import com.team9889.ftc2019.auto.actions.Dumper.DumperScoring;
import com.team9889.ftc2019.auto.actions.Intake.Intake;
import com.team9889.ftc2019.auto.actions.Intake.IntakeGrabbing;
import com.team9889.ftc2019.auto.actions.Intake.IntakeInFront;
import com.team9889.ftc2019.auto.actions.Intake.IntakeUp;
import com.team9889.ftc2019.auto.actions.Intake.IntakeZeroing;
import com.team9889.ftc2019.auto.actions.Intake.MarkerDumper;
import com.team9889.ftc2019.auto.actions.Intake.MarkerDumperUp;
import com.team9889.ftc2019.auto.actions.Intake.Outtake;
import com.team9889.ftc2019.auto.actions.Lift.Land2;
import com.team9889.ftc2019.auto.actions.Lift.LiftDown;
import com.team9889.ftc2019.auto.actions.Wait;
import com.team9889.ftc2019.subsystems.Camera;
import com.team9889.lib.control.math.cartesian.Rotation2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by Jin on 11/10/2017.
 */

@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class Autonomous extends AutoModeBase {

    private boolean middle = false;
    private boolean right = false;

    @Override
    public void run(AutoModeBase.Side side, boolean doubleSample, boolean scoreSample) {
//        runAction(new Land2());

        switch (side){
            case DEPOT:
                depotSingle(scoreSample, doubleSample);
                break;

            case CRATER:
                craterSingle(scoreSample, doubleSample);
                break;
        }
        
    }

    public void depotSingle(boolean scoreSample, boolean doubleSample){
        Robot.getIntake().setIntakeGateState(com.team9889.ftc2019.subsystems.Intake.IntakeGateStates.HOLDINGMARKER);
        Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTCENTER);
//        land
        if (doubleSample)
            runAction(new Land2());
        else
            runAction(new Wait(1000));

//        Scan Mineral
        runAction(new Wait(1000));
        if (Robot.getCamera().isGoldInfront())
            middle = true;
        else{
            Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTRIGHT);
            runAction(new Wait(500));

            if (Robot.getCamera().isGoldInfront())
                right = true;
        }


//                Drop Marker
        runAction(new IntakeUp());
        runAction(new DriveMotionProfile(16, 0));
        runAction(new IntakeInFront(30, 5000, true));
        ThreadAction(new LiftDown());
        ThreadAction(new DumperCollecting());
        runAction(new MarkerDumper());
        ThreadAction(new MarkerDumperUp());
        Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);


//                Collect Sample
        if (middle){ // Middle
            runAction(new IntakeZeroing(false, 2000));

            runAction(new DriveMotionProfile(-24, 0));

            runAction(new Turn(new Rotation2d(0, AngleUnit.DEGREES), 1000));
            runAction(new IntakeInFront(16, 1000 ,true));

            runAction(new Intake(1000));

        } else if (right){ //Right
            runAction(new IntakeZeroing(false, 2000));

            runAction(new DriveMotionProfile(-24, 0));

            runAction(new Turn(new Rotation2d(31.5, AngleUnit.DEGREES), 1000));
            runAction(new Turn(new Rotation2d(31.5, AngleUnit.DEGREES), 1000));
            runAction(new IntakeInFront(21.5, 5000, false));
            runAction(new Intake(1000));
        } else{ //Left
            runAction(new IntakeZeroing(false, 2000));

            runAction(new DriveMotionProfile(-24, 0));

            runAction(new Turn(new Rotation2d(-29.5, AngleUnit.DEGREES), 1000));
            runAction(new Turn(new Rotation2d(-29.5, AngleUnit.DEGREES), 1000));
            runAction(new IntakeInFront(21.5, 5000, false));
            runAction(new Intake(1000));
        }

        if (scoreSample) {
            runAction(new IntakeGrabbing());
            runAction(new Wait(250));
            runAction(new Turn(new Rotation2d(0, AngleUnit.DEGREES), 1000));

            if (!middle)
                ThreadAction(new DriveMotionProfile(-10, 0));

            runAction(new Wait(250));
            runAction(new DumperScoring());
            ThreadAction(new Turn(new Rotation2d(0, AngleUnit.DEGREES), 1000));
            runAction(new DumperDump());
        }else {
            runAction(new IntakeZeroing());
            runAction(new Turn(new Rotation2d(0, AngleUnit.DEGREES), 1000));
            if (!middle)
                runAction(new DriveMotionProfile(-5, 0));
        }

        runAction(new DriveMotionProfile(13, 0));

        // Drive to Crater

        ThreadAction(new DumperCollecting());

        runAction(new IntakeUp());

        runAction(new Turn(new Rotation2d(-90, AngleUnit.DEGREES), 1000));

        runAction(new DriveMotionProfile(40, -90));

        runAction(new Turn(new Rotation2d(-120, AngleUnit.DEGREES), 1000));

        runAction(new IntakeInFront(20, 5000, true));
    }

    public void craterSingle(boolean scoreSample, boolean doubleSample) {
        Robot.getIntake().setIntakeGateState(com.team9889.ftc2019.subsystems.Intake.IntakeGateStates.HOLDINGMARKER);
        Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTCENTER);

//        Land
        if (doubleSample)
            runAction(new Land2());
        runAction(new Wait(800));
        Robot.getIntake().setIntakeGateState(com.team9889.ftc2019.subsystems.Intake.IntakeGateStates.DOWN);


//                Collect Sample
        if (Robot.getCamera().isGoldInfront()){ // Middle
            // Grab Gold Block
            middle = true;
            Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
            runAction(new IntakeUp());

            runAction(new IntakeInFront(17, 2000, false));
            runAction(new Turn(new Rotation2d(0, AngleUnit.DEGREES), 1000));
            runAction(new Intake(1000));
        } else{
            Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTRIGHT);
            runAction(new Wait(500));

            if (Robot.getCamera().isGoldInfront()){ //Right
                right = true;
                Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                runAction(new DriveMotionProfile(5, 0));
                runAction(new Turn(new Rotation2d(36, AngleUnit.DEGREES), 1500));
                runAction(new IntakeUp());
                runAction(new IntakeInFront(18, 2000, false));
                runAction(new Intake(1000));
                runAction(new IntakeUp());
            } else{ //Left
                Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
                runAction(new DriveToDistanceAndAngle(5, 0, 500));
                runAction(new Turn(new Rotation2d(-30, AngleUnit.DEGREES), 1500));
                runAction(new IntakeUp());
                runAction(new IntakeInFront(20, 2000, false));
                runAction(new Intake(1000));
                runAction(new IntakeUp());
            }
        }


//        Collect and Score
        if (scoreSample){
//            runAction(new IntakeUp());
//            ThreadAction(new IntakeInFront(30, 2000, true));
//            runAction(new DriveMotionProfile(15, 0));
//            runAction(new Intake(1500));

            runAction(new IntakeGrabbing());

//            runAction(new DriveMotionProfile(-12, 0));

            runAction(new Turn(new Rotation2d(25, AngleUnit.DEGREES), 1000));

            if (!middle)
                runAction(new DriveMotionProfile(-8, 25));
            else
                runAction(new DriveMotionProfile(-3, 25));

            runAction(new DumperScoring());

            runAction(new DumperDump());

            if (right)
                runAction(new DriveMotionProfile(5, 25));
            else
                runAction(new DriveMotionProfile(8, 25));

            ThreadAction(new DumperCollecting());

            runAction(new Turn(new Rotation2d(0, AngleUnit.DEGREES), 1500));
//            ThreadAction(new IntakeZeroing());
        }else {
            ThreadAction(new IntakeZeroing());
            runAction(new Turn(new Rotation2d(0, AngleUnit.DEGREES), 1000));
            if (!right)
                runAction(new DriveMotionProfile(5, 0));
        }

//         Drive to Wall
        ThreadAction(new LiftDown());
        runAction(new DriveMotionProfile(10, 0));
        runAction(new Turn(new Rotation2d(-90, AngleUnit.DEGREES), 2000));
        runAction(new DriveMotionProfile(40, -90));
        runAction(new Turn(new Rotation2d(-135, AngleUnit.DEGREES), 1000));


        // Drive to Depot
        runAction(new IntakeInFront(35, 2000, true));

        if (!scoreSample)
            ThreadAction(new Outtake(1, 1000));

        runAction(new MarkerDumper());
        ThreadAction(new IntakeZeroing());
        ThreadAction(new MarkerDumperUp());
        runAction(new DriveMotionProfile(-52, -135));


//        Dump Marker in Depot


    }
}
