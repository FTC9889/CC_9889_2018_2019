package com.team9889.ftc2019.auto.actions;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.subsystems.Drive;
import com.team9889.ftc2019.subsystems.Robot;
import com.team9889.lib.control.controllers.PID;

import static com.team9889.ftc2019.Constants.ENCODER_TO_DISTANCE_RATIO;

/**
 * Created by MannoMation on 11/2/2018.
 */
public class DriveToPosition extends Action{

    private Drive mDrive = Robot.getInstance().getDrive();
    private double left, right;
    private int leftTick, rightTick;
    private PID leftPid, rightPid;
    private ElapsedTime time;
    private double timeOut;

    public DriveToPosition(double left, double right, double timeOut){
        this.left = left;
        this.right = right;
        this.timeOut = timeOut;
    }

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        leftPid = new PID(0.005, 0.0000001, 0.001);
        rightPid = new PID(0.005, 0.0000001, 0.001);

        mDrive.DriveControlState(Drive.DriveControlStates.POWER);
        leftTick = mDrive.getLeftTicks() + (int)(left / ENCODER_TO_DISTANCE_RATIO);
        rightTick = mDrive.getRightTicks() + (int)(right / ENCODER_TO_DISTANCE_RATIO);
        time = new ElapsedTime();
    }

    @Override
    public void update() {
        mDrive.setLeftRightPower(leftPid.update(mDrive.getLeftTicks(), leftTick),
                rightPid.update(mDrive.getRightTicks(), rightTick));
    }

    @Override
    public boolean isFinished() {
        return (Math.abs(leftTick - mDrive.getLeftTicks()) < 5 &&
                Math.abs(rightTick - mDrive.getRightTicks()) < 5) || time.milliseconds() > timeOut;
    }

    @Override
    public void done() {
        this.mDrive.setLeftRightPower(0.0, 0.0);
    }
}
