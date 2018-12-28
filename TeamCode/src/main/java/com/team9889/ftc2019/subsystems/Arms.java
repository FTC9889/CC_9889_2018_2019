package com.team9889.ftc2019.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.team9889.ftc2019.Constants;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by MannoMation on 9/14/2018.
 */

public class Arms extends Subsystem{
    private Servo leftShoulder, rightShoulder;
    private Servo leftElbow, rightElbow;
    private Servo leftClaw, rightClaw;

    public enum ArmStates{
        STORED, GRAB, PARK, GOLDGOLD, GOLDSILVER, SILVERSILVER, SILVERGOLD
    }

    ElapsedTime timer = new ElapsedTime();

    @Override
    public void init(HardwareMap hardwareMap, boolean auto) {
        this.leftShoulder = hardwareMap.get(Servo.class, Constants.kLeftShoulderID);
        this.rightShoulder = hardwareMap.get(Servo.class, Constants.kRightShoulderId);
        this.leftElbow = hardwareMap.get(Servo.class, Constants.kLeftElbowId);
        this.rightElbow = hardwareMap.get(Servo.class, Constants.kRightElbowId);
        this.leftClaw = hardwareMap.get(Servo.class, Constants.kLeftClawId);
        this.rightClaw = hardwareMap.get(Servo.class, Constants.kRightClawId);

        if (auto) {
            setArmsStates(ArmStates.STORED);
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
    public void test(Telemetry telemetry) {

    }

    @Override
    public void stop() {

    }

    public void getLeftClawPosition(){
        leftClaw.getPosition();
    }

    public void getRightClawPosition(){
        rightClaw.getPosition();
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
        if(open)
            leftClaw.setPosition(.6);
        else
            leftClaw.setPosition(.9);
    }

    public void setRightClawOpen(boolean open){
        if (open)
            rightClaw.setPosition(.6);
        else
            rightClaw.setPosition(.9);
    }

    boolean first = true;
    public void setArmsStates(ArmStates state){
        switch (state){
            case SILVERSILVER:
                setRightArm(0.2, 0.241);
                setLeftArm(0.9, 0.524);
                first = true;
                break;
            case SILVERGOLD:
                setRightArm(0.15, 0);
                setLeftArm(0.9, 0.524);
                break;

            case GOLDSILVER:
                break;

            case GOLDGOLD:
                break;

            case PARK:
                break;

            case STORED:
                setRightArm(.9, .72);
                setLeftArm(.23, .13);
                break;

            case GRAB:
                if(first){
                    setRightArm(.9, .72);
                    timer.reset();
                    first = false;
                } else if(timer.milliseconds()>40){
                    setLeftArm(.25, .1);
                }

                break;
        }
    }

}
