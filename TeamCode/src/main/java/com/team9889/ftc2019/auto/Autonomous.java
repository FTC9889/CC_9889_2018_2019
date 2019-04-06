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
import com.team9889.ftc2019.auto.actions.Intake.Outtake;
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
        switch (side){
            case DEPOT:
                depotSingle();
                break;

            case CRATER:
                craterSingle(scoreSample);
                break;
        }
        
    }

    public void depotSingle(){
        Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTCENTER);
//                land here

//                Scan Mineral
        runAction(new Wait(500));
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
        runAction(new DriveMotionProfile(17, 0));
        runAction(new IntakeInFront(22, 5000, true));
        runAction(new Turn(new Rotation2d(0, AngleUnit.DEGREES), 1000));
        ThreadAction(new DumperCollecting());
        runAction(new Outtake(-1, 1000));
        runAction(new IntakeUp());
        Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);


//                Collect Sample
        if (middle){ // Middle
            ThreadAction(new DriveMotionProfile(-12, 0));
            runAction(new IntakeZeroing(false, 2000));
            runAction(new IntakeInFront(5, 1000 ,true));
            runAction(new Intake(3000));

        } else if (right){ //Right
            ThreadAction(new DriveMotionProfile(-5, 0));
            runAction(new IntakeZeroing(false, 2000));

            runAction(new Turn(new Rotation2d(35, AngleUnit.DEGREES), 1000));
            runAction(new IntakeInFront(6, 5000, false));
            runAction(new Intake(3000));
        } else{ //Left
            ThreadAction(new DriveMotionProfile(-2, 0));
            runAction(new IntakeZeroing(false, 2000));

            runAction(new Turn(new Rotation2d(-45, AngleUnit.DEGREES), 1000));
            runAction(new IntakeInFront(6, 5000, false));
            runAction(new Intake(3000));
        }
    }

    public void craterSingle(boolean scoreSample) {
        Robot.getIntake().setIntakeGateState(com.team9889.ftc2019.subsystems.Intake.IntakeGateStates.HOLDINGMARKER);
        Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTCENTER);
//                Land Here
        runAction(new Wait(1300));
        Robot.getIntake().setIntakeGateState(com.team9889.ftc2019.subsystems.Intake.IntakeGateStates.DOWN);


//                Collect Sample
        if (Robot.getCamera().isGoldInfront()){ // Middle
            // Grab Gold Block
            Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);
            runAction(new IntakeInFront(17, 2000, false));

            runAction(new Turn(new Rotation2d(0, AngleUnit.DEGREES), 1000));
            runAction(new Intake(1000));
        } else{
            Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTRIGHT);
            runAction(new Wait(500));

            if (Robot.getCamera().isGoldInfront()){ //Right
                runAction(new DriveMotionProfile(17, 0));
                runAction(new Turn(new Rotation2d(45, AngleUnit.DEGREES), 2000));
                runAction(new Turn(new Rotation2d(45, AngleUnit.DEGREES), 2000));
                runAction(new IntakeInFront(18, 2000, false));
                runAction(new Intake(3000));
                runAction(new IntakeUp());
                ThreadAction(new IntakeZeroing(false, 2000));
                runAction(new DriveMotionProfile(-3, 45));
            } else{ //Left
                runAction(new DriveToDistanceAndAngle(13, 0, 2000));
                runAction(new Turn(new Rotation2d(-45, AngleUnit.DEGREES), 2000));
                runAction(new IntakeInFront(18, 2000, false));
                runAction(new Intake(3000));
                runAction(new IntakeUp());
                ThreadAction(new IntakeZeroing(false, 2000));
            }
        }


//        Collect and Score
        if (scoreSample){
            runAction(new IntakeUp());
            ThreadAction(new IntakeInFront(30, 2000, true));
            runAction(new DriveMotionProfile(15, 0));
            runAction(new Intake(1500));

            ThreadAction(new IntakeGrabbing());

            runAction(new DriveMotionProfile(-12, 0));

            runAction(new Turn(new Rotation2d(20, AngleUnit.DEGREES), 1000));

            runAction(new DriveMotionProfile(-8, 25));

            runAction(new DumperScoring());

            runAction(new DumperDump());

            runAction(new DriveMotionProfile(8, 25));

            ThreadAction(new DumperCollecting());

            runAction(new Turn(new Rotation2d(0, AngleUnit.DEGREES), 1500));
            ThreadAction(new IntakeZeroing());
            runAction(new DriveMotionProfile(16, 0));
        }

//         Drive to Wall
        runAction(new Turn(new Rotation2d(-90, AngleUnit.DEGREES), 2000));
        runAction(new DriveMotionProfile(55, -90));
        runAction(new Turn(new Rotation2d(-135, AngleUnit.DEGREES), 3000));


        // Drive to Depot
        ThreadAction(new DriveMotionProfile(15, -135));
        runAction(new IntakeInFront(20, 2000, true));
        runAction(new Wait(1000));
//        ThreadAction(new IntakeZeroing());
        runAction(new DriveMotionProfile(-52, -135));


//        Dump Marker in Depot


    }
}
