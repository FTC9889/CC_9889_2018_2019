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
import com.team9889.ftc2019.states.LiftStates;
import com.team9889.ftc2019.subsystems.Camera;
import com.team9889.ftc2019.subsystems.Robot;
import com.team9889.lib.control.math.cartesian.Rotation2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by MannoMation on 1/26/2019.
 */

@Autonomous
public class AutonomousCraterSide extends AutoModeBase {
    @Override
    public void run(AllianceColor allianceColor) {
        Robot.setScorerStates(com.team9889.ftc2019.subsystems.Robot.scorerStates.STORED);
        Robot.update(matchTime);
        Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTCENTER);
        runAction(new Land2(2500));
        runAction(new Wait(500));
        Robot.getIntake().setHopperDumperState(com.team9889.ftc2019.subsystems.Intake.HopperDumperStates.OPEN);

        if (Robot.getCamera().isGoldInfront()){ // Middle
//            Grab Gold Block
            runAction(new IntakeInFront(20, 2000, false));
            Robot.getIntake().setIntakeHardStopState(com.team9889.ftc2019.subsystems.Intake.IntakeHardStop.UP);
            runAction(new Intake());
            ThreadAction(new IntakeZeroing(false, 2000));
            runAction(new DriveToDistanceAndAngle(12.5, 0, 1000));
        } else{
            Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTRIGHT);
            runAction(new Wait(500));

            if (Robot.getCamera().isGoldInfront()){ //Right
                runAction(new DriveToDistanceAndAngle(5, 0, 1000));
                runAction(new Turn(new Rotation2d(45, AngleUnit.DEGREES), 2000));
                runAction(new IntakeInFront(21, 2000, false));
                Robot.getIntake().setIntakeHardStopState(com.team9889.ftc2019.subsystems.Intake.IntakeHardStop.UP);
                runAction(new Intake());
                runAction(new IntakeUp());
                ThreadAction(new IntakeZeroing(false, 2000));
                runAction(new Turn(new Rotation2d(0, AngleUnit.DEGREES), 2000));
                runAction(new DriveToDistanceAndAngle(15, 0, 1000));
            } else{ //Left
                runAction(new DriveToDistanceAndAngle(10, 0, 2000));
                runAction(new Turn(new Rotation2d(-45, AngleUnit.DEGREES), 2000));
                runAction(new IntakeInFront(21, 2000, false));
                Robot.getIntake().setIntakeHardStopState(com.team9889.ftc2019.subsystems.Intake.IntakeHardStop.UP);
                runAction(new Intake());
                runAction(new IntakeUp());
                ThreadAction(new IntakeZeroing(false, 2000));
                runAction(new Turn(new Rotation2d(0, AngleUnit.DEGREES), 2000));
                runAction(new DriveToDistanceAndAngle(15, 0, 1000));
            }
        }

//        Drive to Wall
        runAction(new Turn(new Rotation2d(90, AngleUnit.DEGREES), 2000));
        runAction(new DriveToDistanceAndAngle(-40, 90, 4000));
        runAction(new Turn(new Rotation2d(45, AngleUnit.DEGREES), 3000));

//        Drive to Depot
        runAction(new DriveToDistanceAndAngle(-45, 45, 1000));

//        Dump Marker
        runAction(new DumperScoring());
        runAction(new DumperInit(2000));
        runAction(new DumpMarker());

//        Park in Crater
        ThreadAction(new DumperScoring());
        runAction(new DriveToDistanceAndAngle(30, 45, 4000));
        runAction(new IntakeInFront(20, 2000, true));

    }
}
