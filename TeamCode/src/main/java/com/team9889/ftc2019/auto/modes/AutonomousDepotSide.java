package com.team9889.ftc2019.auto.modes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.team9889.ftc2019.auto.AutoModeBase;
import com.team9889.ftc2019.auto.actions.Arms.ArmsToPark;
import com.team9889.ftc2019.auto.actions.Drive.DriveToDistanceAndAngle;
import com.team9889.ftc2019.auto.actions.Drive.DriveTurn;
import com.team9889.ftc2019.auto.actions.Drive.Turn;
import com.team9889.ftc2019.auto.actions.Intake.IntakeInFront;
import com.team9889.ftc2019.auto.actions.Intake.IntakeZeroing;
import com.team9889.ftc2019.auto.actions.Intake.Outtake;
import com.team9889.ftc2019.auto.actions.Lift.LiftLand;
import com.team9889.ftc2019.auto.actions.Wait;
import com.team9889.ftc2019.states.LiftStates;
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
        Robot.getLift().setLiftState(LiftStates.READY);
        while (!Robot.getLift().isCurrentWantedState()) {
            Robot.getLift().update(matchTime);
        }

        ThreadAction(new IntakeInFront());
        runAction(new Wait(1500));

        if (Robot.getCamera().isGoldInfront()){ // Middle
            runAction(new DriveToDistanceAndAngle(50, 0, 3000));

            runAction(new Outtake());

            runAction(new Turn(new Rotation2d(90, AngleUnit.DEGREES), 2000));

            runAction(new DriveToDistanceAndAngle(-8, 90, 2500));
            runAction(new Turn(new Rotation2d(47, AngleUnit.DEGREES), 2000));

            runAction(new Wait(300));

            runAction(new DriveToDistanceAndAngle(-52, 90 - 47, 5000));
            runAction(new ArmsToPark());

        } else{
            Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTRIGHT);

            runAction(new Wait(1000));

            if (Robot.getCamera().isGoldInfront()){ //Right
                runAction(new DriveToDistanceAndAngle(10, 0, 3000));
                runAction(new DriveTurn(new Rotation2d(45, AngleUnit.DEGREES), 2000));
                runAction(new DriveToDistanceAndAngle(35, 45, 3000));
                runAction(new DriveTurn(new Rotation2d(-90, AngleUnit.DEGREES), 2000));
                runAction(new DriveToDistanceAndAngle(20, -45, 2000));
                runAction(new Outtake(-1));
                runAction(new IntakeZeroing());

                runAction(new DriveTurn(new Rotation2d(-45, AngleUnit.DEGREES), 1500));
                runAction(new Wait(1000));
                runAction(new DriveToDistanceAndAngle(25, -90, 2000));
                runAction(new DriveTurn(new Rotation2d(-50, AngleUnit.DEGREES), 1500));
                runAction(new DriveToDistanceAndAngle(50, -140, 4000));
                runAction(new DriveTurn(new Rotation2d(185, AngleUnit.DEGREES), 3000));
                runAction(new DriveToDistanceAndAngle(5, 45, 1500));
                runAction(new ArmsToPark());

            } else{ //Left
                runAction(new DriveToDistanceAndAngle(8, 0, 3000));
                runAction(new DriveTurn(new Rotation2d(-45, AngleUnit.DEGREES), 2000));
                runAction(new DriveToDistanceAndAngle(35, -45, 3000));
                runAction(new DriveTurn(new Rotation2d(90, AngleUnit.DEGREES), 2000));
                runAction(new DriveToDistanceAndAngle(20, 45, 2000));
                runAction(new Outtake(-1));

                runAction(new IntakeZeroing());

                runAction(new DriveToDistanceAndAngle(-50, 45, 3000));
                runAction(new ArmsToPark());


            }
        }





    }
}
