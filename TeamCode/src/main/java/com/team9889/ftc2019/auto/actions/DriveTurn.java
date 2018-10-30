package com.team9889.ftc2019.auto.actions;

import com.team9889.ftc2019.Constants;
import com.team9889.ftc2019.subsystems.Drive;
import com.team9889.ftc2019.subsystems.Robot;
import com.team9889.lib.control.math.cartesian.Rotation2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

import static com.team9889.ftc2019.Constants.ENCODER_TO_DISTANCE_RATIO;

/**
 * Created by MannoMation on 10/26/2018.
 */

public class DriveTurn extends Action{
    private double left;
    private double right;
    private final double AngleToInchesRatio = 0.13955556;

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
        mDrive.DriveControlState(com.team9889.ftc2019.subsystems.Drive.DriveControlStates.POSITION);

        mDrive.leftMaster_.setTargetPosition(mDrive.getLeftTicks() + (int) (left / ENCODER_TO_DISTANCE_RATIO));
        mDrive.rightMaster_.setTargetPosition(mDrive.getRightTicks() + (int) (right / ENCODER_TO_DISTANCE_RATIO));
    }

    @Override
    public void update() {
        mDrive.setLeftRightPower(0.2, 0.2);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(mDrive.getLeftDistance() - left) < 0.1 && Math.abs(mDrive.getRightDistance() - right) < 0.1;
    }

    @Override
    public void done() {
        mDrive.setLeftRightPower(0.0, 0.0);
    }
}
