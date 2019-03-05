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

    private boolean middle = false;
    private boolean right = false;

    @Override
    public void run(AllianceColor allianceColor) {
        Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTCENTER);
        runAction(new Land2(2500));

//        Scan Mineral
        runAction(new Wait(500));
        if (Robot.getCamera().isGoldInfront())
            middle = true;
        else{
            Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTRIGHT);
            runAction(new Wait(500));

            if (Robot.getCamera().isGoldInfront())
                right = true;
        }

        runAction(new IntakeUp());
        runAction(new DriveToDistanceAndAngle(24, 0, 1000));
        runAction(new IntakeInFront(22, 5000, true));
        runAction(new Turn(new Rotation2d(0, AngleUnit.DEGREES), 1000));
        Robot.getIntake().setIntakeHardStopState(com.team9889.ftc2019.subsystems.Intake.IntakeHardStop.UP);
        ThreadAction(new DumperCollecting());
        runAction(new Outtake(-1, 1000));
        runAction(new IntakeUp());
        Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);

//        runAction(new Wait(1000));

        ThreadAction(new DriveToDistanceAndAngle(-8, 0, 1000));
        runAction(new IntakeZeroing(false, 1000));

        if (middle){ // Middle
            runAction(new DriveToDistanceAndAngle(-5, 0, 1000));
            runAction(new Intake());
            runAction(new Intake());
            runAction(new Intake());
        } else if (right){ //Right
                runAction(new Turn(new Rotation2d(35, AngleUnit.DEGREES), 1000));
                runAction(new IntakeInFront(10, 5000, false));
                runAction(new Intake());
                runAction(new Intake());
                runAction(new Intake());
        } else{ //Left
                runAction(new Turn(new Rotation2d(-45, AngleUnit.DEGREES), 1000));
                runAction(new IntakeInFront(12, 5000, false));
                runAction(new Intake());
                runAction(new Intake());
                runAction(new Intake());
            }

//        Score Sample
        if (Robot.autoSampled) {
            runAction(new IntakeGrabbing());
            runAction(new Wait(500));
            runAction(new Turn(new Rotation2d(0, AngleUnit.DEGREES), 1000));
            runAction(new DriveToDistanceAndAngle(-2, 0, 1000));
            runAction(new DumperScoring());
            runAction(new DumperDump());
        }


//            Drive to Crater
        runAction(new IntakeUp());
        ThreadAction(new IntakeZeroing(false, 1500));
        if (middle)
            runAction(new DriveToDistanceAndAngle(8, 0, 1000));
        else
            runAction(new DriveToDistanceAndAngle(4, 0, 1000));
        runAction(new Turn(new Rotation2d(-90, AngleUnit.DEGREES), 1000));
        runAction(new DriveToDistanceAndAngle(36, -90, 3000));
        runAction(new Turn(new Rotation2d(-120, AngleUnit.DEGREES), 2000));
        runAction(new IntakeInFront(20, 5000, true));
    }
}
