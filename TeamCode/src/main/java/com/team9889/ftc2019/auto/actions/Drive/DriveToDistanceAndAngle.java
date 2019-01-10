package com.team9889.ftc2019.auto.actions.Drive;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Drive;
import com.team9889.ftc2019.subsystems.Robot;
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

    private double leftOffset, rightOffset;

    private double timeOut;
    private ElapsedTime Timer = new ElapsedTime();
    private int currentStep = 0;

    FileWriter file;

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
        file = new FileWriter("DriveDistanceAngleData.csv");
        String output = "CurrentStep," + "WantedDistance" + "," + "CurrentPosition" + "," + "Throttle" + "," +
                "WantedAngle" + "," + "CurrentAngle" + "," + "Steer";
        file.write(output);

        drivePid = new PID(0.08, 0.0, 0.5);
        anglePid = new PID(0.15, 0, 0);
        distance += getAverageDistance();

        leftOffset = mDrive.getLeftDistance();
        rightOffset = mDrive.getRightDistance();

        Timer.reset();
    }

    private double getAverageDistance(){
        return (mDrive.getLeftDistance() + mDrive.getRightDistance()) / 2.0;
    }

    @Override
    public void update() {
        double currentPosition = getAverageDistance();
        double throttle = drivePid.update(currentPosition, distance);

        double currentAngle = mDrive.getAngle().getTheda(AngleUnit.DEGREES);
        double steer = anglePid.update(currentAngle, angle);

        steer = Math.sin(steer);

        mDrive.setThrottleSteerPower(throttle, steer);

        String output = String.valueOf(currentStep) + "," +
                String.valueOf(distance) + "," + String.valueOf(currentPosition) + "," +
                String.valueOf(throttle * 100) + "," +
                String.valueOf(angle) + "," + String.valueOf(currentAngle) + "," +
                String.valueOf(steer * 100);
        file.write(output);
        currentStep++;
    }

    @Override
    public boolean isFinished() {
        return mDrive.getLeftDistance() - leftOffset >= distance
                || mDrive.getRightDistance() - rightOffset >= distance
                || Timer.milliseconds() >= timeOut;
    }

    @Override
    public void done() {
        mDrive.setThrottleSteerPower(0,0);
        file.close();
    }
}
