package com.team9889.ftc2019.auto.modes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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
import com.team9889.ftc2019.auto.actions.Wait;
import com.team9889.ftc2019.subsystems.Camera;
import com.team9889.lib.control.math.cartesian.Rotation2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by MannoMation on 1/12/2019.
 */

@Autonomous
public class AutonomousCraterSideDouble extends AutoModeBase {
    @Override
    public void run(AllianceColor allianceColor) {
        Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTCENTER);
        /*
        while(opModeIsActive() && !Robot.getLift().inPosition()) {
            Robot.getLift().setLiftState(Lift.LiftStates.READY);
        }
        */
        runAction(new IntakeInFront());
        runAction(new Wait(1500));

        if (Robot.getCamera().isGoldInfront()){ // Middle
            runAction(new DriveToDistanceAndAngle(20, 0, 2000));

            runAction(new IntakeZeroing());
            runAction(new DriveToDistanceAndAngle(-7, 0, 1500));

            runAction(new Turn(new Rotation2d(-100, AngleUnit.DEGREES), 3000));
            runAction(new DriveToDistanceAndAngle(55, -100, 3000));

            runAction(new DriveTurn(new Rotation2d(-35, AngleUnit.DEGREES), 1500));

            ThreadAction(new IntakeInFront());

            runAction(new DriveToDistanceAndAngle(20, -135, 3000));

            runAction(new Outtake(-.8));

            runAction(new DriveRightMotor(new Rotation2d(-105, AngleUnit.DEGREES), 2000));

            runAction(new Intake());
            runAction(new Wait(500));

            runAction(new DriveRightMotor(new Rotation2d(105, AngleUnit.DEGREES), 3000));
            runAction(new DriveToDistanceAndAngle(-65, -137, 4000));
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
                runAction(new DriveToDistanceAndAngle(-20, 45, 1500));

                // Face Wall
                runAction(new Turn(new Rotation2d(-90, AngleUnit.DEGREES), 3000));
                runAction(new DriveToDistanceAndAngle(47.5, -90, 4000));

                // Turn To Depot
                runAction(new DriveTurn(new Rotation2d(-45, AngleUnit.DEGREES), 2000));

                ThreadAction(new IntakeInFront());

                // Drive closer to depot
                runAction(new DriveToDistanceAndAngle(30, -135, 3000));

                // Score Marker
                runAction(new Outtake(-.5));

                ThreadAction(new IntakeZeroing());

                // Knock off second sample
                runAction(new DriveRightMotor(new Rotation2d(45, AngleUnit.DEGREES), 1500));
                runAction(new DriveToDistanceAndAngle(-12, -90, 1000));
                runAction(new DriveToDistanceAndAngle(14, -90, 1000));
                runAction(new DriveRightMotor(new Rotation2d(-45, AngleUnit.DEGREES), 1500));

                // Drive to crater
                runAction(new DriveToDistanceAndAngle(-70, -137, 4000));

            } else{ //Left

                runAction(new DriveToDistanceAndAngle(8, 0, 3000));
                runAction(new DriveTurn(new Rotation2d(-45, AngleUnit.DEGREES), 2000));
                runAction(new DriveToDistanceAndAngle(24, -45, 3000));
                ThreadAction(new IntakeZeroing());
                runAction(new Turn(new Rotation2d(-90, AngleUnit.DEGREES), 3000));
                runAction(new DriveToDistanceAndAngle(20, -90, 2500));
                runAction(new DriveTurn(new Rotation2d(-45, AngleUnit.DEGREES), 1500));
                ThreadAction(new IntakeInFront());
                runAction(new DriveToDistanceAndAngle(43, -135, 3000));
                runAction(new Outtake());

                // Knock off second sample
                runAction(new DriveRightMotor(new Rotation2d(-90, AngleUnit.DEGREES), 1500));
                runAction(new DriveToDistanceAndAngle(15, 135, 1000));
                runAction(new Intake());
                runAction(new Wait(500));
                runAction(new DriveToDistanceAndAngle(-12, 135, 1000));
                runAction(new DriveRightMotor(new Rotation2d(90, AngleUnit.DEGREES), 1500));

                runAction(new IntakeStop());
                ThreadAction(new IntakeZeroing());
                runAction(new DriveToDistanceAndAngle(-70, -135, 4000));

            }
        }
    }
}
