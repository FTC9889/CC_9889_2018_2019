package com.team9889.ftc2019.auto.modes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.team9889.ftc2019.auto.AutoModeBase;
import com.team9889.ftc2019.auto.actions.Drive.DriveMotionProfile;
import com.team9889.ftc2019.auto.actions.Drive.DriveToDistanceAndAngle;
import com.team9889.ftc2019.auto.actions.Drive.DriveToPosition;
import com.team9889.ftc2019.auto.actions.Drive.DriveTurn;
import com.team9889.ftc2019.auto.actions.Intake.IntakeInFront;
import com.team9889.ftc2019.auto.actions.Intake.Outtake;
import com.team9889.ftc2019.auto.actions.Wait;
import com.team9889.ftc2019.subsystems.Camera;
import com.team9889.ftc2019.subsystems.Intake;
import com.team9889.ftc2019.subsystems.Lift;
import com.team9889.lib.control.math.cartesian.Rotation2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by MannoMation on 1/5/2019.
 */

@Autonomous
public class AutonomousDepotSide extends AutoModeBase {
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

        if (Robot.getCamera().isGoldInfront()){
            runAction(new DriveToDistanceAndAngle(60, 0, 3000));
            runAction(new Outtake(-1));
            runAction(new DriveTurn(new Rotation2d(90, AngleUnit.DEGREES), 2000));
            runAction(new DriveToDistanceAndAngle(-8, 90, 2500));
            runAction(new DriveTurn(new Rotation2d(-47, AngleUnit.DEGREES), 2000));
            //extend Right Arm for parking
            runAction(new Wait(300));
            runAction(new DriveToDistanceAndAngle(-55, 90 - 47, 5000));
        } else{
            Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTRIGHT);

            runAction(new Wait(1000));

            if (Robot.getCamera().isGoldInfront()){
                runAction(new DriveToDistanceAndAngle(12, 0, 3000));
                runAction(new DriveTurn(new Rotation2d(45, AngleUnit.DEGREES), 2000));
                runAction(new DriveToDistanceAndAngle(40, 45, 3000));
                Robot.getIntake().setIntakeRotatorState(Intake.RotatorStates.UP);
                runAction(new DriveTurn(new Rotation2d(-90, AngleUnit.DEGREES), 2000));
                runAction(new DriveToDistanceAndAngle(30, -45, 2000));
                runAction(new Outtake(-1));

                Robot.getIntake().setWantedIntakeState(Intake.States.ZEROING);
                while (opModeIsActive() && !Robot.getIntake().isCurrentStateWantedState()){
                    Robot.getIntake().update(autoTimer);
                }

                runAction(new DriveTurn(new Rotation2d(-45, AngleUnit.DEGREES), 1500));
                runAction(new Wait(1000));
                runAction(new DriveToDistanceAndAngle(25, -90, 2000));
                runAction(new DriveTurn(new Rotation2d(-50, AngleUnit.DEGREES), 1500));
                runAction(new DriveToDistanceAndAngle(50, -140, 4000));
                runAction(new DriveTurn(new Rotation2d(185, AngleUnit.DEGREES), 3000));
                runAction(new DriveToDistanceAndAngle(5, 45, 1500));
            } else{
                runAction(new DriveToDistanceAndAngle(20, 0, 3000));
                runAction(new DriveTurn(new Rotation2d(-45, AngleUnit.DEGREES), 2000));
                runAction(new DriveToDistanceAndAngle(30, -45, 3000));
            }
        }





    }
}
