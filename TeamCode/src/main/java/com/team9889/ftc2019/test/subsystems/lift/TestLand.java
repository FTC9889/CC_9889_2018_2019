package com.team9889.ftc2019.test.subsystems.lift;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.team9889.ftc2019.auto.AutoModeBase;
import com.team9889.ftc2019.auto.actions.Drive.DriveToDistanceAndAngle;
import com.team9889.ftc2019.auto.actions.Lift.Land2;
import com.team9889.ftc2019.subsystems.Camera;

/**
 * Created by MannoMation on 1/19/2019.
 */

@Autonomous(group = "Test")
public class TestLand extends AutoModeBase {
    @Override
    public void run(AutoModeBase.Side side, boolean doubleSample, boolean scoreSample) {
        waitForStart(true);
        Robot.getCamera().setCameraPosition(Camera.CameraPositions.FRONTCENTER);
        runAction(new Land2());
        runAction(new DriveToDistanceAndAngle(24, 0, 3000));
    }
}
