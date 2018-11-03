package com.team9889.ftc2019.auto.actions;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.Constants;
import com.team9889.ftc2019.subsystems.Drive;
import com.team9889.ftc2019.subsystems.Robot;
import com.team9889.lib.control.controllers.PID;
import com.team9889.lib.control.math.cartesian.Rotation2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import static com.team9889.ftc2019.Constants.ENCODER_TO_DISTANCE_RATIO;

/**
 * Created by MannoMation on 11/2/2018.
 */
public class DriveRightMotor extends Action{
    private double right;
    private double rightTick;
    private PID rightPid;
    private ElapsedTime time;
    private double timeOut;


    private Drive mDrive = Robot.getInstance().getDrive();

    public DriveRightMotor(Rotation2d angle, double timeOut){
        this.right =  -angle.getTheda(AngleUnit.DEGREES) * Constants.AngleToInchRatio * 2;
    }

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        rightPid = new PID(0.001, 0, 0.0001);

        mDrive.DriveControlState(Drive.DriveControlStates.POWER);
        rightTick = mDrive.getRightTicks() + (int)(right / ENCODER_TO_DISTANCE_RATIO);
        time = new ElapsedTime();
    }

    @Override
    public void update() {
        mDrive.setLeftRightPower(0, rightPid.update(mDrive.getRightTicks(), rightTick));
    }

    @Override
    public boolean isFinished() {
        return Math.abs(rightTick - mDrive.getRightTicks()) < 5 || time.milliseconds() > timeOut;
    }

    @Override
    public void done() {
        mDrive.setLeftRightPower(0.0, 0.0);
    }
}
