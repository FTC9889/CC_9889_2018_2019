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
public class DriveRightMotor extends Action {
    private double right, rightTick, offset;
    private PID rightPid;
    private ElapsedTime time;
    private double timeOut;


    private Drive mDrive = Robot.getInstance().getDrive();

    public DriveRightMotor(Rotation2d angle, double timeOut){
        this.right =  -angle.getTheda(AngleUnit.DEGREES) * Constants.DriveConstants.AngleToInchRatio * 2;
        this.timeOut = timeOut;
    }

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        rightPid = new PID(0.003, 0.0, 0.025);

        mDrive.DriveControlState(Drive.DriveControlStates.POWER);

        offset = mDrive.getRightTicks();
        rightTick = (int)(right / ENCODER_TO_DISTANCE_RATIO);
        time = new ElapsedTime();
    }

    @Override
    public void update() {
        double rightPower = rightPid.update(mDrive.getRightTicks() - offset, rightTick);
        rightPower = CruiseLib.limitValue(rightPower, 0.5);

        mDrive.setLeftRightPower(0, rightPower);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(rightTick - (mDrive.getRightTicks() - offset)) < 5 || time.milliseconds() > timeOut;
    }

    @Override
    public void done() {
        mDrive.setLeftRightPower(0.0, 0.0);
    }
}
