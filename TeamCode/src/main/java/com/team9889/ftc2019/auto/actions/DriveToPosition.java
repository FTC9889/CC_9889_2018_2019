package com.team9889.ftc2019.auto.actions;

import com.team9889.ftc2019.subsystems.Drive;
import com.team9889.ftc2019.subsystems.Robot;

import static com.team9889.ftc2019.Constants.ENCODER_TO_DISTANCE_RATIO;

/**
 * Created by MannoMation on 10/29/2018.
 */

public class DriveToPosition extends Action{

    private Drive mDrive = Robot.getInstance().getDrive();
    private double left, right;

    public DriveToPosition(double left, double right){
        this.left = left;
        this.right = right;
    }

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        this.mDrive.DriveControlState(Drive.DriveControlStates.POSITION);
        this.mDrive.leftMaster_.setTargetPosition(mDrive.getLeftTicks() + (int)(left / ENCODER_TO_DISTANCE_RATIO));
        this.mDrive.rightMaster_.setTargetPosition(mDrive.getRightTicks() + (int)(right / ENCODER_TO_DISTANCE_RATIO));

    }

    @Override
    public void update() {
        this.mDrive.setLeftRightPower(0.2, 0.2);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void done() {
        this.mDrive.setLeftRightPower(0.0, 0.0);
    }
}
