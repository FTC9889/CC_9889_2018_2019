package com.team9889.ftc2019.auto.actions.Drive;

import com.team9889.ftc2019.Constants;
import com.team9889.ftc2019.auto.actions.Action;
import com.team9889.ftc2019.subsystems.Drive;
import com.team9889.ftc2019.subsystems.Robot;
import com.team9889.lib.control.controllers.PID;
import com.team9889.lib.control.math.cartesian.Rotation2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import static com.team9889.ftc2019.Constants.ENCODER_TO_DISTANCE_RATIO;

/**
 * Created by MannoMation on 11/2/2018.
 */
public class DriveLeftMotor extends Action {
    private double left;
    private double leftTick;
    private PID leftPid;

    private Drive mDrive = Robot.getInstance().getDrive();

    public DriveLeftMotor(Rotation2d angle){
        this.left =  angle.getTheda(AngleUnit.DEGREES) * Constants.AngleToInchRatio * 2;
    }

    @Override
    public void setup(String args) {

    }

    @Override
    public void start() {
        leftPid = new PID(0.001, 0, 0.0001);

        mDrive.DriveControlState(Drive.DriveControlStates.POWER);
        leftTick = mDrive.getLeftTicks() + (int)(left / ENCODER_TO_DISTANCE_RATIO);
    }

    @Override
    public void update() {
        mDrive.setLeftRightPower(leftPid.update(mDrive.getLeftTicks(), leftTick), 0);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(leftTick - mDrive.getLeftTicks()) < 5;
    }

    @Override
    public void done(){mDrive.setLeftRightPower(0,0);}
}
