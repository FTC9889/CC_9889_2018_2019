package com.team9889.ftc2019.auto.actions;

import com.team9889.ftc2019.subsystems.Robot;
import com.team9889.lib.control.math.cartesian.Rotation2d;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by joshua9889 on 8/28/2018.
 */
public class Drive extends Action {

    private double distance;
    private Rotation2d angle;
    private double left;
    private double right;

    private Robot Robot = com.team9889.ftc2019.subsystems.Robot.getInstance();

    public Drive(double left, double right){
        this.left =  left;
        this.right = right;
    }

    @Override
    public void setup(String args) {
        double[] array = getNumbersFromString(args);

        distance = array[0];
        angle = new Rotation2d(array[1], AngleUnit.DEGREES);
    }

    @Override
    public void start() {
        Robot.getDrive().DriveControlState(com.team9889.ftc2019.subsystems.Drive.DriveControlStates.POSITION);

        Robot.getDrive().leftMaster_.setTargetPosition(Robot.getDrive().getLeftTicks() + (int) (left / Robot.getDrive().ENCODER_TO_DISTANCE_RATIO));
        Robot.getDrive().rightMaster_.setTargetPosition(Robot.getDrive().getRightTicks() + (int) (right / Robot.getDrive().ENCODER_TO_DISTANCE_RATIO));
    }

    @Override
    public void update() {
        Robot.getDrive().setLeftRightPower(0.2, 0.2);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(Robot.getDrive().getLeftDistance() - left) < 0.1 && Math.abs(Robot.getDrive().getRightDistance() - right) < 0.1;
    }

    @Override
    public void done() {
        Robot.getDrive().setLeftRightPower(0.0, 0.0);
    }
}
