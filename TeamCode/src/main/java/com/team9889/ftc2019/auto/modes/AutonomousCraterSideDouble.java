package com.team9889.ftc2019.auto.modes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.team9889.ftc2019.auto.AutoModeBase;
import com.team9889.ftc2019.auto.actions.Drive.DriveRightMotor;
import com.team9889.ftc2019.auto.actions.Drive.DriveToDistanceAndAngle;
import com.team9889.ftc2019.auto.actions.Drive.DriveTurn;
import com.team9889.ftc2019.auto.actions.Drive.Turn;
import com.team9889.ftc2019.auto.actions.Intake.Intake;
import com.team9889.ftc2019.auto.actions.Intake.IntakeInFront;
import com.team9889.ftc2019.auto.actions.Intake.IntakeStop;
import com.team9889.ftc2019.auto.actions.Intake.IntakeZeroing;
import com.team9889.ftc2019.auto.actions.Intake.Outtake;
import com.team9889.ftc2019.auto.actions.Lift.Land2;
import com.team9889.ftc2019.auto.actions.Wait;
import com.team9889.ftc2019.subsystems.Camera;
import com.team9889.lib.control.math.cartesian.Rotation2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by MannoMation on 1/12/2019.
 */

@Autonomous(group = "Competition Autonomous")
@Disabled
public class AutonomousCraterSideDouble extends AutoModeBase {
    @Override
    public void run(Side side, boolean doubleSample, boolean scoreSample) {
        Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTCENTER);
        runAction(new Land2(5000));

        ThreadAction(new IntakeInFront(20, 2000, true));
        runAction(new Wait(1500));

        if (Robot.getCamera().isGoldInfront()){ // Middle
            runAction(new DriveToDistanceAndAngle(25, 0, 2000));

            runAction(new IntakeZeroing());
            runAction(new DriveToDistanceAndAngle(-5, 0, 1500));

            runAction(new Turn(new Rotation2d(-100, AngleUnit.DEGREES), 3000));
            runAction(new DriveToDistanceAndAngle(62, -100, 3000));

            runAction(new DriveTurn(new Rotation2d(-35, AngleUnit.DEGREES), 1500));

            ThreadAction(new IntakeInFront(20, 2000, true));

            runAction(new DriveToDistanceAndAngle(25, -135, 3000));

            runAction(new Outtake(-.8, 1));

            runAction(new DriveRightMotor(new Rotation2d(-110, AngleUnit.DEGREES), 2000));

            runAction(new Intake(3000));
            runAction(new Wait(500));

            runAction(new DriveRightMotor(new Rotation2d(105, AngleUnit.DEGREES), 3000));
            runAction(new DriveToDistanceAndAngle(-70, -137, 4000));
            runAction(new IntakeStop());

            runAction(new Wait(400));

        } else{
            Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTRIGHT);

            runAction(new Wait(1000));

            if (Robot.getCamera().isGoldInfront()){ //Right
                runAction(new DriveToDistanceAndAngle(10, 0, 3000));
                runAction(new DriveTurn(new Rotation2d(45, AngleUnit.DEGREES), 2000));
                runAction(new DriveToDistanceAndAngle(20, 45, 1500));
                ThreadAction(new IntakeZeroing());
                runAction(new DriveToDistanceAndAngle(-8, 45, 1500));

                // Face Wall
                runAction(new Turn(new Rotation2d(-90, AngleUnit.DEGREES), 3000));
                runAction(new DriveToDistanceAndAngle(58, -90, 4000));

                // Turn To Depot
                runAction(new DriveTurn(new Rotation2d(-45, AngleUnit.DEGREES), 2000));

                ThreadAction(new IntakeInFront(20, 2000, true));

                // Drive closer to depot
                runAction(new DriveToDistanceAndAngle(30, -135, 3000));

                // Score Marker
                runAction(new Outtake(-.5, 1));

                ThreadAction(new IntakeZeroing());

                // Knock off second sample
                runAction(new DriveRightMotor(new Rotation2d(45, AngleUnit.DEGREES), 1500));
                runAction(new DriveToDistanceAndAngle(-12, -90, 1000));
                runAction(new DriveToDistanceAndAngle(14, -90, 1000));
                runAction(new DriveRightMotor(new Rotation2d(-45, AngleUnit.DEGREES), 1500));

                // Drive to crater
                runAction(new DriveToDistanceAndAngle(-70, -137, 4000));

            } else{ //Left

                // Drive away from Lander
                runAction(new DriveToDistanceAndAngle(8, 0, 3000));
                runAction(new DriveTurn(new Rotation2d(-45, AngleUnit.DEGREES), 2000));
                runAction(new DriveToDistanceAndAngle(30, -45, 3000));
                ThreadAction(new IntakeZeroing());

                // Turn to wall
                runAction(new Turn(new Rotation2d(-90, AngleUnit.DEGREES), 3000));
                runAction(new DriveToDistanceAndAngle(20, -90, 2500));

                runAction(new DriveTurn(new Rotation2d(-45, AngleUnit.DEGREES), 1500));
                ThreadAction(new IntakeInFront(20, 2000, true));
                runAction(new DriveToDistanceAndAngle(43, -135, 3000)); // Distance to Depot TODO: Check this value on field
                runAction(new Outtake());

                // Knock off second sample
                runAction(new DriveRightMotor(new Rotation2d(-90, AngleUnit.DEGREES), 1500));
                runAction(new Intake(3000));
                runAction(new DriveToDistanceAndAngle(16, 135, 1000));
                runAction(new Wait(250));
                runAction(new DriveToDistanceAndAngle(-13, 135, 1000));
                runAction(new DriveRightMotor(new Rotation2d(90, AngleUnit.DEGREES), 1500));

                runAction(new IntakeStop());
                ThreadAction(new IntakeZeroing());
                runAction(new DriveToDistanceAndAngle(-70, -135, 4000));
            }
        }
    }
}
