package com.team9889.ftc2019.auto.actions.Drive;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Drive;
import com.team9889.ftc2019.subsystems.Robot;
import com.team9889.lib.CruiseLib;
import com.team9889.lib.control.controllers.PID;
import com.team9889.lib.control.math.cartesian.Rotation2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by MannoMation on 1/12/2019.
 */
public class Turn extends Action {
    private int counter = 0;
    public double timeOut;
    private ElapsedTime timer = new ElapsedTime();
    private Drive mDrive = Robot.getInstance().getDrive();
    private double angle;
    private PID anglePid = new PID(0.05, 0, 1);

    public Turn(Rotation2d rotation2d, double timeOut){
        angle = rotation2d.getTheda(AngleUnit.DEGREES);
        this.timeOut = timeOut;
    }

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        timer.reset();
    }

    @Override
    public void update() {
        double leftPower = anglePid.update(mDrive.getAngle().getTheda(AngleUnit.DEGREES), angle);
        leftPower = CruiseLib.limitValue(leftPower, .5);
        mDrive.setLeftRightPower(leftPower, -leftPower);
        if (Math.abs(anglePid.getError()) < 2 ){
            counter ++;
        }
    }

    @Override
    public boolean isFinished() {
        return counter > 5 || timer.milliseconds() > timeOut;
    }

    @Override
    public void done() {
        mDrive.setLeftRightPower(0, 0);
    }
}