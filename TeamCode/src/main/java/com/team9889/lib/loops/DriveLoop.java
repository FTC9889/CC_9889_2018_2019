package com.team9889.lib.loops;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.subsystems.Drive;
import com.team9889.ftc2019.subsystems.Robot;

/**
 * Created by joshua9889 on 4/5/2018.
 */

public class DriveLoop extends Loop {
    ElapsedTime t = new ElapsedTime();

    private Drive mDrive = Robot.getInstance().getDrive();
    private int leftStartPosition, rightStartPosition;
    private int newLeftPosition, newRightPosition;
    private double angle;

    public boolean isActing = false;

    @Override
    public void start(double timestamp) {
        leftStartPosition = mDrive.getLeftTicks();
        rightStartPosition = mDrive.getRightTicks();
        t.reset();
    }

    @Override
    public void loop(double timestamp) {
        newLeftPosition = mDrive.getLeftTicks();
        newRightPosition = mDrive.getRightTicks();
        angle = mDrive.getGyroAngleRadians();
        System.out.println("DriveLoop> "+t.nanoseconds() + " | System Time: " + System.currentTimeMillis());
        t.reset();
    }

    @Override
    public void stop(double timestamp) {

    }
}
