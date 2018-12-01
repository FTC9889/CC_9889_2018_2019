package com.team9889.ftc2019.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.team9889.ftc2019.Constants;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by MannoMation on 9/14/2018.
 */

public class Arms extends Subsystem{
    private Servo leftShoulder, rightShoulder;
    private Servo leftElbow, rightElbow;
    private Servo leftClaw, rightClaw;

    @Override
    public void init(HardwareMap hardwareMap, boolean auto) {
        this.leftShoulder = hardwareMap.get(Servo.class, Constants.kLeftShoulderID);
        this.rightShoulder = hardwareMap.get(Servo.class, Constants.kRightShoulderId);
        this.leftElbow = hardwareMap.get(Servo.class, Constants.kLeftElbowId);
        this.rightElbow = hardwareMap.get(Servo.class, Constants.kRightElbowId);
        this.leftClaw = hardwareMap.get(Servo.class, Constants.kLeftClawId);
        this.rightClaw = hardwareMap.get(Servo.class, Constants.kRightClawId);

        setLeftArm(0.5, 0.5);
        setLeftClawOpen(true);
        setRightArm(0.5, 0.5);
        setRightClawOpen(true);
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
        leftClaw.setPosition(0);
    }

    public void setLeftClawClosed(boolean closed){
        leftClaw.setPosition(1);
    }

    public void setRightClawOpen(boolean open){
        rightClaw.setPosition(0);
    }

    public void setRightClawClosed(boolean closed){
        rightClaw.setPosition(1);
    }
}
