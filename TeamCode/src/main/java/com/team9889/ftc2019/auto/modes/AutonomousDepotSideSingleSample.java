package com.team9889.ftc2019.auto.modes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.AutoModeBase;
import com.team9889.ftc2019.auto.actions.Drive.DriveToPosition;
import com.team9889.ftc2019.auto.actions.Drive.DriveTurn;
import com.team9889.ftc2019.auto.actions.Wait;
import com.team9889.ftc2019.subsystems.Camera;
import com.team9889.ftc2019.subsystems.Lift;
import com.team9889.lib.control.math.cartesian.Rotation2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by MannoMation on 11/3/2018.
 */

@Autonomous
public class AutonomousDepotSideSingleSample extends AutoModeBase {
    @Override
    public void run(AllianceColor allianceColor) {
        ElapsedTime timer = new ElapsedTime();

        Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTCENTER);
//        while(opModeIsActive() && !Robot.getLift().inPosition()) {
//            Robot.getLift().setLiftState(Lift.LiftStates.READY);
//        }

        runAction(new Wait(1250));

        if (Robot.getCamera().isGoldInfront()){
            runAction(new Wait((int)(24000 - timer.milliseconds())));
            runAction(new DriveToPosition(23,23, 3000));
        } else{
            Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTRIGHT);

            runAction(new Wait(1000));

            if (Robot.getCamera().isGoldInfront()){
                runAction(new DriveToPosition(5, 5, 500));
                runAction(new DriveTurn(new Rotation2d(35, AngleUnit.DEGREES), 3000));
                runAction(new Wait((int)(24000 - timer.milliseconds())));
                runAction(new DriveToPosition(20, 20, 1000));
            } else{
                runAction(new DriveToPosition(5, 5, 1500));
                runAction(new DriveTurn(new Rotation2d(-45, AngleUnit.DEGREES), 3000));
                runAction(new Wait((int)(24000 - timer.milliseconds())));
                runAction(new DriveToPosition(30, 30, 2750));
                runAction(new DriveTurn(new Rotation2d(40, AngleUnit.DEGREES),2000));
            }
        }

        Robot.getCamera().setCameraPosition(Camera.CameraPositions.STORED);

        runAction(new DriveToPosition(15, 15, 1000));


    }
}
