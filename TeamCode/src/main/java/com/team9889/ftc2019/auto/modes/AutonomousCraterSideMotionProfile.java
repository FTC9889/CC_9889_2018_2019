package com.team9889.ftc2019.auto.modes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.team9889.ftc2019.auto.AutoModeBase;
import com.team9889.ftc2019.auto.actions.Drive.DriveMotionProfile;
import com.team9889.ftc2019.auto.actions.Drive.DriveToDistanceAndAngle;
import com.team9889.ftc2019.auto.actions.Drive.Turn;
import com.team9889.ftc2019.auto.actions.Dumper.DumperInit;
import com.team9889.ftc2019.auto.actions.Dumper.DumperScoring;
import com.team9889.ftc2019.auto.actions.Intake.Intake;
import com.team9889.ftc2019.auto.actions.Intake.IntakeInFront;
import com.team9889.ftc2019.auto.actions.Intake.IntakeUp;
import com.team9889.ftc2019.auto.actions.Intake.IntakeZeroing;
import com.team9889.ftc2019.auto.actions.Lift.Land2;
import com.team9889.ftc2019.auto.actions.LiftReady;
import com.team9889.ftc2019.auto.actions.Wait;
import com.team9889.ftc2019.subsystems.Camera;
import com.team9889.ftc2019.subsystems.Dumper;
import com.team9889.lib.control.math.cartesian.Rotation2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by MannoMation on 1/26/2019.
 */

@Autonomous(group = "Motion Profiled : Competition Autonomous")
@Disabled
public class AutonomousCraterSideMotionProfile extends AutoModeBase {
    @Override
    public void run(AutoModeBase.Side side, boolean doubleSample, boolean scoreSample) {
        Robot.setScorerStates(com.team9889.ftc2019.subsystems.Robot.scorerStates.STORED);
        Robot.update(matchTime);
        Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTCENTER);
        runAction(new Land2(2500));
        runAction(new Wait(500));
        Robot.getIntake().setIntakeGateState(com.team9889.ftc2019.subsystems.Intake.IntakeGateStates.DOWN);

        if (Robot.getCamera().isGoldInfront()){ // Middle
            // Grab Gold Block
            runAction(new IntakeInFront(20, 2000, false));

            runAction(new Intake(3000));

            ThreadAction(new IntakeZeroing(false, 2000));
            runAction(new DriveMotionProfile(17, 0));
//            runAction(new DriveToDistanceAndAngle(30, 0, 1000));
        } else{
            Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTRIGHT);
            runAction(new Wait(500));

            if (Robot.getCamera().isGoldInfront()){ //Right
                runAction(new DriveMotionProfile(17, 0));
//                runAction(new DriveToDistanceAndAngle(20, 0, 1000));
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

        // Drive to Wall
        runAction(new Turn(new Rotation2d(90, AngleUnit.DEGREES), 2000));
        runAction(new DriveMotionProfile(-55, 90));
//        runAction(new DriveToDistanceAndAngle(-48, 90, 3000));
        runAction(new Turn(new Rotation2d(45, AngleUnit.DEGREES), 3000));

        // Drive to Depot
        runAction(new DriveMotionProfile(-25, 45));
//        runAction(new DriveToDistanceAndAngle(-25, 45, 1000));

        // Dump Marker
        Robot.getDumper().setDumperStates(Dumper.dumperStates.DUMPAUTO);
        runAction(new DumperInit(2000));

        // Park in Crater
        ThreadAction(new DumperScoring());
        runAction(new DriveMotionProfile(40, 45));
        ThreadAction(new IntakeInFront(20, 2000, true));
//        runAction(new DriveToDistanceAndAngle(40, 45, 4000));
        runAction(new LiftReady());

    }
}
