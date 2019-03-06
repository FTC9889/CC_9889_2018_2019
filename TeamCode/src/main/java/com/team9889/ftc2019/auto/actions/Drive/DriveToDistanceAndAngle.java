package com.team9889.ftc2019.auto.actions.Drive;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Drive;
import com.team9889.ftc2019.subsystems.Robot;
import com.team9889.lib.CruiseLib;
import com.team9889.lib.control.controllers.PID;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/**
 * Created by MannoMation on 1/9/2019.
 */

public class DriveToDistanceAndAngle extends Action {

    private Drive mDrive = Robot.getInstance().getDrive();
    private PID drivePid, anglePid;
    private double distance, angle;

    private double currentMax = 0.1;
    private double maxIncrement = 0.08;

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
        drivePid = new PID(0.3, 0, 10);
        anglePid = new PID(0.03, 0, 0.05);

        distance = getAverageDistance() + distance;

        mDrive.DriveControlState(Drive.DriveControlStates.POSITION);

        Timer.reset();
    }

    @Override
    public void update() {
        double currentPosition = getAverageDistance();
        double throttle = drivePid.update(currentPosition, distance);
        throttle = CruiseLib.limitValue(throttle, currentMax);

//        if(Math.abs(distance - getAverageDistance()) < Math.abs(2*distance/3) && currentMax > 0.25)
//            currentMax -= maxIncrement/2;
//        else
        if(currentMax < .8)
            currentMax += maxIncrement;

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
        mDrive.DriveControlState(Drive.DriveControlStates.OPERATOR_CONTROL);
    }

    private double getAverageDistance(){
        return (mDrive.getLeftDistance() + mDrive.getRightDistance()) / 2.0;
    }
}
