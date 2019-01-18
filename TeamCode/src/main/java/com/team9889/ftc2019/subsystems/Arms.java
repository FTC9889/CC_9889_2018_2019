package com.team9889.ftc2019.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;
import com.team9889.ftc2019.Constants;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by MannoMation on 9/14/2018.
 */

public class Arms extends Subsystem{
    private Servo leftShoulder, rightShoulder;
    private Servo leftElbow, rightElbow;
    private Servo leftClaw, rightClaw;

    private boolean leftOpen, rightOpen;

    @Override
    public void init(HardwareMap hardwareMap, boolean auto) {
        this.leftShoulder = hardwareMap.get(Servo.class, Constants.ArmConstants.kLeftShoulderId);
        this.rightShoulder = hardwareMap.get(Servo.class, Constants.ArmConstants.kRightShoulderId);
        this.leftElbow = hardwareMap.get(Servo.class, Constants.ArmConstants.kLeftElbowId);
        this.rightElbow = hardwareMap.get(Servo.class, Constants.ArmConstants.kRightElbowId);
        this.leftClaw = hardwareMap.get(Servo.class, Constants.ArmConstants.kLeftClawId);
        this.rightClaw = hardwareMap.get(Servo.class, Constants.ArmConstants.kRightClawId);

        if (auto) {
            setLeftClawOpen(false);
            setRightClawOpen(false);
        }
    }

    @Override
    public void zeroSensors() {

    }

    @Override
    public void outputToTelemetry(Telemetry telemetry) {
        telemetry.addData("Left Arm Shoulder", leftShoulder.getPosition());
        telemetry.addData("Left Arm Elbow", leftElbow.getPosition());
        telemetry.addData("Left Arm Claw", leftClaw.getPosition());
        telemetry.addLine();
        telemetry.addData("Right Arm Shoulder", rightShoulder.getPosition());
        telemetry.addData("Right Arm Elbow", rightElbow.getPosition());
        telemetry.addData("Right Arm Claw", rightClaw.getPosition());
    }

    @Override
    public void update(ElapsedTime time) {

    }

    @Override
    public void test(Telemetry telemetry) {

    }

    @Override
    public void stop() {

    }

    public void setLeftArm(double Shoulder , double Elbow){
        leftShoulder.setPosition(Shoulder);
        leftElbow.setPosition(Elbow);
    }

    public void setRightArm(double Shoulder , double Elbow){
        rightShoulder.setPosition(Shoulder);
        rightElbow.setPosition(Elbow);
    }

    public  void setLeftClawOpen(boolean open){
        leftOpen = open;

        if(open)
            leftClaw.setPosition(.215);
        else
            leftClaw.setPosition(1);
    }

    public void setRightClawOpen(boolean open){
        rightOpen = open;

        if (open)
            rightClaw.setPosition(.116);
        else
            rightClaw.setPosition(.9);
    }

    public boolean bothOpen() {
        return leftOpen && rightOpen;
    }

    @Override
    public String toString() {
        return "Arms";
    }
}
