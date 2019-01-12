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
 * Created by MannoMation on 11/2/2018.
 */
public class DriveLeftMotor extends Action {
    private double left, leftTick, offset;
    private PID leftPid;
    private ElapsedTime time;
    private double timeOut;


    private Drive mDrive = Robot.getInstance().getDrive();

    public DriveLeftMotor(Rotation2d angle, double timeOut){
        this.left =  angle.getTheda(AngleUnit.DEGREES) * Constants.DriveConstants.AngleToInchRatio * 2;
        this.timeOut = timeOut;
    }

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        leftPid = new PID(0.0025, 0.0, 0.025);

        mDrive.DriveControlState(Drive.DriveControlStates.POWER);

        offset = mDrive.getRightTicks();
        leftTick = (int)(left / ENCODER_TO_DISTANCE_RATIO);
        time = new ElapsedTime();
    }

    @Override
    public void update() {
        double leftPower = leftPid.update(mDrive.getLeftTicks() - offset, leftTick);
        leftPower = CruiseLib.limitValue(leftPower, 0.5);

        mDrive.setLeftRightPower(leftPower,0);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(leftTick - (mDrive.getLeftTicks() - offset)) < 5 || time.milliseconds() > timeOut;
    }

    @Override
    public void done() {
        mDrive.setLeftRightPower(0.0, 0.0);
    }
}
