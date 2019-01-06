package com.team9889.ftc2019.auto.modes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.team9889.ftc2019.auto.AutoModeBase;
import com.team9889.ftc2019.auto.actions.Drive.DriveToPosition;
import com.team9889.ftc2019.subsystems.Camera;
import com.team9889.ftc2019.subsystems.Lift;

/**
 * Created by MannoMation on 1/5/2019.
 */

@Autonomous
public class AutonomousCraterSide extends AutoModeBase {
    @Override
    public void run(AllianceColor allianceColor) {
        Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTCENTER);
        while(opModeIsActive() && !Robot.getLift().inPosition()) {
            Robot.getLift().setLiftState(Lift.LiftStates.READY);
        }
        runAction(new DriveToPosition(20, 20, 3000));

    }
}
