package com.team9889.ftc2019.auto.modes.deprecated;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.team9889.ftc2019.auto.AutoModeBase;
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
 * Created by MannoMation on 1/5/2019.
 */

@Autonomous(group = "Motion Profiled : Competition Autonomous")
@Disabled
@Deprecated
public class AutonomousDepotSideMotionProfile extends AutoModeBase {

    private boolean middle = false;
    private boolean right = false;

    @Override
    public void run(AutoModeBase.Side side, boolean doubleSample, boolean scoreSample) {
        Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTCENTER);
//        runAction(new Land2(2500));

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
        runAction(new DriveMotionProfile(17, 0));
        runAction(new IntakeInFront(22, 5000, true));
        runAction(new Turn(new Rotation2d(0, AngleUnit.DEGREES), 1000));
        ThreadAction(new DumperCollecting());
        runAction(new Outtake(-1, 1000));
        runAction(new IntakeUp());
        Robot.getCamera().setCameraPosition(Camera.CameraPositions.TELEOP);

//        runAction(new Wait(1000));



        if (middle){ // Middle
            ThreadAction(new DriveMotionProfile(-12, 0));
            runAction(new IntakeZeroing(false, 1000));
            runAction(new Intake(3000));
        } else if (right){ //Right
            ThreadAction(new DriveMotionProfile(-5, 0));
            runAction(new IntakeZeroing(false, 1000));

            runAction(new Turn(new Rotation2d(35, AngleUnit.DEGREES), 1000));
            runAction(new IntakeInFront(6, 5000, false));
            runAction(new Intake(3000));
        } else{ //Left
            ThreadAction(new DriveMotionProfile(-2, 0));
            runAction(new IntakeZeroing(false, 1000));

            runAction(new Turn(new Rotation2d(-45, AngleUnit.DEGREES), 1000));
            runAction(new IntakeInFront(6, 5000, false));
            runAction(new Intake(3000));
        }

        // Score Sample
        if (Robot.autoSampled) {
            runAction(new IntakeGrabbing());
            runAction(new Wait(250));
            runAction(new Turn(new Rotation2d(0, AngleUnit.DEGREES), 1000));

//            if(middle)
//                runAction(new DriveToDistanceAndAngle(-2, 0, 2000));
            if (right)
                runAction(new DriveToDistanceAndAngle(-4, 0, 2000));
            else
                runAction(new DriveToDistanceAndAngle(-6, 0, 2000));

            runAction(new Wait(250));
            runAction(new DumperScoring());
//            ThreadAction(new RobotUpdate(1900));
            ThreadAction(new Turn(new Rotation2d(0, AngleUnit.DEGREES), 1000));
            runAction(new Wait(2000));
            Robot.getDumper().collectingTimer.reset();
            runAction(new DumperDump());
        }


        // Drive to Crater
        runAction(new IntakeUp());
        ThreadAction(new IntakeZeroing(false, 1500));

        if (!right)
            runAction(new DriveMotionProfile(7, 0));

        runAction(new Turn(new Rotation2d(-90, AngleUnit.DEGREES), 1000));

        runAction(new DriveMotionProfile(40, -90));

        runAction(new Turn(new Rotation2d(-120, AngleUnit.DEGREES), 2000));

        runAction(new IntakeInFront(20, 5000, true));

//        runAction(new LiftReady());
    }
}
