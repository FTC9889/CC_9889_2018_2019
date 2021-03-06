package com.team9889.ftc2019.auto.actions.Drive;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.Constants;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Drive;
import com.team9889.ftc2019.subsystems.Robot;
import com.team9889.lib.CruiseLib;
import com.team9889.lib.control.controllers.PID;
import com.team9889.lib.control.math.cartesian.Rotation2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import static com.team9889.ftc2019.Constants.DriveConstants.ENCODER_TO_DISTANCE_RATIO;

/**
 * Created by MannoMation on 10/26/2018.
 */

public class DriveTurn extends Action {
    private double left, right;
    private double leftTick, rightTick;
    private ElapsedTime time;
    private double timeOut;

    private PID leftPid, rightPid;

    private Drive mDrive = Robot.getInstance().getDrive();

    public DriveTurn(Rotation2d angle, double timeOut){
        this.left =  angle.getTheda(AngleUnit.DEGREES) * Constants.DriveConstants.AngleToInchRatio;
        this.right =  -angle.getTheda(AngleUnit.DEGREES) * Constants.DriveConstants.AngleToInchRatio;
        this.timeOut = timeOut;
    }

    @Override
    public void setup(String args) {
        
    }

    @Override
    public void start() {
        leftPid = new PID(0.007, 0.000004, 0.001);
        rightPid = new PID(0.007, 0.000004, 0.001);

        mDrive.DriveControlState(Drive.DriveControlStates.POWER);
        leftTick = mDrive.getLeftTicks() + (int)(left / ENCODER_TO_DISTANCE_RATIO);
        rightTick = mDrive.getRightTicks() + (int)(right / ENCODER_TO_DISTANCE_RATIO);
        time = new ElapsedTime();
    }

    @Override
    public void update() {
        double leftPower = CruiseLib.limitValue(
                leftPid.update(mDrive.getLeftTicks(), leftTick), 0.5);

        double rightPower = CruiseLib.limitValue(
                rightPid.update(mDrive.getRightTicks(), rightTick), 0.5);

        mDrive.setLeftRightPower(leftPower, rightPower);
    }

    @Override
    public boolean isFinished() {
        return (Math.abs(leftTick - mDrive.getLeftTicks()) < 5 &&
                Math.abs(rightTick - mDrive.getRightTicks()) < 5)
                || time.milliseconds() > timeOut;
    }

    @Override
    public void done() {
        mDrive.setLeftRightPower(0.0, 0.0);
    }
}
