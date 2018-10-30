package com.team9889.ftc2019.auto.actions;

import com.team9889.ftc2019.subsystems.Drive;
import com.team9889.ftc2019.subsystems.Robot;
import com.team9889.lib.control.controllers.PID;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class Turn extends Action {

    public Turn(double angle){
        this.angle = angle;
    }

    private double angle;

    // Proportional gain
    private double kP = 0.19;

    // Integral gain
    private double kI = 0;

    // Derivative gain
    private double kD = 5;

    PID pid = new PID(kP, kI, kD);

    private double leftPow, rightPow;

    @Override
    public void setup(String args) {
        double[] array = getNumbersFromString(args);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(Robot.getInstance().getDrive().getAngle().getTheda(AngleUnit.DEGREES) - angle) < 1;
    }

    @Override
    public void update() {
        leftPow = pid.update(-Robot.getInstance().getDrive().getAngle().getTheda(AngleUnit.DEGREES), angle);
        rightPow = -leftPow;

        Robot.getInstance().getDrive().setLeftRightPower(leftPow, rightPow);
    }

    @Override
    public void done() {
        Robot.getInstance().getDrive().setLeftRightPower(0,0);
    }

    @Override
    public void start() {
        Robot.getInstance().getDrive().DriveControlState(Drive.DriveControlStates.POWER);
    }
}
