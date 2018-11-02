package com.team9889.ftc2019.auto.actions;

import com.team9889.ftc2019.Constants;
import com.team9889.ftc2019.subsystems.Drive;
import com.team9889.ftc2019.subsystems.Robot;
import com.team9889.lib.control.controllers.PID;
import com.team9889.lib.control.math.cartesian.Rotation2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import static com.team9889.ftc2019.Constants.ENCODER_TO_DISTANCE_RATIO;

/**
 * Created by MannoMation on 10/26/2018.
 */

public class DriveTurn extends Action{
    private double left, right;
    private double leftTick, rightTick;
    private final double AngleToInchesRatio = 0.13955556;

    private PID leftPid, rightPid;

    private Drive mDrive = Robot.getInstance().getDrive();

    public DriveTurn(Rotation2d angle){
        this.left =  angle.getTheda(AngleUnit.DEGREES) * Constants.AngleToInchRatio;
        this.right =  -angle.getTheda(AngleUnit.DEGREES) * Constants.AngleToInchRatio;
    }

    @Override
    public void setup(String args) {
        
    }

    @Override
    public void start() {
        leftPid = new PID(0.001, 0, 0.0001);
        rightPid = new PID(0.001, 0, 0.0001);

        mDrive.DriveControlState(Drive.DriveControlStates.POWER);
        leftTick = mDrive.getLeftTicks() + (int)(left / ENCODER_TO_DISTANCE_RATIO);
        rightTick = mDrive.getRightTicks() + (int)(right / ENCODER_TO_DISTANCE_RATIO);
    }

    @Override
    public void update() {
        mDrive.setLeftRightPower(leftPid.update(mDrive.getLeftTicks(), leftTick),
                rightPid.update(mDrive.getRightTicks(), rightTick));
    }

    @Override
    public boolean isFinished() {
        return Math.abs(leftTick - mDrive.getLeftTicks()) < 5 && Math.abs(rightTick - mDrive.getRightTicks()) < 5;
    }

    @Override
    public void done() {
        mDrive.setLeftRightPower(0.0, 0.0);
    }
}
