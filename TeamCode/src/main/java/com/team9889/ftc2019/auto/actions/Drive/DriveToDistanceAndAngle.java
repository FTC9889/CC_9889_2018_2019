package com.team9889.ftc2019.auto.actions.Drive;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Drive;
import com.team9889.ftc2019.subsystems.Robot;
import com.team9889.lib.CruiseLib;
import com.team9889.lib.android.FileWriter;
import com.team9889.lib.control.controllers.PID;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by MannoMation on 1/9/2019.
 */

public class DriveToDistanceAndAngle extends Action {

    private Drive mDrive = Robot.getInstance().getDrive();
    private PID drivePid, anglePid;
    private double distance, angle;

    private double timeOut;
    private ElapsedTime Timer = new ElapsedTime();

    public DriveToDistanceAndAngle(double distance, double angle, double timeOut){
        this.distance = distance;
        this.angle = angle;
        this.timeOut = timeOut;
    }

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        drivePid = new PID(0.07, 0, 0.2);
        anglePid = new PID(0.03, 0, 0.001);

        distance = getAverageDistance() + distance;

        Timer.reset();
    }

    @Override
    public void update() {
        double currentPosition = getAverageDistance();
        double throttle = drivePid.update(currentPosition, distance);
        throttle = CruiseLib.limitValue(throttle, .5);

        double currentAngle = mDrive.getAngle().getTheda(AngleUnit.DEGREES);
        double steer = anglePid.update(currentAngle, angle);
        steer = Math.sin(steer);

        mDrive.setThrottleSteerPower(throttle, steer);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(distance - getAverageDistance()) < 0.3
                || Timer.milliseconds() >= timeOut;
    }

    @Override
    public void done() {
        mDrive.setThrottleSteerPower(0,0);
    }

    private double getAverageDistance(){
        return (mDrive.getLeftDistance() + mDrive.getRightDistance()) / 2.0;
    }
}
