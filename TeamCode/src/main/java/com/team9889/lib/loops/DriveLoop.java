package com.team9889.lib.loops;

import com.team9889.ftc2019.Constants;
import com.team9889.ftc2019.subsystems.Drive;
import com.team9889.ftc2019.subsystems.Robot;
import com.team9889.lib.control.math.Rotation2d;
import com.team9889.lib.control.math.Vector2d;

/**
 * Created by joshua9889 on 4/5/2018.
 */

public class DriveLoop extends Loop {
    private Drive mDrive = Robot.getInstance().getDrive();

    @Override
    public void start(double timestamp) {

    }

    @Override
    public void loop(double timestamp) {
        double avgDistance = (mDrive.getLeftDistanceInches() + mDrive.getRightDistanceInches()) / 2.0;
        double leftDistance = mDrive.getLeftDistanceInches();
        double rightDisance = mDrive.getRightDistanceInches();

        double changeInAngle = (rightDisance - leftDistance) / Constants.WheelbaseWidth;
        double changeInX = avgDistance * Math.cos(changeInAngle);
        double changeInY = avgDistance * Math.sin(changeInAngle);

        Vector2d vector2d = Vector2d.add(mDrive.getPose().getVector2D(), new Vector2d(changeInX, changeInY));
        Rotation2d rotation2d = Rotation2d.add(mDrive.getPose().getRotation2d(), new Rotation2d(changeInAngle, Rotation2d.Unit.RADIANS));

        mDrive.getPose().setVector2D(vector2d);
        mDrive.getPose().setRotation2d(rotation2d);
    }

    @Override
    public void stop(double timestamp) {

    }
}
